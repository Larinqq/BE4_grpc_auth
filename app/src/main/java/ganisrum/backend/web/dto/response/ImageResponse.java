package ganisrum.backend.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record ImageResponse(
        @Schema(description = "UUID изображения", example = "89d700fc-827a-405e-b395-b9cc6874a648")
        UUID id,
        @Schema(description = "byte изображения", example = "40 20 31.......")
        byte[] imageData
) {
}
