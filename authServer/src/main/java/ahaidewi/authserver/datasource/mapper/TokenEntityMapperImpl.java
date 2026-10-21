package ahaidewi.authserver.datasource.mapper;

import ahaidewi.authserver.datasource.entity.TokenObjectEntity;
import ahaidewi.authserver.domain.model.TokenObject;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class TokenEntityMapperImpl implements TokenEntityMapper {
  @Override
  public TokenObjectEntity modelToEntity(@NonNull TokenObject refreshToken) {
    return TokenObjectEntity.builder()
        .id(refreshToken.getId())
        .userId(refreshToken.getUserId())
        .expiresAt(Timestamp.from(refreshToken.getExpiresAt()))
        .build();
  }

  @Override
  public TokenObject entityToModel(@NonNull TokenObjectEntity tokenEntity) {
    return TokenObject.builder()
        .id(tokenEntity.getId())
        .userId(tokenEntity.getUserId())
        .expiresAt(tokenEntity.getExpiresAt().toInstant())
        .build();
  }
}
