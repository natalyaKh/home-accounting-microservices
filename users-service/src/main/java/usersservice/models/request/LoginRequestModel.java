package usersservice.models.request;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequestModel {
	@NotNull(message="Email cannot be null")
	@Email(message = " you should put valid e-mail")
	private String email;
	@NotNull(message = "Password cannot be null")
	@Size(min=2, message= "password must not be less than two characters")
	private String password;

}
