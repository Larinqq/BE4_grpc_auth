package ganisrum.backend.datasource.mapper;


import ganisrum.backend.datasource.model.ProductEntity;
import ganisrum.backend.domain.model.Product;


public interface ProductEntityMapper {

    ProductEntity toEntity(Product product);

    Product toDomain(ProductEntity entity);
}
