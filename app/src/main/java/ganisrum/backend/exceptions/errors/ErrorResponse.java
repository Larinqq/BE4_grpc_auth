package ganisrum.backend.exceptions.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Instant;

@Data
@Schema(description = "Базовый ответ при ошибке")
public class ErrorResponse {

    @Schema(description = "HTTP статус", example = "один из вариантов ошибки {401,404,409,413,503}")
    private Integer status;

    @Schema(description = "Краткое описание ошибки", example = "Not Found")
    private String title;

    @Schema(description = "Детальное описание ошибки", example = "Продукт с id: 1bc9a68b-9ccd-48cb-a258-0b9baae4c512 не существует")
    private String detail;

    @Schema(description = "Путь запроса", example = "/api/v1/products/1bc9a68b-9ccd-48cb-a258-0b9baae4c512/images")
    private String instance;

    @Schema(description = "Время возникновения ошибки", example = "2026-08-26T20:03:43.024602395Z")
    private Instant timestamp;
}