package ganisrum.backend.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressDto(
        @Schema(description = "Страна", example = "Россия")
        @NotBlank(message = "Страна не может быть пустой")
        @Size(min = 2, max = 200, message = "Страна должна быть не меньше 2 и более 200 символов")
        String country,
        @NotBlank(message = "Город не может быть пустым")
        @Size(min = 2, max = 200, message = "Город должен быть не меньше 2 и более 200 символов")
        @Schema(description = "Город", example = "Киров")
        String city,
        @NotBlank(message = "Улица не может быть пустой")
        @Size(min = 2, max = 200, message = "Улица должна быть не меньше 2 и более 200 символов")
        @Schema(description = "Улица", example = "Пионерская")
        String street) {
}


