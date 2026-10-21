package ganisrum.backend.datasource.repository;

import ganisrum.backend.domain.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    void delete(UUID id);

    Product findProductById(UUID id);

    List<Product> getAllProducts();

    int decreaseQuantity(UUID id, int quantity);

}
