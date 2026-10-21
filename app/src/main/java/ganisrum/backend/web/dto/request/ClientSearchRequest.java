package ganisrum.backend.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

public record ClientSearchRequest(
        @Pattern(regexp = "^$|^[a-zA-Zа-яА-Я\\s]+$",
                message = "Имя должно содержать только буквы и пробелы")
        @Schema(description = "Имя клиента", example = "Кирилл")

        String clientName,

        @Pattern(regexp = "^$|^[a-zA-Zа-яА-Я\\s\\-]+$",
                message = "Фамилия должна содержать только буквы, пробелы и дефис")
        @Schema(description = "Фамилия клиента", example = "Агапов")
        String clientSurname

) {
}
