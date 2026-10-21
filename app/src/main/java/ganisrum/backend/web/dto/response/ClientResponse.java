package ganisrum.backend.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ganisrum.backend.web.dto.AddressDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record ClientResponse(
        @Schema(description = "UUID Клиента", example = "89d700fc-827a-405e-b395-b9cc6874a648")
        UUID id,
        @JsonProperty("client_name")
        @Schema(description = "Имя клиента", example = "Никита")
        String clientName,
        @Schema(description = "Фамилия клиента", example = "Вакутагин")
        @JsonProperty("client_surname")
        String clientSurname,
        @Schema(description = "Дата рождения клиента", example = "30-01-1993")
        LocalDate birthday,
        @Schema(description = "Пол клиента, М или Ж", example = "М")
        String gender,
        @Schema(description = "Дата регистрации", example = "2026-08-26T23:03:28.409117Z")
        @JsonProperty("registration_date")
        Instant registrationDate,
        @Schema(description = "адрес клиента")
        AddressDto address
) {
}
