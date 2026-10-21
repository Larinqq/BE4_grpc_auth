package ahaidewi.authserver.datasource.repository;

import ahaidewi.authserver.datasource.entity.TokenObjectEntity;
import ahaidewi.authserver.datasource.mapper.TokenEntityMapper;
import ahaidewi.authserver.domain.model.TokenObject;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {
  private final TokenEntityMapper mapper;
  private final JdbcClient jdbcClient;

  @Override
  public TokenObject save(TokenObject token) {
    TokenObjectEntity tokenEntity = mapper.modelToEntity(token);
    int affectedRows = jdbcClient.sql(
            """
                INSERT INTO refresh_tokens (id, user_id, expires_at)
                VALUES (:id, :userId, :expiresAt)
                """)

        .paramSource(tokenEntity)
        .update();

    if (affectedRows == 0) {
      throw new IllegalStateException("Ошибка при записи в базу данных");
    }
    return mapper.entityToModel(tokenEntity);
  }

  @Override
  public boolean existsById(UUID id) {
    return jdbcClient.sql("SELECT EXISTS(SELECT 1 FROM refresh_tokens t WHERE t.id = :id)")
        .param("id", id)
        .query(Boolean.class)
        .optional()
        .map(Boolean.TRUE::equals) // чтобы избежать теоретического NullPointerException
        .orElse(false);
  }

  @Override
  public Optional<TokenObject> findById(UUID id) {
    Optional<TokenObjectEntity> tokenEntity = jdbcClient.sql("SELECT * FROM refresh_tokens WHERE id = :id")
        .param("id", id)
        .query(new DataClassRowMapper<>(TokenObjectEntity.class))
        .optional();
    return tokenEntity.map(mapper::entityToModel);
  }

  @Override
  public boolean deleteByUserId(UUID userId) {
    int deletedRows = jdbcClient.sql("DELETE FROM refresh_tokens WHERE user_id = :userId")
        .param("userId", userId)
        .update();
    return deletedRows > 0;
  }

  @Override
  public void deleteExpiredTokens() {
    Timestamp nowTimestamp = Timestamp.from(Instant.now());
    jdbcClient.sql("DELETE FROM refresh_tokens WHERE expires_at < :now")
        .param("now", nowTimestamp)
        .update();
  }
}
