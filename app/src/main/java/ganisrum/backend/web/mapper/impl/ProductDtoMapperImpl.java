package ganisrum.backend.web.mapper.impl;


import ganisrum.backend.domain.model.Product;
import ganisrum.backend.web.dto.request.ProductCreateRequest;
import ganisrum.backend.web.dto.response.ProductResponse;
import ganisrum.backend.web.mapper.ProductDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductDtoMapperImpl implements ProductDtoMapper {

    @Override
    public Product toDomain(ProductCreateRequest product) {
        if (product == null) {
            return null;
        }
        Product domainProduct = new Product();
        domainProduct.setName(product.name());
        domainProduct.setCategory(product.category());
        domainProduct.setPrice(product.price());
        domainProduct.setAvailableStock(product.availableStock());
        if (product.supplier() != null) {
            domainProduct.setSupplier(product.supplier());
        }
        domainProduct.setLastUpdateDate(null);
        return domainProduct;
    }

    @Override
    public ProductResponse toDto(Product product) {
        if (product == null) {
            return null;
        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setCategory(product.getCategory());
        productResponse.setPrice(product.getPrice());
        productResponse.setAvailableStock(product.getAvailableStock());
        productResponse.setLastUpdateDate(product.getLastUpdateDate());
        if (product.getSupplier() != null) {
            productResponse.setSupplier(product.getSupplier());
        }
        if (product.getImage() != null) {
            productResponse.setImage(product.getImage());
        }
        return productResponse;
    }


}
