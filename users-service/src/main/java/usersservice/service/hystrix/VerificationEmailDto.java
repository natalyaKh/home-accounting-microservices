package usersservice.service.hystrix;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VerificationEmailDto {
    String tokenValue;
    String userName;
    String userLastName;
    String email;
}
