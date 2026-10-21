package ahaidewi.authserver.domain.model;

import com.github.f4b6a3.uuid.UuidCreator;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class User {
  @Builder.Default
  private UUID id = UuidCreator.getTimeOrderedEpoch(); // Генерация UUIDv7

  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private String phoneNumber;
}
