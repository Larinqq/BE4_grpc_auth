package ganisrum.backend.domain.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Supplier {

    private UUID id;
    private String name;
    private String phone;
    private Address address;
}
