package ahaidewi.authserver.datasource.mapper;

import ahaidewi.authserver.datasource.entity.UserEntity;
import ahaidewi.authserver.domain.model.User;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapperImpl implements UserEntityMapper {
  @Override
  public UserEntity modelToEntity(@NonNull User user) {
    return UserEntity.builder()
        .id(user.getId())
        .email(user.getEmail())
        .passwordHash(user.getPassword())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .phoneNumber(user.getPhoneNumber())
        .build();
  }

  @Override
  public User entityToModel(@NonNull UserEntity userEntity) {
    return User.builder()
        .id(userEntity.getId())
        .email(userEntity.getEmail())
        .password(userEntity.getPasswordHash())
        .firstName(userEntity.getFirstName())
        .lastName(userEntity.getLastName())
        .phoneNumber(userEntity.getPhoneNumber())
        .build();
  }
}
