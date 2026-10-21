package ganisrum.backend.datasource.repository.jpa;

import ganisrum.backend.datasource.model.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SupplierRepositoryJpa extends JpaRepository<SupplierEntity, UUID> {

    @Query("""
            SELECT EXISTS
             (SELECT 1
             FROM ProductEntity p 
             WHERE p.supplier.id = :supplierId)
            """)
    boolean hasProducts(@Param("supplierId") UUID supplierId);
}
