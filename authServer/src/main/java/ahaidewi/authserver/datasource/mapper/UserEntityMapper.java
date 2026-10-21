package ahaidewi.authserver.datasource.mapper;

import ahaidewi.authserver.datasource.entity.UserEntity;
import ahaidewi.authserver.domain.model.User;

public interface UserEntityMapper {

  UserEntity modelToEntity(User user);

  User entityToModel(UserEntity userEntity);
}
