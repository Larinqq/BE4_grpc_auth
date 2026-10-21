package ahaidewi.authserver.domain.security;

import ahaidewi.authserver.domain.model.TokenObject;
import ahaidewi.authserver.domain.model.User;
import io.jsonwebtoken.Claims;

public interface JwtProvider {

  String generateAccessToken(User user);

  TokenObject generateRefreshToken(User user);

  boolean validateAccessToken(String accessToken);

  boolean validateRefreshToken(String refreshToken);

  Claims getAccessClaims(String accessToken);

  Claims getRefreshClaims(String refreshToken);
}
