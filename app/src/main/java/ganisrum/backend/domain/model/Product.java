package ganisrum.backend.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
public class Product {
    private UUID id;
    private String name;
    private String category;
    private BigDecimal price;
    private Integer availableStock;
    private Instant lastUpdateDate;
    private UUID supplier;
    private UUID image;
}
