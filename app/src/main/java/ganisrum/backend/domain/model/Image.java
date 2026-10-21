package ganisrum.backend.domain.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Image {
    private UUID id;
    private byte[] image;

}
