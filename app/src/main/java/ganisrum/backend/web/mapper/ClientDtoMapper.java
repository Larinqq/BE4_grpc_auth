package ganisrum.backend.web.mapper;

import ganisrum.backend.domain.model.Client;
import ganisrum.backend.web.dto.request.ClientCreateRequest;
import ganisrum.backend.web.dto.response.ClientPagesResponse;
import ganisrum.backend.web.dto.response.ClientResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = AddressDtoMapper.class)
public interface ClientDtoMapper {


    ClientResponse toDto(Client client);

    Client toDomain(ClientCreateRequest client);

    default ClientPagesResponse<ClientResponse> clientPageToDto(Page<Client> clientPage) {
        if (clientPage == null) {
            return null;
        }

        return new ClientPagesResponse<>(
                clientPage.getContent().stream().map(this::toDto).toList(),
                clientPage.getNumber(),
                clientPage.getSize(),
                clientPage.getTotalElements(),
                clientPage.getTotalPages(),
                clientPage.isLast(),
                clientPage.isFirst()
        );
    }
}
