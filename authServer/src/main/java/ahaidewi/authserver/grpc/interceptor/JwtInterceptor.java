package ahaidewi.authserver.grpc.interceptor;

import ahaidewi.authserver.domain.security.model.JwtAuthAccessResult;
import ahaidewi.authserver.domain.service.AuthService;
import ahaidewi.authserver.grpc.AuthServiceProto;
import ahaidewi.authserver.grpc.GrpcConstants;
import io.grpc.*;
import io.grpc.protobuf.ProtoMethodDescriptorSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.GlobalServerInterceptor;
import org.springframework.stereotype.Component;

@Component
@GlobalServerInterceptor
@RequiredArgsConstructor
public class JwtInterceptor implements ServerInterceptor {
  private final AuthService authService;


  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
      ServerCall<ReqT, RespT> call,
      Metadata headers,
      ServerCallHandler<ReqT, RespT> next) {

    Object schemaDescriptor = call.getMethodDescriptor().getSchemaDescriptor();
    if (schemaDescriptor instanceof ProtoMethodDescriptorSupplier supplier) {
      boolean needAuth = supplier.getMethodDescriptor().getOptions().getExtension(AuthServiceProto.requiresAuth);
      if (!needAuth) {
        return next.startCall(call, headers);
      }
    } else {
      return next.startCall(call, headers);
    }

    String authHeader = headers.get(GrpcConstants.AUTHORIZATION_METADATA_KEY);
    if (authHeader == null) {
      return closeWithError(call, "Отсутствует заголовок авторизации");
    }
    if (!authHeader.startsWith(authService.getTokenPrefix())) {
      return closeWithError(call, "Неправильный тип токена");
    }

    String token = authHeader.substring(authService.getTokenPrefix().length());
    JwtAuthAccessResult authentication = authService.validateAccessToken(token);

    if (!authentication.isValid()) {
      return closeWithError(call, "Невалидный токен");
    }

    Context context = Context.current().withValue(GrpcConstants.AUTH_CONTEXT_KEY, authentication);
    return Contexts.interceptCall(context, call, headers, next);
  }


  private <ReqT, RespT> ServerCall.Listener<ReqT> closeWithError(ServerCall<ReqT, RespT> call, String message) {
    call.close(Status.UNAUTHENTICATED.withDescription(message), new Metadata());
    return new ServerCall.Listener<>() {
    };
  }

}
