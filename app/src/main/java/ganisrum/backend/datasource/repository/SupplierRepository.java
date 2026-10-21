package ganisrum.backend.datasource.repository;

import ganisrum.backend.domain.model.Address;
import ganisrum.backend.domain.model.Supplier;

import java.util.List;
import java.util.UUID;

public interface SupplierRepository {

    Supplier save(Supplier supplier);

    Address changeAddress(UUID supplierId, Address newAddress);

    void delete(UUID id);

    List<Supplier> getAll();

    Supplier getById(UUID id);

    boolean isExist(UUID id);
}
