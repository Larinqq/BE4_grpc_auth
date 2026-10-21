package ganisrum.backend.datasource.repository.Impl;


import ganisrum.backend.datasource.mapper.ImageEntityMapper;
import ganisrum.backend.datasource.model.ImageEntity;
import ganisrum.backend.datasource.repository.ImageRepository;
import ganisrum.backend.datasource.repository.jpa.ImageRepositoryJpa;
import ganisrum.backend.domain.model.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepository {
    private final ImageRepositoryJpa imageRepository;
    private final ImageEntityMapper imageEntityMapper;

    @Override
    public void updateImage(UUID id, byte[] image) {
        ImageEntity imageEntity = findIfExistById(id);
        imageEntity.setImage(image);
        imageRepository.save(imageEntity);
    }

    @Override
    public void deleteImage(UUID id) {
        ImageEntity imageEntity = findIfExistById(id);
        imageRepository.delete(imageEntity);
    }

    @Override
    public Image getImage(UUID id) {
        ImageEntity entity = findIfExistById(id);
        return imageEntityMapper.toDomain(entity);
    }

    @Override
    public Image addImage(Image image) {
        ImageEntity entity = imageRepository.save(imageEntityMapper.toEntity(image));
        return imageEntityMapper.toDomain(entity);
    }

    private ImageEntity findIfExistById(UUID id) {
        return imageRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Изображение с id: " + id + " не существует"));
    }
}
