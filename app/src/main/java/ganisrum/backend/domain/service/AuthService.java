package ganisrum.backend.domain.service;

import ganisrum.backend.web.dto.request.UserAuthRequest;
import ganisrum.backend.web.dto.request.UserChangePasswordRequest;
import ganisrum.backend.web.dto.request.UserPasswordRecoveryRequest;
import ganisrum.backend.web.dto.request.UserRegisterRequest;
import ganisrum.backend.web.dto.request.UserUpdateJwtTokenRequest;
import ganisrum.backend.web.dto.response.UserActionResponse;
import ganisrum.backend.web.dto.response.UserJwtTokenResponse;
import ganisrum.backend.web.dto.response.UserValidateTokenResponse;
import org.apache.tomcat.websocket.AuthenticationException;


public interface AuthService {
    UserJwtTokenResponse register(UserRegisterRequest request);

    UserJwtTokenResponse login(UserAuthRequest request);

    UserValidateTokenResponse validateToken(String token) throws AuthenticationException;

    UserActionResponse changePassword(UserChangePasswordRequest request, String token) throws AuthenticationException;

    UserActionResponse recoverPassword(UserPasswordRecoveryRequest request);

    UserJwtTokenResponse updateAccessToken(UserUpdateJwtTokenRequest request);

    UserJwtTokenResponse updateRefreshToken(UserUpdateJwtTokenRequest request);

}


