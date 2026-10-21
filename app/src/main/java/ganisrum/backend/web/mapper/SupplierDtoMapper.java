package ganisrum.backend.web.mapper;

import ganisrum.backend.domain.model.Supplier;
import ganisrum.backend.web.dto.request.SupplierCreateRequest;
import ganisrum.backend.web.dto.response.SupplierResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AddressDtoMapper.class)
public interface SupplierDtoMapper {

    @Mapping(target = "phone", source = "phoneNumber")
    Supplier toDomain(SupplierCreateRequest supplier);

    SupplierResponse toDto(Supplier supplier);
}
