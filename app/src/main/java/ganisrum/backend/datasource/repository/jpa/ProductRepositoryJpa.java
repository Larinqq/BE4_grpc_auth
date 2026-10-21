package ganisrum.backend.datasource.repository.jpa;

import ganisrum.backend.datasource.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepositoryJpa extends JpaRepository<ProductEntity, UUID> {
    @Modifying
    @Query("""
            UPDATE ProductEntity p
            SET p.availableStock = p.availableStock - :quantity,
                p.lastUpdateDate = CURRENT_TIMESTAMP
            WHERE p.id = :id AND p.availableStock >= :quantity
            """)
    int decreaseQuantity(@Param("id") UUID id, @Param("quantity") int quantity);

}


