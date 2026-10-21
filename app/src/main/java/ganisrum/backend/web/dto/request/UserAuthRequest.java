package ganisrum.backend.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserAuthRequest(
        @Schema(description = "Email пользователя", example = "user@example.com")
        @Email(message = "Некорректный формат email")
        @NotBlank(message = "Email обязателен")
        @JsonProperty("email")
        String email,

        @Schema(description = "Пароль (от 4 до 10 символов)")
        @Size(
                min = 4,
                max = 10,
                message = "Пароль должен содержать от 4 до 10 символов"
        )
        @JsonProperty("password")
        String password
) {
}
