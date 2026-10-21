package ahaidewi.authserver.domain.service;

import ahaidewi.authserver.domain.model.User;
import ahaidewi.authserver.domain.security.model.JwtAuthAccessResult;
import ahaidewi.authserver.domain.security.model.JwtAuthRefreshResult;

public interface AuthService {

  String getTokenPrefix();

  String encodePassword(String rawPassword);

  boolean checkPassword(String rawPassword, String encodedPassword);

  String generateAccessToken(User user);

  String generateRefreshToken(User user);

  JwtAuthAccessResult validateAccessToken(String accessToken);

  JwtAuthRefreshResult validateRefreshToken(String refreshToken);
}
