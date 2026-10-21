package ahaidewi.authserver.domain.security;


import ahaidewi.authserver.domain.security.model.JwtAuthAccessResult;
import ahaidewi.authserver.domain.security.model.JwtAuthRefreshResult;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtUtil {

  public JwtAuthAccessResult resultAccessAuthentication(Claims claims, boolean isValid) {
    UUID userId = null;
    String email = null;
    if (claims != null) {
      userId = UUID.fromString(claims.get("userId", String.class));
      email = claims.get("email", String.class);
    }
    return new JwtAuthAccessResult(userId, email, isValid);
  }

  public JwtAuthRefreshResult resultRefreshAuthentication(Claims claims, boolean isValid) {
    UUID userId = null;
    UUID tokenId = null;
    if (claims != null) {
      userId = UUID.fromString(claims.get("userId", String.class));
      tokenId = UUID.fromString(claims.getId());
    }
    return new JwtAuthRefreshResult(userId, tokenId, isValid);
  }
}
