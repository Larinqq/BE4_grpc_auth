package ahaidewi.authserver.datasource.repository;

import ahaidewi.authserver.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

  boolean existsByEmail(String email);

  User save(User user);

  Optional<User> findByEmail(String email);

  void updatePasswordHash(User user);

  Optional<User> findById(UUID id);
}
