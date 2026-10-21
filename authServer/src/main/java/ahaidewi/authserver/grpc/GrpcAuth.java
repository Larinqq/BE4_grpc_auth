package ahaidewi.authserver.grpc;

import ahaidewi.authserver.domain.model.User;
import ahaidewi.authserver.domain.security.model.JwtAuthAccessResult;
import ahaidewi.authserver.domain.security.model.JwtAuthRefreshResult;
import ahaidewi.authserver.domain.service.AuthService;
import ahaidewi.authserver.domain.service.UserService;
import ahaidewi.authserver.grpc.exception.EntityAlreadyExistsException;
import ahaidewi.authserver.grpc.mapper.WebUserMapper;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.UUID;

@GrpcService
@Slf4j
@RequiredArgsConstructor
public class GrpcAuth extends AuthServiceGrpc.AuthServiceImplBase {
  private final AuthService authService;
  private final UserService userService;
  private final WebUserMapper webUserMapper;

  @Override
  public void register(RegisterRequest request, StreamObserver<TokenResponse> responseObserver) {
    if (userService.existsByEmail(request.getEmail())) {
      throw new EntityAlreadyExistsException("Пользователь с таким email уже существует");
    }
    User user = webUserMapper.webToDomain(request);
    user.setPassword(authService.encodePassword(request.getPassword()));
    User savedUser = userService.saveNewUser(user);

    String accessToken = authService.generateAccessToken(savedUser);
    String refreshToken = authService.generateRefreshToken(savedUser);
    String tokenType = authService.getTokenPrefix().strip();

    responseObserver.onNext(TokenResponse.newBuilder()
        .setAccessToken(accessToken)
        .setRefreshToken(refreshToken)
        .setTokenType(tokenType)
        .build());
    responseObserver.onCompleted();
  }

  @Override
  public void login(LoginRequest request, StreamObserver<TokenResponse> responseObserver) {
    User user = userService.findByEmail(request.getEmail());
    if (!authService.checkPassword(request.getPassword(), user.getPassword())) {
      throw new BadCredentialsException("Неверный пароль");
    }

    String accessToken = authService.generateAccessToken(user);
    String refreshToken = authService.generateRefreshToken(user);
    String tokenType = authService.getTokenPrefix().strip();

    responseObserver.onNext(TokenResponse.newBuilder()
        .setAccessToken(accessToken)
        .setRefreshToken(refreshToken)
        .setTokenType(tokenType)
        .build());
    responseObserver.onCompleted();
  }

  @Override
  public void changePassword(ChangePasswordRequest request, StreamObserver<ActionResponse> responseObserver) {
    JwtAuthAccessResult authResult = GrpcConstants.AUTH_CONTEXT_KEY.get();
    User user = userService.findByEmail(authResult.email());

    if (!authService.checkPassword(request.getOldPassword(), user.getPassword())) {
      throw new IllegalArgumentException("Неверный старый пароль");
    }

    user.setPassword(authService.encodePassword(request.getNewPassword()));
    userService.updatePasswordHash(user);

    responseObserver.onNext(ActionResponse.newBuilder()
        .setMessage("Пароль успешно изменен")
        .build());
    responseObserver.onCompleted();
  }

  @Override
  public void resetPassword(ResetPasswordRequest request, StreamObserver<ActionResponse> responseObserver) {
    User user = userService.findByEmail(request.getEmail());
    String tempPassword = UUID.randomUUID().toString().substring(0, 8); //временный пароль

    user.setPassword(authService.encodePassword(tempPassword));
    userService.updatePasswordHash(user);

    log.info("""
        
        ====================================
        Симуляция отправки письма на Email: {}
        Пароль сброшен. Временный пароль: {}
        ====================================
        
        """, request.getEmail(), tempPassword);

    responseObserver.onNext(ActionResponse.newBuilder()
        .setMessage("Пароль успешно сброшен. Новый пароль отправлен на почту")
        .build());
    responseObserver.onCompleted();
  }

  @Override
  public void validateToken(Empty request, StreamObserver<ValidateTokenResponse> responseObserver) {
    JwtAuthAccessResult authResult = GrpcConstants.AUTH_CONTEXT_KEY.get();
    responseObserver.onNext(ValidateTokenResponse
        .newBuilder()
        .setIsValid(authResult.isValid()) // Небольшой костыль, до этого момента дойдут лишь валидные email
        .setEmail(authResult.email()) // Остальное отсечёт interceptor в виде исключения
        .build());
    responseObserver.onCompleted();
  }

  @Override
  public void updateAccessToken(RefreshTokenRequest request, StreamObserver<TokenResponse> responseObserver) {
    User user = validateRefreshTokenAndGetUser(request.getRefreshToken());

    String accessToken = authService.generateAccessToken(user);
    String tokenType = authService.getTokenPrefix().strip();

    responseObserver.onNext(TokenResponse.newBuilder()
        .setAccessToken(accessToken)
        .setRefreshToken(request.getRefreshToken())
        .setTokenType(tokenType)
        .build());
    responseObserver.onCompleted();

  }

  @Override
  public void updateRefreshToken(RefreshTokenRequest request, StreamObserver<TokenResponse> responseObserver) {
    User user = validateRefreshTokenAndGetUser(request.getRefreshToken());

    String accessToken = authService.generateAccessToken(user);
    String newRefreshToken = authService.generateRefreshToken(user);
    String tokenType = authService.getTokenPrefix().strip();

    responseObserver.onNext(TokenResponse.newBuilder()
        .setAccessToken(accessToken)
        .setRefreshToken(newRefreshToken)
        .setTokenType(tokenType)
        .build());
    responseObserver.onCompleted();
  }

  private User validateRefreshTokenAndGetUser(String refreshToken) {
    JwtAuthRefreshResult result = authService.validateRefreshToken(refreshToken);
    if (!result.isValid()) {
      throw new IllegalArgumentException("Невалидный refresh token");
    }
    return userService.findById(result.userId());
  }
}
