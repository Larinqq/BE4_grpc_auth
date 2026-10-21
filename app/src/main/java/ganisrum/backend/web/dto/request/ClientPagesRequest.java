package ganisrum.backend.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Optional;

public record ClientPagesRequest(
        @Min(value = 1, message = "Количество записей на странице должно быть от 1 до 50")
        @Max(value = 50, message = "Количество записей на странице должно быть от 1 до 50")
        @Schema(description = "Лимит записей на странице", example = "3")
        Integer limit,

        @PositiveOrZero(message = "Смещение не может быть отрицательным")
        @Schema(description = "Смещение на заданное кол-во записей", example = "2")
        Integer offset
) {

    public ClientPagesRequest {
        limit = Optional.ofNullable(limit).orElse(10);
        offset = Optional.ofNullable(offset).orElse(0);
    }
}
