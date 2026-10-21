package ahaidewi.authserver.domain.security.model;

import java.util.UUID;

public record JwtAuthRefreshResult(UUID userId, UUID tokenId, boolean isValid) {
}
