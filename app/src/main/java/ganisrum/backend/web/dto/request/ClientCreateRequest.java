package ganisrum.backend.web.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import ganisrum.backend.web.dto.AddressDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ClientCreateRequest(
        @Schema(description = "Имя клиента", example = "Никита")
        @NotBlank(message = "Имя не может быть пустым")
        @Size(min = 2, max = 50, message = "Имя должно содержать от 2 до 50 символов")
        @Pattern(regexp = "^$|^[a-zA-Zа-яА-Я\\s]+$",
                message = "Имя должно содержать только буквы (латиницу или кириллицу) и пробелы")
        @JsonProperty("client_name")
        String clientName,

        @Schema(description = "Фамилия клиента", example = "Вакутагин")
        @NotBlank(message = "Фамилия не может быть пустой")
        @Size(min = 2, max = 50, message = "Фамилия должна содержать от 2 до 50 символов")
        @Pattern(
                regexp = "^$|^[a-zA-Zа-яА-Я\\s\\-]+$",
                message = "Фамилия должна содержать только буквы (латиницу или кириллицу), дефис и пробелы"
        )
        @JsonProperty("client_surname")
        String clientSurname,

        @Schema(description = "Дата рождения клиента", example = "30-01-1993")
        @NotNull(message = "Дата рождения не может быть пустой")
        @Past(message = "Дата рождения должна быть в прошлом")
        @JsonFormat(pattern = "dd-MM-yyyy")
        @JsonProperty("birthday")
        LocalDate birthday,

        @Schema(description = "Пол клиента, М или Ж", example = "М")
        @Pattern(regexp = "^[МЖ]$", message = "Пол может быть только М или Ж")
        String gender,

        @Schema(description = "адрес клиента")
        @Valid
        @NotNull(message = "Адрес обязателен")
        AddressDto address) {

}
