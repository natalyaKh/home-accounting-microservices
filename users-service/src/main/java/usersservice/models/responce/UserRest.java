package usersservice.models.responce;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRest {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
}
