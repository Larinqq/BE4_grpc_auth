package ganisrum.backend.web.dto.response;

import ganisrum.backend.web.dto.AddressDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;


public record SupplierResponse(
        @Schema(description = "UUID поставщика", example = "89d700fc-827a-405e-b395-b9cc6874a648")
        UUID id,
        @Schema(description = "Название поставщика", example = "Ozon")
        String name,
        @Schema(description = "Номер телефона в формате +7XXXXXXXXXX (10 цифр после +7) ", example = "=79999999999")
        String phone,
        @Schema(description = "Адрес Поставщика")
        AddressDto address) {

}
