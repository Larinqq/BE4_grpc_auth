package ahaidewi.authserver.datasource.repository;

import ahaidewi.authserver.datasource.entity.UserEntity;
import ahaidewi.authserver.datasource.mapper.UserEntityMapper;
import ahaidewi.authserver.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
  private final UserEntityMapper mapper;
  private final JdbcClient jdbcClient;

  @Override
  public boolean existsByEmail(String email) {
    return jdbcClient.sql("SELECT EXISTS(SELECT 1 FROM users s WHERE s.email = :email)")
        .param("email", email)
        .query(Boolean.class)
        .optional()
        .map(Boolean.TRUE::equals) // чтобы избежать теоретического NullPointerException
        .orElse(false);
  }

  @Override
  public User save(User user) {
    UserEntity userEntity = mapper.modelToEntity(user);
    int affectedRows = jdbcClient.sql(
            """
                INSERT INTO users (id, email, password_hash, first_name, last_name, phone_number)
                VALUES (:id, :email, :passwordHash, :firstName, :lastName, :phoneNumber)
                """)
        .paramSource(userEntity)
        .update();
    if (affectedRows == 0) {
      throw new IllegalStateException("Ошибка при записи в базу данных");
    }

    return mapper.entityToModel(userEntity);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    Optional<UserEntity> userEntity = jdbcClient.sql("SELECT * FROM users WHERE email = :email")
        .param("email", email)
        .query(UserEntity.class)
        .optional();
    return userEntity.map(mapper::entityToModel);
  }

  @Override
  public void updatePasswordHash(User user) {
    UserEntity userEntity = mapper.modelToEntity(user);
    jdbcClient.sql(
            """
                UPDATE users
                SET password_hash = :passwordHash
                WHERE id = :id
                """)
        .paramSource(userEntity)
        .update();
  }

  @Override
  public Optional<User> findById(UUID id) {
    Optional<UserEntity> userEntity = jdbcClient.sql("SELECT * FROM users WHERE id = :id")
        .param("id", id)
        .query(UserEntity.class)
        .optional();
    return userEntity.map(mapper::entityToModel);
  }
}
