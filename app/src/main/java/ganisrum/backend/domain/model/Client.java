package ganisrum.backend.domain.model;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class Client {
    private UUID id;

    private String clientName;

    private String clientSurname;

    private LocalDate birthday;

    private String gender;

    private Instant registrationDate;

    private Address address;
}
