package ganisrum.backend.datasource.repository.jpa;

import ganisrum.backend.datasource.model.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageRepositoryJpa extends JpaRepository<ImageEntity, UUID> {
}
