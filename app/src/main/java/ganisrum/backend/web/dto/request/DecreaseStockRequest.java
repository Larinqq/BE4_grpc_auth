package ganisrum.backend.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DecreaseStockRequest(
        @NotNull(message = "Уменьшаемое кол-во не может быть пустым")
        @Min(value = 1, message = "Кол-во должно быть больше ноля")
        @Schema(description = "Уменьшаемое кол-во", example = "2")
        Integer quantity

) {
}
