package ganisrum.backend.web.mapper;

import ganisrum.backend.domain.model.Address;
import ganisrum.backend.web.dto.AddressDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressDtoMapper {

    Address toDomain(AddressDto addressDto);

    AddressDto toDto(Address address);
}
