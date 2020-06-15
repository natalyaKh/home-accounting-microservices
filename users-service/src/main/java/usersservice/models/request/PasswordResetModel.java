package usersservice.models.request;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PasswordResetModel {
	private String token;
	private String password;
}
