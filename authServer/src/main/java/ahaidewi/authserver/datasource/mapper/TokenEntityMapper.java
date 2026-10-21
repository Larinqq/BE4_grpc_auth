package ahaidewi.authserver.datasource.mapper;

import ahaidewi.authserver.datasource.entity.TokenObjectEntity;
import ahaidewi.authserver.domain.model.TokenObject;

public interface TokenEntityMapper {
  TokenObjectEntity modelToEntity(TokenObject refreshToken);

  TokenObject entityToModel(TokenObjectEntity tokenEntity);
}
