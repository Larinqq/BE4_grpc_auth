package ganisrum.backend.datasource.mapper;


import ganisrum.backend.datasource.model.SupplierEntity;
import ganisrum.backend.domain.model.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AddressEntityMapper.class)
public interface SupplierEntityMapper {

    SupplierEntity toEntity(Supplier supplier);

    Supplier toDomain(SupplierEntity entity);
}
