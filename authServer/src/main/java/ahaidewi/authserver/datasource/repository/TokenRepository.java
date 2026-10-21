package ahaidewi.authserver.datasource.repository;


import ahaidewi.authserver.domain.model.TokenObject;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepository {
  TokenObject save(TokenObject token);

  boolean existsById(UUID id);

  Optional<TokenObject> findById(UUID id);

  boolean deleteByUserId(UUID userId);

  void deleteExpiredTokens();
}
