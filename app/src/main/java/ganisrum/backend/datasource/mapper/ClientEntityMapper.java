package ganisrum.backend.datasource.mapper;

import ganisrum.backend.datasource.model.ClientEntity;
import ganisrum.backend.domain.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AddressEntityMapper.class)
public interface ClientEntityMapper {


    ClientEntity toEntity(Client client);

    Client toDomain(ClientEntity clientEntity);

}
