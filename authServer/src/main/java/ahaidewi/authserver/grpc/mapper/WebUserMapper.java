package ahaidewi.authserver.grpc.mapper;

import ahaidewi.authserver.domain.model.User;
import ahaidewi.authserver.grpc.RegisterRequest;

public interface WebUserMapper {
  User webToDomain(RegisterRequest request);
}
