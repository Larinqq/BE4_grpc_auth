package ahaidewi.authserver.domain.security.model;

import java.util.UUID;

public record JwtAuthAccessResult(UUID userId, String email, boolean isValid) {
}
