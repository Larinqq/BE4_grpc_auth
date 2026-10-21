package ganisrum.backend.datasource.repository.Impl;

import ganisrum.backend.datasource.mapper.ProductEntityMapper;
import ganisrum.backend.datasource.model.ProductEntity;
import ganisrum.backend.datasource.repository.ProductRepository;
import ganisrum.backend.datasource.repository.jpa.ProductRepositoryJpa;
import ganisrum.backend.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductRepositoryJpa productRepositoryJpa;
    private final ProductEntityMapper productEntityMapper;

    @Override
    public Product save(Product product) {
        ProductEntity entity = productEntityMapper.toEntity(product);
        ProductEntity saved = productRepositoryJpa.save(entity);
        return productEntityMapper.toDomain(saved);
    }


    @Override
    public void delete(UUID id) {
        ProductEntity entity = findIfExistById(id);
        productRepositoryJpa.delete(entity);

    }

    @Override
    public Product findProductById(UUID id) {
        ProductEntity entity = findIfExistById(id);
        return productEntityMapper.toDomain(entity);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepositoryJpa.findAll().stream()
                .map(productEntityMapper::toDomain)
                .toList();
    }


    private ProductEntity findIfExistById(UUID id) {
        return productRepositoryJpa.findById(id).orElseThrow(() -> new NoSuchElementException("Продукт с id: " + id + " не существует"));
    }

    @Override
    public int decreaseQuantity(UUID id, int quantity) {
        return productRepositoryJpa.decreaseQuantity(id, quantity);
    }

}
