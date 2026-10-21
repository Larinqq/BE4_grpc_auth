package ganisrum.backend.web.controller;

import ganisrum.backend.annotations.swagger.ApiBadRequest;
import ganisrum.backend.annotations.swagger.ApiConflict;
import ganisrum.backend.annotations.swagger.ApiNotFound;
import ganisrum.backend.annotations.swagger.ApiServiceUnavailable;
import ganisrum.backend.annotations.swagger.ApiUnauthorized;
import ganisrum.backend.domain.model.Product;
import ganisrum.backend.domain.service.ProductService;
import ganisrum.backend.web.dto.request.DecreaseStockRequest;
import ganisrum.backend.web.dto.request.ProductCreateRequest;
import ganisrum.backend.web.dto.response.ProductResponse;
import ganisrum.backend.web.mapper.ProductDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController()
@Tag(name = "Товары", description = "Операции с товарами")
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
@ApiUnauthorized
@ApiServiceUnavailable
public class ProductController {
    private final ProductService productService;
    private final ProductDtoMapper productDtoMapper;

    @PostMapping()
    @Operation(summary = "Добавить товар",
            description = "Добавляет товар в базу данных по заданной структуре")
    @ApiResponse(responseCode = "201", description = "Товар добавлен",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductResponse.class)))
    @ApiBadRequest
    @ApiNotFound
    public ResponseEntity<ProductResponse> addProduct(@RequestBody @Valid ProductCreateRequest product) {
        Product addedProduct = productService.addProduct(productDtoMapper.toDomain(product));
        return ResponseEntity.status(HttpStatus.CREATED).body(productDtoMapper.toDto(addedProduct));
    }

    @PatchMapping("/{productId}/decrease_stock")
    @Operation(summary = "Уменьшить остаток товара по UUID ",
            description = "Уменьшить доступный остаток товара переданного по UUID")
    @ApiResponse(responseCode = "204", description = "Остаток уменьшен")
    @ApiNotFound
    @ApiBadRequest
    @ApiConflict
    public ResponseEntity<Void> decreaseStock(@PathVariable
                                              @Parameter(description = "UUID товара",
                                                      example = "89d700fc-827a-405e-b395-b9cc6874a648",
                                                      required = true,
                                                      schema = @Schema(type = "string", format = "uuid")) UUID productId, @Valid @RequestBody DecreaseStockRequest decreaseParams) {
        productService.decreaseQuantity(productId, decreaseParams.quantity());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Получить товар по UUID",
            description = "Получить информацию о товар по UUID продукта")
    @ApiResponse(responseCode = "200", description = "Детальная информация о продукте",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductResponse.class)))
    @ApiBadRequest
    @ApiNotFound
    public ResponseEntity<ProductResponse> getProductById(@PathVariable
                                                          @Parameter(description = "UUID товара",
                                                                  example = "89d700fc-827a-405e-b395-b9cc6874a648",
                                                                  required = true,
                                                                  schema = @Schema(type = "string", format = "uuid")) UUID productId) {
        Product product = productService.findProductById(productId);
        return ResponseEntity.ok(productDtoMapper.toDto(product));
    }

    @GetMapping()
    @Operation(summary = "Получение списка товаров",
            description = "Возвращает список товаров или пустой список если товаров нет")
    @ApiBadRequest
    @ApiResponse(responseCode = "200", description = "Получен список товаров",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = ProductResponse.class))))
    public ResponseEntity<List<ProductResponse>> getProductList() {
        List<Product> product = productService.getAllProducts();
        List<ProductResponse> response = product.stream()
                .map(productDtoMapper::toDto)
                .toList();
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{productId}")
    @Operation(summary = "Удалить товар по ID",
            description = "Удаляет товар в базе данных")
    @ApiResponse(responseCode = "204", description = "Товар удален")
    @ApiBadRequest
    @ApiNotFound
    @ApiConflict
    public ResponseEntity<Void> delete(@PathVariable
                                       @Parameter(description = "UUID товара",
                                               example = "89d700fc-827a-405e-b395-b9cc6874a648",
                                               required = true,
                                               schema = @Schema(type = "string", format = "uuid")) UUID productId) {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }


}
