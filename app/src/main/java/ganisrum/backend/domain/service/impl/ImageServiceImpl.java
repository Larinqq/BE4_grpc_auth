package ganisrum.backend.domain.service.impl;

import ganisrum.backend.datasource.repository.ImageRepository;
import ganisrum.backend.domain.model.Image;
import ganisrum.backend.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository repository;

    @Override
    public Image addImage(Image image) {
        return repository.addImage(image);
    }

    @Override
    @Transactional
    public void updateImage(UUID id, byte[] image) {
        repository.updateImage(id, image);
    }

    @Override
    @Transactional
    public void deleteImage(UUID id) {
        repository.deleteImage(id);

    }

    @Override
    @Transactional(readOnly = true)
    public Image getImage(UUID id) {
        return repository.getImage(id);
    }
}
