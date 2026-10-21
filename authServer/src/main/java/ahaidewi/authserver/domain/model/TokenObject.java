package ahaidewi.authserver.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class TokenObject {
  UUID id;
  UUID userId;
  Instant expiresAt;
  String tokenHash;
}
