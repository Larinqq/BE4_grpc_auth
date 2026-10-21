package ganisrum.backend.infrastructure.mapper.impl;

import ganisrum.backend.authserver.grpc.ActionResponse;
import ganisrum.backend.authserver.grpc.ChangePasswordRequest;
import ganisrum.backend.authserver.grpc.LoginRequest;
import ganisrum.backend.authserver.grpc.RefreshTokenRequest;
import ganisrum.backend.authserver.grpc.RegisterRequest;
import ganisrum.backend.authserver.grpc.ResetPasswordRequest;
import ganisrum.backend.authserver.grpc.TokenResponse;
import ganisrum.backend.authserver.grpc.ValidateTokenResponse;
import ganisrum.backend.infrastructure.mapper.GrpcProtoMapper;
import ganisrum.backend.web.dto.request.UserAuthRequest;
import ganisrum.backend.web.dto.request.UserChangePasswordRequest;
import ganisrum.backend.web.dto.request.UserPasswordRecoveryRequest;
import ganisrum.backend.web.dto.request.UserRegisterRequest;
import ganisrum.backend.web.dto.request.UserUpdateJwtTokenRequest;
import ganisrum.backend.web.dto.response.UserActionResponse;
import ganisrum.backend.web.dto.response.UserJwtTokenResponse;
import ganisrum.backend.web.dto.response.UserValidateTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcProtoMapperImpl implements GrpcProtoMapper {
    @Override
    public LoginRequest dtoAuthToProto(UserAuthRequest request) {
        return LoginRequest.newBuilder()
                .setEmail(request.email())
                .setPassword(request.password())
                .build();
    }

    @Override
    public RegisterRequest dtoRegisterToProto(UserRegisterRequest request) {
        return RegisterRequest.newBuilder()
                .setEmail(request.email())
                .setFirstName(request.name())
                .setLastName(request.lastName())
                .setPassword(request.password())
                .setPhoneNumber(request.phoneNumber())
                .build();
    }


    @Override
    public ResetPasswordRequest dtoResetPasswordToProto(UserPasswordRecoveryRequest request) {
        return ResetPasswordRequest.newBuilder()
                .setEmail(request.email())
                .build();
    }

    @Override
    public UserJwtTokenResponse protoTokenResponseToDto(TokenResponse response) {
        return new UserJwtTokenResponse(
                response.getAccessToken(),
                response.getRefreshToken(),
                response.getTokenType()
        );
    }

    @Override
    public UserActionResponse protoActionResponseToDto(ActionResponse response) {
        return new UserActionResponse(response.getMessage());
    }

    @Override
    public ChangePasswordRequest dtoChangePasswordToProto(UserChangePasswordRequest request) {
        return ChangePasswordRequest.newBuilder()
                .setNewPassword(request.newPassword())
                .setOldPassword(request.oldPassword())
                .build();
    }

    @Override
    public UserValidateTokenResponse protoToUserValidateTokenResponse(ValidateTokenResponse response) {
        return new UserValidateTokenResponse(
                response.getIsValid(),
                response.getEmail()
        );
    }

    @Override
    public RefreshTokenRequest dtoUpdateTokenToProto(UserUpdateJwtTokenRequest request) {
        return RefreshTokenRequest.newBuilder()
                .setRefreshToken(request.refresh_token())
                .build();

    }
}
