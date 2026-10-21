package ganisrum.backend.infrastructure.mapper;

import ganisrum.backend.authserver.grpc.ActionResponse;
import ganisrum.backend.authserver.grpc.ChangePasswordRequest;
import ganisrum.backend.authserver.grpc.LoginRequest;
import ganisrum.backend.authserver.grpc.RefreshTokenRequest;
import ganisrum.backend.authserver.grpc.RegisterRequest;
import ganisrum.backend.authserver.grpc.ResetPasswordRequest;
import ganisrum.backend.authserver.grpc.TokenResponse;
import ganisrum.backend.authserver.grpc.ValidateTokenResponse;
import ganisrum.backend.web.dto.request.UserAuthRequest;
import ganisrum.backend.web.dto.request.UserChangePasswordRequest;
import ganisrum.backend.web.dto.request.UserPasswordRecoveryRequest;
import ganisrum.backend.web.dto.request.UserRegisterRequest;
import ganisrum.backend.web.dto.request.UserUpdateJwtTokenRequest;
import ganisrum.backend.web.dto.response.UserActionResponse;
import ganisrum.backend.web.dto.response.UserJwtTokenResponse;
import ganisrum.backend.web.dto.response.UserValidateTokenResponse;
import jakarta.validation.Valid;

public interface GrpcProtoMapper {
    LoginRequest dtoAuthToProto(UserAuthRequest request);

    RegisterRequest dtoRegisterToProto(UserRegisterRequest request);

    ResetPasswordRequest dtoResetPasswordToProto(UserPasswordRecoveryRequest request);

    UserJwtTokenResponse protoTokenResponseToDto(TokenResponse response);

    UserActionResponse protoActionResponseToDto(ActionResponse response);

    ChangePasswordRequest dtoChangePasswordToProto(@Valid UserChangePasswordRequest request);

    UserValidateTokenResponse protoToUserValidateTokenResponse(ValidateTokenResponse response);

    RefreshTokenRequest dtoUpdateTokenToProto(UserUpdateJwtTokenRequest request);
}
