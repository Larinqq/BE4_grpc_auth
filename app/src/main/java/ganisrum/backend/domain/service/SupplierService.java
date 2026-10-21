package ganisrum.backend.domain.service;

import ganisrum.backend.domain.model.Address;
import ganisrum.backend.domain.model.Supplier;

import java.util.List;
import java.util.UUID;

public interface SupplierService {
    Supplier add(Supplier supplier);

    Address changeAddress(UUID productId, Address address);

    void delete(UUID id);

    List<Supplier> getAll();

    Supplier getById(UUID id);

    boolean isExistById(UUID id);
}
