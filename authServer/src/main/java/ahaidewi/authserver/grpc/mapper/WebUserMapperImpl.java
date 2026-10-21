package ahaidewi.authserver.grpc.mapper;

import ahaidewi.authserver.domain.model.User;
import ahaidewi.authserver.grpc.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class WebUserMapperImpl implements WebUserMapper {
  @Override
  public User webToDomain(RegisterRequest request) {
    return User.builder()
        .email(request.getEmail())
        .password(null)
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .phoneNumber(request.getPhoneNumber())
        .build();
  }
}
