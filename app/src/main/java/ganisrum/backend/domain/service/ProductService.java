package ganisrum.backend.domain.service;

import ganisrum.backend.domain.model.Image;
import ganisrum.backend.domain.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    Product addProduct(Product product);

    void decreaseQuantity(UUID id, int quantity);

    void delete(UUID id);

    Product findProductById(UUID id);

    List<Product> getAllProducts();

    Image getProductImageById(UUID id);

    Image addImageToProduct(UUID productId, Image image);


}
