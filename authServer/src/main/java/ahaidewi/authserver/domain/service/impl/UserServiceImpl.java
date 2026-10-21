package ahaidewi.authserver.domain.service.impl;

import ahaidewi.authserver.datasource.repository.UserRepository;
import ahaidewi.authserver.domain.model.User;
import ahaidewi.authserver.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public User saveNewUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public User findByEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new NoSuchElementException("Пользователь не найден"));
  }

  @Override
  public User findById(UUID id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Пользователь не найден"));
  }

  @Override
  public void updatePasswordHash(User user) {
    userRepository.updatePasswordHash(user);
  }

}
