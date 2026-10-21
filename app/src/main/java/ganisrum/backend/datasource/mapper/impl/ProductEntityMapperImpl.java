package ganisrum.backend.datasource.mapper.impl;

import ganisrum.backend.datasource.mapper.ProductEntityMapper;
import ganisrum.backend.datasource.model.ProductEntity;
import ganisrum.backend.datasource.repository.jpa.ImageRepositoryJpa;
import ganisrum.backend.datasource.repository.jpa.SupplierRepositoryJpa;
import ganisrum.backend.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ProductEntityMapperImpl implements ProductEntityMapper {
    private final SupplierRepositoryJpa supplierRepository;
    private final ImageRepositoryJpa imageRepository;

    @Override
    public Product toDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        Product product = new Product();
        product.setId(entity.getId());
        product.setName(entity.getName());
        product.setCategory(entity.getCategory());
        product.setPrice(entity.getPrice());
        product.setAvailableStock(entity.getAvailableStock());
        product.setLastUpdateDate(entity.getLastUpdateDate());

        if (entity.getSupplier() != null)
            product.setSupplier(entity.getSupplier().getId());

        if (entity.getImage() != null) {
            product.setImage(entity.getImage().getId());
        }

        return product;
    }

    @Override
    public ProductEntity toEntity(Product product) {
        if (product == null) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(product.getId());
        productEntity.setName(product.getName());
        productEntity.setCategory(product.getCategory());
        productEntity.setPrice(product.getPrice());
        productEntity.setAvailableStock(product.getAvailableStock());
        if (product.getLastUpdateDate() != null) {
            productEntity.setLastUpdateDate(product.getLastUpdateDate());
        }

        if (product.getSupplier() != null) {
            productEntity.setSupplier(supplierRepository.getReferenceById(product.getSupplier()));
        }

        if (product.getImage() != null) {
            productEntity.setImage(imageRepository.getReferenceById(product.getImage()));

        }
        return productEntity;
    }
}



