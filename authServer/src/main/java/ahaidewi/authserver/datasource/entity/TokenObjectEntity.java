package ahaidewi.authserver.datasource.entity;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TokenObjectEntity {
  UUID id;
  UUID userId;
  Timestamp expiresAt;
}
