package ganisrum.backend.annotations.swagger;

import ganisrum.backend.exceptions.errors.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(responseCode = "404", description = "Запрашиваемый ресурс не найден",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
public @interface ApiNotFound {
}