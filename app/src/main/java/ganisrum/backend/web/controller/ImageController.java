package ganisrum.backend.web.controller;

import ganisrum.backend.annotations.swagger.ApiBadRequest;
import ganisrum.backend.annotations.swagger.ApiConflict;
import ganisrum.backend.annotations.swagger.ApiNotFound;
import ganisrum.backend.annotations.swagger.ApiServiceUnavailable;
import ganisrum.backend.annotations.swagger.ApiTooLarge;
import ganisrum.backend.annotations.swagger.ApiUnauthorized;
import ganisrum.backend.domain.model.Image;
import ganisrum.backend.domain.service.ImageService;
import ganisrum.backend.domain.service.ProductService;
import ganisrum.backend.web.dto.response.ImageResponse;
import ganisrum.backend.web.mapper.ImageDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Tag(name = "Изображения", description = "Операции с изображениями")
@Validated
@ApiNotFound
@RestController()
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@ApiUnauthorized
@ApiServiceUnavailable
public class ImageController {
    private final ImageService imageService;
    private final ImageDtoMapper imageDtoMapper;
    private final ProductService productService;


    @PutMapping(value = "/images/{imageId}", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(summary = "Заменить изображение по ID",
            description = "Заменяет изображение в базе данных")
    @ApiResponse(responseCode = "204", description = "Изображние изменено")
    @ApiBadRequest
    @ApiTooLarge
    @ApiNotFound
    public ResponseEntity<Void> updateImage(@PathVariable
                                            @Parameter(description = "UUID изображение",
                                                    example = "89d700fc-827a-405e-b395-b9cc6874a648",
                                                    required = true,
                                                    schema = @Schema(type = "string", format = "uuid")) UUID imageId,
                                            @RequestBody @Valid
                                            @Size(min = 5 * 1024, max = 5 * 1024 * 1024, message = "Размер изображения должен быть от 5 КБ до 5 МБ") byte[] newImage) {
        imageService.updateImage(imageId, newImage);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/images/{imageId}")
    @Operation(summary = "Удалить изображение по ID",
            description = "Удаляет изображение в базе данных")
    @ApiResponse(responseCode = "204", description = "Изображние удалено")
    @ApiBadRequest
    @ApiConflict
    public ResponseEntity<Void> deleteImage(@PathVariable
                                            @Parameter(description = "UUID изображения",
                                                    example = "89d700fc-827a-405e-b395-b9cc6874a648",
                                                    required = true,
                                                    schema = @Schema(type = "string", format = "uuid")) UUID imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping(value = "images/{imageId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(summary = "Получить изображение по ID",
            description = """
                    Возвращает бинарные данные изображения из базы данных в формате octet-stream.
                    Изображение передается как вложение (attachment) с именем файла равным UUID.
                    Используется для скачивания изображений клиентом.
                    """)
    @ApiResponse(responseCode = "200", description = "Изображние получено",
            content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                    schema = @Schema(type = "string", format = "binary",
                            description = "Бинарные данные изображения")))

    @ApiBadRequest
    public ResponseEntity<byte[]> getImage(@PathVariable
                                           @Parameter(description = "UUID изображение",
                                                   example = "89d700fc-827a-405e-b395-b9cc6874a648",
                                                   required = true,
                                                   schema = @Schema(type = "string", format = "uuid")) UUID imageId) {
        Image image = imageService.getImage(imageId);
        ImageResponse response = imageDtoMapper.toDto(image);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + response.id())
                .body(response.imageData());
    }

    @GetMapping(value = "/products/{productId}/images", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(summary = "Получить изображение по ID товара",
            description = """
                    Возвращает бинарные данные изображения товара из базы данных в формате octet-stream.
                    Изображение передается как вложение (attachment) с именем файла равным UUID.
                    Используется для скачивания изображений клиентом.
                    """)
    @ApiResponse(responseCode = "200", description = "Изображние получено", content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
            schema = @Schema(type = "string", format = "binary",
                    description = "Бинарные данные изображения")))

    @ApiBadRequest
    public ResponseEntity<byte[]> getProductImage(@PathVariable
                                                  @Parameter(description = "UUID товара",
                                                          example = "89d700fc-827a-405e-b395-b9cc6874a648",
                                                          required = true,
                                                          schema = @Schema(type = "string", format = "uuid")) UUID productId) {
        Image image = productService.getProductImageById(productId);
        ImageResponse response = imageDtoMapper.toDto(image);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + response.id())
                .body(response.imageData());
    }

    @PostMapping(value = "/products/{productId}/images", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(summary = "Добавить изображение к товару",
            description = """
                    Привязывает новое изображение к существующему товару.
                    Размер изображения должен быть от 5 КБ до 5 МБ.
                    В ответ возвращается заголовок Location с URL для получения добавленного изображения.
                    """)
    @ApiResponse(responseCode = "201", description = "Изображние добвлено",
            content = @Content(
                    schema = @Schema(hidden = true)),
            headers = @Header(name = "Location",
                    description = "URL для получения добавленного изображения",
                    example = "http://localhost:8080/api/v1/images/89d700fc-827a-405e-b395-b9cc6874a648"))
    @ApiBadRequest
    @ApiTooLarge
    @ApiConflict
    public ResponseEntity<Void> addImage(@PathVariable @Parameter(description = "UUID товара",
                                                 example = "89d700fc-827a-405e-b395-b9cc6874a648",
                                                 required = true,
                                                 schema = @Schema(type = "string", format = "uuid")) UUID productId,
                                         @RequestBody @Valid
                                         @Size(min = 5 * 1024, max = 5 * 1024 * 1024, message = "Размер изображения должен быть от 5 КБ до 5 МБ") byte[] image) {
        Image savedImage = productService.addImageToProduct(productId, imageDtoMapper.toDomain(image));
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/v1/images/{imageId}")
                .buildAndExpand(savedImage.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }
}
