package ganisrum.backend.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public record UserChangePasswordRequest(
        @Schema(description = "Пароль (от 4 до 10 символов)")
        @Size(
                min = 4,
                max = 10,
                message = "Пароль должен содержать от 4 до 10 символов"
        )
        @JsonProperty("old_password")
        String oldPassword,

        @Schema(description = "Пароль (от 4 до 10 символов)")
        @Size(
                min = 4,
                max = 10,
                message = "Пароль должен содержать от 4 до 10 символов"
        )
        @JsonProperty("new_password")
        String newPassword
) {
}
