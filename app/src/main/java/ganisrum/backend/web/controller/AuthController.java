package ganisrum.backend.web.controller;


import ganisrum.backend.annotations.security.PublicApi;
import ganisrum.backend.annotations.swagger.ApiBadRequest;
import ganisrum.backend.annotations.swagger.ApiConflict;
import ganisrum.backend.annotations.swagger.ApiNotFound;
import ganisrum.backend.annotations.swagger.ApiServiceUnavailable;
import ganisrum.backend.annotations.swagger.ApiUnauthorized;
import ganisrum.backend.domain.service.AuthService;
import ganisrum.backend.web.dto.request.UserAuthRequest;
import ganisrum.backend.web.dto.request.UserChangePasswordRequest;
import ganisrum.backend.web.dto.request.UserPasswordRecoveryRequest;
import ganisrum.backend.web.dto.request.UserRegisterRequest;
import ganisrum.backend.web.dto.request.UserUpdateJwtTokenRequest;
import ganisrum.backend.web.dto.response.UserActionResponse;
import ganisrum.backend.web.dto.response.UserJwtTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Пользователи", description = "Действия с пользователями")
@Validated
@RestController()
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@ApiServiceUnavailable
public class AuthController {
    private final AuthService authService;
    private final HttpServletRequest httpServletRequest;


    @SecurityRequirements
    @PostMapping("/register")
    @Operation(summary = "Добавить пользователя",
            description = "Возвращает токены доступа")
    @ApiResponse(responseCode = "201", description = "Пользователь добавлен",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserJwtTokenResponse.class)))
    @ApiBadRequest
    @ApiConflict
    @PublicApi
    public ResponseEntity<UserJwtTokenResponse> signUp(@Valid @RequestBody UserRegisterRequest request) {
        UserJwtTokenResponse responseToken = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseToken);

    }

    @SecurityRequirements
    @PostMapping()
    @Operation(summary = "Пройти авторизацию",
            description = "Возвращает токены доступа")
    @ApiResponse(responseCode = "200", description = "Пользователь авторизован",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserJwtTokenResponse.class)))
    @ApiBadRequest
    @PublicApi
    @ApiUnauthorized
    @ApiNotFound
    public ResponseEntity<UserJwtTokenResponse> login(@Valid @RequestBody UserAuthRequest request) {
        UserJwtTokenResponse tokenResponse = authService.login(request);
        return ResponseEntity.ok(tokenResponse);
    }

    @SecurityRequirements
    @PostMapping("/reset")
    @Operation(summary = "Восстановить пароль",
            description = "Отправляет восстановленный пароль на почту")
    @ApiResponse(responseCode = "200", description = "Пароль отправлен на почту",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserActionResponse.class)))
    @ApiBadRequest
    @ApiNotFound
    @PublicApi
    public ResponseEntity<UserActionResponse> resetPassword(@Valid @RequestBody UserPasswordRecoveryRequest request) {
        UserActionResponse response = authService.recoverPassword(request);
        return ResponseEntity.ok(response);

    }


    @PostMapping("/change-password")
    @Operation(summary = "Изменить пароль",
            description = "Обновляет пароль")
    @ApiResponse(responseCode = "200", description = "Пароль изменен",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserActionResponse.class)))
    @ApiBadRequest
    @ApiUnauthorized
    public ResponseEntity<UserActionResponse> changePassword(@Valid @RequestBody UserChangePasswordRequest request) throws AuthenticationException {
        String authHeader = httpServletRequest.getHeader("Authorization");
        UserActionResponse response = authService.changePassword(request, authHeader);
        return ResponseEntity.ok(response);
    }

    @SecurityRequirements
    @PostMapping("/update-access")
    @Operation(summary = "Обновить access токен",
            description = "Возвращает обновленный access токен доступа, остальные без изменений")
    @ApiResponse(responseCode = "200", description = "Токен обновлен",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserJwtTokenResponse.class)))
    @ApiBadRequest
    @ApiUnauthorized
    @PublicApi
    public ResponseEntity<UserJwtTokenResponse> updateAccess(
            @Valid @RequestBody UserUpdateJwtTokenRequest token) {
        return ResponseEntity.ok(authService.updateAccessToken(token));
    }


    @SecurityRequirements
    @PostMapping("/update-refresh")
    @Operation(summary = "Обновить refresh token",
            description = "Возвращает обновленные токены доступа")
    @ApiResponse(responseCode = "200", description = "Токены обновлены",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserJwtTokenResponse.class)))
    @ApiBadRequest
    @ApiUnauthorized
    @PublicApi
    public ResponseEntity<UserJwtTokenResponse> updateRefresh(@Valid @RequestBody UserUpdateJwtTokenRequest token) {
        return ResponseEntity.ok(authService.updateRefreshToken(token));
    }


}
