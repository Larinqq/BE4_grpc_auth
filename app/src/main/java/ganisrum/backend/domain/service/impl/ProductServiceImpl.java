package ganisrum.backend.domain.service.impl;

import ganisrum.backend.datasource.repository.ProductRepository;
import ganisrum.backend.domain.model.Image;
import ganisrum.backend.domain.model.Product;
import ganisrum.backend.domain.service.ImageService;
import ganisrum.backend.domain.service.ProductService;
import ganisrum.backend.domain.service.SupplierService;
import ganisrum.backend.exceptions.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ImageService imageService;
    private final SupplierService supplierService;

    @Override
    public Product addProduct(Product product) {
        UUID supplierId = product.getSupplier();
        if (!supplierService.isExistById(supplierId)) {
            throw new NoSuchElementException("Поставщик с id: " + supplierId + " не существует");
        }
        return repository.save(product);
    }

    @Override
    @Transactional
    public void decreaseQuantity(UUID id, int quantity) {
        findProductById(id);
        int updatedRows = repository.decreaseQuantity(id, quantity);
        if (updatedRows == 0) {
            throw new BusinessLogicException("Недоcтаточно продуктов");
        }

    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Product findProductById(UUID id) {
        return repository.findProductById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return repository.getAllProducts();
    }

    @Override
    @Transactional
    public Image getProductImageById(UUID id) {
        Product product = findProductById(id);
        return imageService.getImage(product.getImage());
    }

    @Override
    @Transactional
    public Image addImageToProduct(UUID productId, Image image) {
        Product product = repository.findProductById(productId);

        if (product.getImage() != null) {
            throw new BusinessLogicException("Добавление невозможно, продукт уже содержит изображение");
        }

        Image savedImage = imageService.addImage(image);
        product.setImage(savedImage.getId());
        repository.save(product);

        return savedImage;
    }

}
