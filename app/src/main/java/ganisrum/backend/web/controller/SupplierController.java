package ganisrum.backend.web.controller;


import ganisrum.backend.annotations.swagger.ApiBadRequest;
import ganisrum.backend.annotations.swagger.ApiConflict;
import ganisrum.backend.annotations.swagger.ApiNotFound;
import ganisrum.backend.annotations.swagger.ApiServiceUnavailable;
import ganisrum.backend.annotations.swagger.ApiUnauthorized;
import ganisrum.backend.domain.model.Address;
import ganisrum.backend.domain.model.Supplier;
import ganisrum.backend.domain.service.SupplierService;
import ganisrum.backend.web.dto.AddressDto;
import ganisrum.backend.web.dto.request.SupplierCreateRequest;
import ganisrum.backend.web.dto.response.SupplierResponse;
import ganisrum.backend.web.mapper.AddressDtoMapper;
import ganisrum.backend.web.mapper.SupplierDtoMapper;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "Поставщики", description = "Операции с поставщиками")
@RestController()
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
@ApiUnauthorized
@ApiServiceUnavailable
public class SupplierController {
    private final SupplierService supplierService;
    private final AddressDtoMapper addressDtoMapper;
    private final SupplierDtoMapper supplierDtoMapper;


    @PostMapping
    @Operation(summary = "Добавить поставщика",
            description = "Возвращает созданный объект поставщика")
    @ApiResponse(responseCode = "201", description = "Постващик добавлен",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = SupplierResponse.class)))
    @ApiBadRequest
    public ResponseEntity<SupplierResponse> add(@RequestBody @Valid SupplierCreateRequest supplier) {
        Supplier response = supplierService.add(supplierDtoMapper.toDomain(supplier));
        return ResponseEntity.status(HttpStatus.CREATED).body(supplierDtoMapper.toDto(response));
    }

    @PutMapping("/{supplierId}/address")
    @Operation(summary = "Изменение адреса поставщика",
            description = "Возвращает измененный адрес поставщика")
    @ApiResponse(responseCode = "200", description = "Адрес изменен",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AddressDto.class)))
    @ApiBadRequest
    @ApiNotFound
    public ResponseEntity<AddressDto> changeAddress(@PathVariable
                                                    @Parameter(description = "UUID поставщика   ",
                                                            example = "89d700fc-827a-405e-b395-b9cc6874a648",
                                                            required = true,
                                                            schema = @Schema(type = "string", format = "uuid")) UUID supplierId, @RequestBody @Valid AddressDto address) {
        Address updated = supplierService.changeAddress(supplierId, addressDtoMapper.toDomain(address));
        return ResponseEntity.ok(addressDtoMapper.toDto(updated));
    }

    @DeleteMapping("/{supplierId}")
    @Operation(summary = "Удалить поствщика по ID",
            description = "Удаляет поствщика в базе данных")
    @ApiResponse(responseCode = "204", description = "Поставщик удален")
    @ApiBadRequest
    @ApiNotFound
    @ApiConflict
    public ResponseEntity<Void> delete(@PathVariable
                                       @Parameter(description = "UUID поставщика",
                                               example = "89d700fc-827a-405e-b395-b9cc6874a648",
                                               required = true,
                                               schema = @Schema(type = "string", format = "uuid")) UUID supplierId) {
        supplierService.delete(supplierId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping()
    @Operation(summary = "Получение списка поставщиков",
            description = "Возвращает список поставщиков или пустой список, если поставщиков нет")
    @ApiBadRequest
    @ApiResponse(responseCode = "200", description = "Получен список клиентов",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = SupplierResponse.class))))
    public ResponseEntity<List<SupplierResponse>> getAllSuppliers() {
        List<SupplierResponse> response = supplierService.getAll().stream()
                .map(supplierDtoMapper::toDto)
                .toList();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{supplierId}")
    @Operation(summary = "Получение поставщика по UUID", description = "Возвращает отбъект поставщика")
    @ApiResponse(responseCode = "200", description = "Поставщик получен",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SupplierResponse.class)))
    @ApiBadRequest
    @ApiNotFound
    public ResponseEntity<SupplierResponse> getSupplierById(@PathVariable
                                                            @Parameter(description = "UUID поставщика",
                                                                    example = "89d700fc-827a-405e-b395-b9cc6874a648",
                                                                    required = true,
                                                                    schema = @Schema(type = "string", format = "uuid")) UUID supplierId) {
        Supplier response = supplierService.getById(supplierId);
        return ResponseEntity.ok(supplierDtoMapper.toDto(response));
    }
}
