package ganisrum.backend.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductCreateRequest(
        @Schema(description = "Название продукта", example = "Телевизор")
        @NotBlank(message = "Название не может быть пустым")
        @Size(min = 2, max = 50, message = "Название должно содержать от 2 до 50 символов")
        @Pattern(regexp = "^$|^[a-zA-Zа-яА-Я\\s]+$",
                message = "Название должно содержать только буквы (латиницу или кириллицу) и пробелы")
        String name,
        @Schema(description = "Категория товара", example = "Бытовая техника")
        @NotBlank(message = "Категория не может быть пустой")
        @Size(min = 2, max = 50, message = "Категория должна содержать от 2 до 50 символов")
        @Pattern(regexp = "^$|^[a-zA-Zа-яА-Я\\s]+$",
                message = "Категория должна содержать только буквы (латиницу или кириллицу) и пробелы")
        String category,
        @Schema(description = "Цена товара", example = "23999.59")
        @NotNull(message = "Цена не может быть пустой")
        @PositiveOrZero(message = "Цена должна быть больше или равна нулю")
        BigDecimal price,
        @NotNull(message = "Остаток не может быть пустым")
        @PositiveOrZero(message = "Остаток должно быть больше или равен нулю")
        @JsonProperty("available_stock")
        @Schema(description = "Остаток товара", example = "3")
        Integer availableStock,
        @NotNull(message = "Поставщик обязателен")
        @Valid
        @Schema(description = "Id поставщика товара", example = "89d700fc-827a-405e-b395-b9cc6874a648")
        UUID supplier) {
}
