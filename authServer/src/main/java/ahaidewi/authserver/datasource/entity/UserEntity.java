package ahaidewi.authserver.datasource.entity;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {

  private UUID id;
  private String email;
  private String passwordHash;
  private String firstName;
  private String lastName;
  private String phoneNumber;
}
