package ganisrum.backend.domain.service.impl;

import ganisrum.backend.datasource.repository.SupplierRepository;
import ganisrum.backend.domain.model.Address;
import ganisrum.backend.domain.model.Supplier;
import ganisrum.backend.domain.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository suppliersRepository;

    @Override
    public Supplier add(Supplier supplier) {
        return suppliersRepository.save(supplier);
    }

    @Override
    @Transactional
    public Address changeAddress(UUID productId, Address address) {
        return suppliersRepository.changeAddress(productId, address);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        suppliersRepository.delete(id);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Supplier> getAll() {
        return suppliersRepository.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Supplier getById(UUID id) {
        return suppliersRepository.getById(id);
    }

    @Override
    public boolean isExistById(UUID id) {
        return suppliersRepository.isExist(id);
    }
}
