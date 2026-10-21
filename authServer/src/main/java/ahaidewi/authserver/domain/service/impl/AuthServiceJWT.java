package ahaidewi.authserver.domain.service.impl;

import ahaidewi.authserver.datasource.repository.TokenRepository;
import ahaidewi.authserver.domain.model.TokenObject;
import ahaidewi.authserver.domain.model.User;
import ahaidewi.authserver.domain.security.JwtProvider;
import ahaidewi.authserver.domain.security.JwtUtil;
import ahaidewi.authserver.domain.security.model.JwtAuthAccessResult;
import ahaidewi.authserver.domain.security.model.JwtAuthRefreshResult;
import ahaidewi.authserver.domain.service.AuthService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class AuthServiceJWT implements AuthService {

  private static final String TOKEN_PREFIX = "Bearer ";

  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private final JwtUtil jwtUtil;

  @Override
  public String getTokenPrefix() {
    return TOKEN_PREFIX;
  }

  @Override
  public String encodePassword(String rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }

  @Override
  public boolean checkPassword(String rawPassword, String encodedPassword) {
    return passwordEncoder.matches(rawPassword, encodedPassword);
  }

  @Override
  public String generateAccessToken(User user) {
    return jwtProvider.generateAccessToken(user);
  }

  @Override
  @Transactional
  public String generateRefreshToken(User user) {
    tokenRepository.deleteByUserId(user.getId());

    TokenObject refreshToken = jwtProvider.generateRefreshToken(user);

    tokenRepository.save(refreshToken);

    return refreshToken.getTokenHash();
  }

  @Override
  public JwtAuthAccessResult validateAccessToken(String accessToken) {
    boolean isValid = jwtProvider.validateAccessToken(accessToken);
    Claims claims = null;
    if (isValid) {
      claims = jwtProvider.getAccessClaims(accessToken);
    }
    return jwtUtil.resultAccessAuthentication(claims, isValid);
  }

  @Override
  public JwtAuthRefreshResult validateRefreshToken(String refreshToken) {
    boolean isValid = jwtProvider.validateRefreshToken(refreshToken);
    Claims claims = null;
    if (isValid) {
      claims = jwtProvider.getRefreshClaims(refreshToken);
      JwtAuthRefreshResult result = jwtUtil.resultRefreshAuthentication(claims, isValid);
      if (!tokenRepository.existsById(result.tokenId())) {
        if (tokenRepository.deleteByUserId(result.userId())) {
          throw new BadCredentialsException("Попытка входа с отозванным токеном. Все сессии завершены.");
          //В идеале нужно отправить на почту сообщение об этом и заблокировать аккаунт
        }
        throw new BadCredentialsException("Токен отозван");
      }
      return result;
    }
    return jwtUtil.resultRefreshAuthentication(claims, isValid);
  }
}
