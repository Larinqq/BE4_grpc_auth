package ganisrum.backend.datasource.repository.Impl;

import ganisrum.backend.datasource.mapper.AddressEntityMapper;
import ganisrum.backend.datasource.mapper.SupplierEntityMapper;
import ganisrum.backend.datasource.model.AddressEntity;
import ganisrum.backend.datasource.model.SupplierEntity;
import ganisrum.backend.datasource.repository.SupplierRepository;
import ganisrum.backend.datasource.repository.jpa.SupplierRepositoryJpa;
import ganisrum.backend.domain.model.Address;
import ganisrum.backend.domain.model.Supplier;
import ganisrum.backend.exceptions.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SupplierRepositoryImpl implements SupplierRepository {
    private final SupplierRepositoryJpa repository;
    private final SupplierEntityMapper supplierEntityMapper;
    private final AddressEntityMapper addressEntityMapper;

    @Override
    public Supplier save(Supplier supplier) {
        SupplierEntity savedEntity = repository.save(supplierEntityMapper.toEntity(supplier));
        return supplierEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Address changeAddress(UUID supplierId, Address newAddress) {
        SupplierEntity supplierEntity = findIfExistById(supplierId);
        AddressEntity newAddressEntity = addressEntityMapper.toEntity(newAddress);
        newAddressEntity.setId(supplierEntity.getAddress().getId());
        supplierEntity.setAddress(newAddressEntity);
        SupplierEntity savedEntity = repository.save(supplierEntity);
        return addressEntityMapper.toDomain(savedEntity.getAddress());

    }

    @Override
    public void delete(UUID id) {
        SupplierEntity supplierEntity = findIfExistById(id);
        if (repository.hasProducts(id))
            throw new BusinessLogicException("Невозможно удалить поставщика, т.к он привязан к товарам");
        repository.delete(supplierEntity);
    }

    @Override
    public List<Supplier> getAll() {
        return repository.findAll().stream()
                .map(supplierEntityMapper::toDomain).toList();
    }

    @Override
    public Supplier getById(UUID id) {
        return supplierEntityMapper.toDomain(findIfExistById(id));
    }

    private SupplierEntity findIfExistById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Поставщик с id: " + id + " не существует"));
    }

    @Override
    public boolean isExist(UUID id) {
        return repository.existsById(id);
    }
}
