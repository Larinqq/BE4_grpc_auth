package ganisrum.backend.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
public class ProductResponse {
    @Schema(description = "UUID продукта", example = "89d700fc-827a-405e-b395-b9cc6874a648")
    UUID id;
    @Schema(description = "Название продуктв", example = "Телевизор")
    String name;
    @Schema(description = "Категория товара", example = "Бытовая техника")
    String category;
    @Schema(description = "Цена товара", example = "23999.59")
    BigDecimal price;
    @JsonProperty("available_stock")
    @Schema(description = "Остаток товара", example = "3")
    Integer availableStock;
    @JsonProperty("last_update_date")
    @Schema(description = "Дата обновления информации", example = "2026-08-23T21:39:35.398084Z")
    Instant lastUpdateDate;
    @Schema(description = "UUID поставщика", example = "89d700fc-827a-405e-b395-b9cc6874a648")
    UUID supplier;
    @Schema(description = "UUID изображения", example = "89d700fc-827a-405e-b395-b9cc6874a648")
    UUID image;
}
