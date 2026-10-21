package ganisrum.backend.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import ganisrum.backend.web.dto.AddressDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springdoc.core.annotations.ParameterObject;

public record SupplierCreateRequest(
        @Schema(description = "Название поставщика", example = "Ozon")
        @NotBlank(message = "Название не может быть пустым")
        @Size(min = 2, max = 50, message = "Имя должно содержать от 2 до 50 символов")
        @Pattern(regexp = "^$|^[a-zA-Zа-яА-Я\\s]+$",
                message = "Название должно содержать только буквы (латиницу или кириллицу) и пробелы")
        String name,
        @Valid
        @NotNull(message = "Адрес обязателен")
        @Schema(description = "Адрес поставщика")
        @ParameterObject
        AddressDto address,

        @Pattern(regexp = "^\\+7\\d{10}$", message = "Номер телефона должен быть в формате +7XXXXXXXXXX (10 цифр после +7)")
        @NotBlank(message = "Телефон обязателен")
        @JsonProperty("phone_number")
        @Schema(description = "Номер телефона в формате +7XXXXXXXXXX (10 цифр после +7) ", example = "+79999999999")
        String phoneNumber) {
}
