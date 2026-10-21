package ganisrum.backend.datasource.repository;

import ganisrum.backend.domain.model.Image;

import java.util.UUID;

public interface ImageRepository {
    void updateImage(UUID id, byte[] image);

    void deleteImage(UUID id);

    Image getImage(UUID id);

    Image addImage(Image image);
}
