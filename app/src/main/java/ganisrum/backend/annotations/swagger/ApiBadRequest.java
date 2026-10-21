package ganisrum.backend.annotations.swagger;

import ganisrum.backend.exceptions.errors.ErrorResponse;
import ganisrum.backend.exceptions.errors.ValidationErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(responseCode = "400", description = "Неправильный запрос или некорректные данные",
        content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {ValidationErrorResponse.class, ErrorResponse.class})))
public @interface ApiBadRequest {
}