package ganisrum.backend.datasource.mapper;

import ganisrum.backend.datasource.model.ImageEntity;
import ganisrum.backend.domain.model.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageEntityMapper {

    ImageEntity toEntity(Image image);

    Image toDomain(ImageEntity image);
}
