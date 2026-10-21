package ganisrum.backend.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest(
        @Schema(description = "Email пользователя", example = "user@example.com")
        @Email(message = "Некорректный формат email")
        @NotBlank(message = "Email обязателен")
        @JsonProperty("email")
        String email,

        @Schema(description = "Имя ", example = "Никита")
        @NotBlank(message = "Имя не может быть пустым")
        @Size(min = 2, max = 50, message = "Имя должно содержать от 2 до 50 символов")
        @Pattern(regexp = "^$|^[a-zA-Zа-яА-Я\\s]+$",
                message = "Имя должно содержать только буквы (латиницу или кириллицу) и пробелы")
        @JsonProperty("name")
        String name,

        @Schema(description = "Фамилия", example = "Вакутагин")
        @NotBlank(message = "Фамилия не может быть пустой")
        @Size(min = 2, max = 50, message = "Фамилия должна содержать от 2 до 50 символов")
        @Pattern(
                regexp = "^$|^[a-zA-Zа-яА-Я\\s\\-]+$",
                message = "Фамилия должна содержать только буквы (латиницу или кириллицу), дефис и пробелы"
        )
        @JsonProperty("surname")
        String lastName,

        @Schema(description = "Номер телефона в формате +7XXXXXXXXXX (10 цифр после +7) ", example = "+79999999999")
        @Pattern(regexp = "^\\+7\\d{10}$", message = "Номер телефона должен быть в формате +7XXXXXXXXXX (10 цифр после +7)")
        @NotBlank(message = "Телефон обязателен")
        @JsonProperty("phone_number")
        String phoneNumber,

        @Schema(description = "Пароль (от 4 до 10 символов)")
        @Size(
                min = 4,
                max = 10,
                message = "Пароль должен содержать от 4 до 10 символов"
        )
        @JsonProperty("password")
        String password
) {
}
