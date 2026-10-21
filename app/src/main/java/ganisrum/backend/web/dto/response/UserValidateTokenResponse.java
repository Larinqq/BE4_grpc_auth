package ganisrum.backend.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserValidateTokenResponse(
        @Schema(description = "Valid token or not", example = "true")
        @JsonProperty("is_valid")
        boolean isValid,
        @Schema(description = "user email", example = "abc@example.com")
        @JsonProperty("email")
        String email
) {
}
