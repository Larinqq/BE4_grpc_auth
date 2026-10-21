package ganisrum.backend.exceptions.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Ошибка валидации полей")
public class ValidationErrorResponse extends ErrorResponse {

    @Schema(description = "Поля с ошибками валидации",
            example = """
                    {
                        "address": "Адрес обязателен",
                        "phoneNumber": "Неверный формат номера телефона"
                    }""")
    private Map<String, String> invalidFields;
}
