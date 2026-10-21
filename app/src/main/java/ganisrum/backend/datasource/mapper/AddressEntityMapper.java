package ganisrum.backend.datasource.mapper;

import ganisrum.backend.datasource.model.AddressEntity;
import ganisrum.backend.domain.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressEntityMapper {


    AddressEntity toEntity(Address address);

    Address toDomain(AddressEntity addressEntity);

}
