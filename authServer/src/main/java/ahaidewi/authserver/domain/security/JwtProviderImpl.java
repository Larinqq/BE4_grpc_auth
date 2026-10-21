package ahaidewi.authserver.domain.security;

import ahaidewi.authserver.domain.model.TokenObject;
import ahaidewi.authserver.domain.model.User;
import com.github.f4b6a3.uuid.UuidCreator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtProviderImpl implements JwtProvider {

  private final SecretKey jwtAccessSecret;
  private final SecretKey jwtRefreshSecret;
  private final Duration accessTokenDuration;
  private final Duration refreshTokenDuration;

  public JwtProviderImpl(@Value("${jwt.access.secret}") String jwtAccessSecret,
                         @Value("${jwt.refresh.secret}") String jwtRefreshSecret,
                         @Value("${jwt.access.lifetime}") Duration accessTokenDuration,
                         @Value("${jwt.refresh.lifetime}") Duration refreshTokenDuration) {
    this.jwtAccessSecret = Keys.hmacShaKeyFor(jwtAccessSecret.getBytes(StandardCharsets.UTF_8));
    this.jwtRefreshSecret = Keys.hmacShaKeyFor(jwtRefreshSecret.getBytes(StandardCharsets.UTF_8));
    this.accessTokenDuration = accessTokenDuration;
    this.refreshTokenDuration = refreshTokenDuration;
  }

  @Override
  public String generateAccessToken(User user) {
    String email = user.getEmail();
    UUID userId = user.getId();
    Map<String, Object> claims = Map.of(
        "email", email, "userId", userId.toString());

    Instant issuedDate = Instant.now();
    Instant expiresDate = issuedDate.plus(this.accessTokenDuration);

    return Jwts.builder()
        .claims(claims)
        .issuedAt(Date.from(issuedDate))
        .expiration(Date.from(expiresDate))
        .signWith(this.jwtAccessSecret)
        .compact();
  }

  @Override
  public TokenObject generateRefreshToken(@NonNull User user) {
    UUID userId = user.getId();
    UUID tokenId = UuidCreator.getTimeOrderedEpoch();
    Instant issuedDate = Instant.now();
    Instant expiresDate = issuedDate.plus(this.refreshTokenDuration);
    Map<String, Object> claims = Map.of(
        "userId", userId);

    String tokenHash = Jwts.builder()
        .claims(claims)
        .issuedAt(Date.from(issuedDate))
        .expiration(Date.from(expiresDate))
        .id(tokenId.toString())
        .signWith(this.jwtRefreshSecret)
        .compact();

    return TokenObject.builder()
        .id(tokenId)
        .userId(userId)
        .expiresAt(expiresDate)
        .tokenHash(tokenHash)
        .build();
  }

  @Override
  public boolean validateAccessToken(String accessToken) {
    return validateToken(accessToken, jwtAccessSecret);
  }

  @Override
  public boolean validateRefreshToken(String refreshToken) {
    return validateToken(refreshToken, jwtRefreshSecret);
  }

  private boolean validateToken(String token, SecretKey secret) {
    try {
      Jwts.parser().verifyWith(secret).build().parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  private Claims getClaims(String token, SecretKey secret) {
    return Jwts.parser()
        .verifyWith(secret)
        .build().parseSignedClaims(token)
        .getPayload();
  }

  @Override
  public Claims getAccessClaims(String accessToken) {
    return getClaims(accessToken, jwtAccessSecret);
  }

  @Override
  public Claims getRefreshClaims(String refreshToken) {
    return getClaims(refreshToken, jwtRefreshSecret);
  }

}
