package ganisrum.backend.web.controller;


import ganisrum.backend.annotations.swagger.ApiBadRequest;
import ganisrum.backend.annotations.swagger.ApiNotFound;
import ganisrum.backend.annotations.swagger.ApiServiceUnavailable;
import ganisrum.backend.annotations.swagger.ApiUnauthorized;
import ganisrum.backend.domain.model.Address;
import ganisrum.backend.domain.model.Client;
import ganisrum.backend.domain.service.ClientService;
import ganisrum.backend.web.dto.AddressDto;
import ganisrum.backend.web.dto.request.ClientCreateRequest;
import ganisrum.backend.web.dto.request.ClientPagesRequest;
import ganisrum.backend.web.dto.request.ClientSearchRequest;
import ganisrum.backend.web.dto.response.ClientPagesResponse;
import ganisrum.backend.web.dto.response.ClientResponse;
import ganisrum.backend.web.mapper.AddressDtoMapper;
import ganisrum.backend.web.mapper.ClientDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

import java.util.List;
import java.util.UUID;

@Tag(name = "Клиенты", description = "Операции с клиентами")
@Validated
@RestController()
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@ApiUnauthorized
@ApiServiceUnavailable
public class ClientController {
    private final ClientDtoMapper clientDtoMapper;
    private final AddressDtoMapper addressDtoMapper;
    private final ClientService clientService;


    @PostMapping()
    @Operation(summary = "Добавить клиента",
            description = "Возвращает созданный объект клиента")
    @ApiResponse(responseCode = "201", description = "Клиент добавлен",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ClientResponse.class)))
    @ApiBadRequest
    public ResponseEntity<ClientResponse> addClient(@RequestBody @Valid ClientCreateRequest claims) {
        Client respond = clientService.add(clientDtoMapper.toDomain(claims));
        return ResponseEntity.status(HttpStatus.CREATED).body(clientDtoMapper.toDto(respond));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить клиента по ID",
            description = "Удаляет клиента в базе данных")
    @ApiResponse(responseCode = "204", description = "Клиент удален")
    @ApiBadRequest
    @ApiNotFound
    public ResponseEntity<Void> deleteClient(@PathVariable
                                             @Parameter(description = "UUID клиента",
                                                     example = "89d700fc-827a-405e-b395-b9cc6874a648",
                                                     required = true,
                                                     schema = @Schema(type = "string", format = "uuid")) UUID id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Поиск клиентов по имени и/или фамилии",
            description = "Возвращает список клиентов, удовлетворящий условиям поиска")
    @ApiBadRequest
    @ApiResponse(responseCode = "200", description = "Получен список клиентов",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = ClientResponse.class))))
    public ResponseEntity<List<ClientResponse>> getClientByClaims(@ParameterObject @Valid ClientSearchRequest claims) {
        List<Client> clients = clientService.searchClientByClaims(claims.clientName(), claims.clientSurname());
        List<ClientResponse> clientsResponse = clients.stream().
                map(clientDtoMapper::toDto).toList();
        return ResponseEntity.ok(clientsResponse);
    }

    @GetMapping
    @Operation(summary = "Поиск клиентов с опциональными параметрами пагинации",
            description = "Возвращает список клиентов, удовлетворящий условиям поиска")
    @ApiBadRequest
    @ApiResponse(responseCode = "200", description = "Получен список клиентов",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = ClientPagesResponse.class))))
    public ResponseEntity<ClientPagesResponse<ClientResponse>> getClientPages(@ParameterObject @Valid ClientPagesRequest pageParams) {
        Page<Client> clients = clientService.searchWithPaginatition(pageParams.limit(), pageParams.offset());
        ClientPagesResponse<ClientResponse> response = clientDtoMapper.clientPageToDto(clients);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{clientId}/address")
    @Operation(summary = "Изменение адреса клиента",
            description = "Возвращает измененный адрес клиента")
    @ApiResponse(responseCode = "200", description = "Адрес изменен",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AddressDto.class)))
    @ApiBadRequest
    @ApiNotFound
    public ResponseEntity<AddressDto> changeAddress(@PathVariable
                                                    @Parameter(description = "UUID клиента",
                                                            example = "89d700fc-827a-405e-b395-b9cc6874a648",
                                                            required = true,
                                                            schema = @Schema(type = "string", format = "uuid")) UUID clientId, @RequestBody @Valid AddressDto address) {
        Address updated = clientService.changeAddress(clientId, addressDtoMapper.toDomain(address));
        return ResponseEntity.ok(addressDtoMapper.toDto(updated));
    }
}
