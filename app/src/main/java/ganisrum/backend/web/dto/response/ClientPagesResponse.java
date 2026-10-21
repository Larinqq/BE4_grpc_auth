package ganisrum.backend.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ClientPagesResponse<T>(
        @Schema(description = "Содержание запроса")
        List<T> content,
        @Schema(description = "Номер страницы", example = "2")
        int pageNumber,
        @Schema(description = "Кол-во элементов на странице", example = "3")
        int pageSize,
        @Schema(description = "Кол-во элементов на страницу", example = "3")
        long totalElements,
        @Schema(description = "Кол-во элементов на страницу", example = "3")
        int totalPages,
        @Schema(description = "Последняя страница", example = "true")
        boolean last,
        @Schema(description = "Первая страница", example = "true")
        boolean first) {
}
