package ganisrum.backend.web.mapper;

import ganisrum.backend.domain.model.Image;
import ganisrum.backend.web.dto.response.ImageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageDtoMapper {
    @Mapping(target = "imageData", source = "image")
    ImageResponse toDto(Image image);

    Image toDomain(byte[] image);
}
