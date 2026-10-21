package ganisrum.backend.datasource.repository.jpa;

import ganisrum.backend.datasource.model.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepositoryJpa extends JpaRepository<ClientEntity, UUID>, JpaSpecificationExecutor<ClientEntity> {
    @EntityGraph(attributePaths = {"address"})
    Page<ClientEntity> findAllBy(PageRequest pageRequest);
}
