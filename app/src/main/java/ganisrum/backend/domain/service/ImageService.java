package ganisrum.backend.domain.service;

import ganisrum.backend.domain.model.Image;

import java.util.UUID;

public interface ImageService {

    Image addImage(Image image);

    void updateImage(UUID id, byte[] image);

    void deleteImage(UUID id);

    Image getImage(UUID id);
}
