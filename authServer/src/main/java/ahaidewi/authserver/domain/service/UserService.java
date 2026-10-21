package ahaidewi.authserver.domain.service;

import ahaidewi.authserver.domain.model.User;

import java.util.UUID;

public interface UserService {
  boolean existsByEmail(String email);

  User saveNewUser(User user);

  User findByEmail(String email);

  User findById(UUID id);

  void updatePasswordHash(User user);
}
