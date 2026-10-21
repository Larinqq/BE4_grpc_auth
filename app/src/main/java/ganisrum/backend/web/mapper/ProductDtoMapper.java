package ganisrum.backend.web.mapper;

import ganisrum.backend.domain.model.Product;
import ganisrum.backend.web.dto.request.ProductCreateRequest;
import ganisrum.backend.web.dto.response.ProductResponse;

public interface ProductDtoMapper {

    Product toDomain(ProductCreateRequest product);

    ProductResponse toDto(Product productDto);
}
