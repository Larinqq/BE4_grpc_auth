package ganisrum.backend.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserPasswordRecoveryRequest(
        @Schema(description = "Email пользователя", example = "user@example.com")
        @Email(message = "Некорректный формат email")
        @NotBlank(message = "Email обязателен")
        @JsonProperty("email")
        String email
) {
}