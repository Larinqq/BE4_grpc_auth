package ganisrum.backend.infrastructure.grpc;

import com.google.protobuf.Empty;
import ganisrum.backend.authserver.grpc.ActionResponse;
import ganisrum.backend.authserver.grpc.AuthServiceGrpc;
import ganisrum.backend.authserver.grpc.ChangePasswordRequest;
import ganisrum.backend.authserver.grpc.LoginRequest;
import ganisrum.backend.authserver.grpc.RefreshTokenRequest;
import ganisrum.backend.authserver.grpc.RegisterRequest;
import ganisrum.backend.authserver.grpc.ResetPasswordRequest;
import ganisrum.backend.authserver.grpc.TokenResponse;
import ganisrum.backend.authserver.grpc.ValidateTokenResponse;
import ganisrum.backend.domain.service.AuthService;
import ganisrum.backend.infrastructure.mapper.GrpcProtoMapper;
import ganisrum.backend.web.dto.request.UserAuthRequest;
import ganisrum.backend.web.dto.request.UserChangePasswordRequest;
import ganisrum.backend.web.dto.request.UserPasswordRecoveryRequest;
import ganisrum.backend.web.dto.request.UserRegisterRequest;
import ganisrum.backend.web.dto.request.UserUpdateJwtTokenRequest;
import ganisrum.backend.web.dto.response.UserActionResponse;
import ganisrum.backend.web.dto.response.UserJwtTokenResponse;
import ganisrum.backend.web.dto.response.UserValidateTokenResponse;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Metadata.Key<String> AUTH_KEY =
            Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER);
    private final AuthServiceGrpc.AuthServiceBlockingStub authStub;
    private final GrpcProtoMapper mapper;

    @Override
    public UserJwtTokenResponse register(UserRegisterRequest request) {
        RegisterRequest registerRequest = mapper.dtoRegisterToProto(request);
        TokenResponse response = authStub.register(registerRequest);
        return mapper.protoTokenResponseToDto(response);
    }

    @Override
    public UserJwtTokenResponse login(UserAuthRequest request) {
        LoginRequest loginRequest = mapper.dtoAuthToProto(request);
        TokenResponse response = authStub.login(loginRequest);
        return mapper.protoTokenResponseToDto(response);
    }

    @Override
    public UserValidateTokenResponse validateToken(String token) throws AuthenticationException {
        if (token == null || token.isBlank())
            throw new AuthenticationException("Не передан заголовок авторизации");
        Metadata metadata = new Metadata();
        metadata.put(AUTH_KEY, token);
        var stub = authStub
                .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(metadata))
                .withDeadlineAfter(2, TimeUnit.SECONDS);
        ValidateTokenResponse validateTokenResponse = stub.validateToken(Empty.newBuilder().build());
        return mapper.protoToUserValidateTokenResponse(validateTokenResponse);
    }

    @Override
    public UserActionResponse changePassword(UserChangePasswordRequest request, String token) throws AuthenticationException {
        if (token == null || token.isBlank())
            throw new AuthenticationException("Не передан заголовок авторизации");
        Metadata metadata = new Metadata();
        metadata.put(AUTH_KEY, token);
        var stub = authStub
                .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(metadata))
                .withDeadlineAfter(2, TimeUnit.SECONDS);
        ChangePasswordRequest changePasswordRequest = mapper.dtoChangePasswordToProto(request);
        ActionResponse actionResponse = stub.changePassword(changePasswordRequest);
        return mapper.protoActionResponseToDto(actionResponse);
    }

    @Override
    public UserActionResponse recoverPassword(UserPasswordRecoveryRequest request) {
        ResetPasswordRequest resetPasswordRequest = mapper.dtoResetPasswordToProto(request);
        ActionResponse actionResponse = authStub.resetPassword(resetPasswordRequest);
        return mapper.protoActionResponseToDto(actionResponse);
    }

    @Override
    public UserJwtTokenResponse updateAccessToken(UserUpdateJwtTokenRequest request) {
        RefreshTokenRequest refreshTokenRequest = mapper.dtoUpdateTokenToProto(request);
        TokenResponse tokenResponse = authStub.updateAccessToken(refreshTokenRequest);
        return mapper.protoTokenResponseToDto(tokenResponse);
    }

    @Override
    public UserJwtTokenResponse updateRefreshToken(UserUpdateJwtTokenRequest request) {
        RefreshTokenRequest refreshTokenRequest = mapper.dtoUpdateTokenToProto(request);
        TokenResponse tokenResponse = authStub.updateRefreshToken(refreshTokenRequest);
        return mapper.protoTokenResponseToDto(tokenResponse);
    }
}
