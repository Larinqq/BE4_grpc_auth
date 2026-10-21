package ganisrum.backend.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserActionResponse(
        @Schema(description = "Сообщение о состоянии", example = "Успех")
        @JsonProperty("message")
        String message
) {
}
