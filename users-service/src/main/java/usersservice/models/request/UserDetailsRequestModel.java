package usersservice.models.request;

import lombok.*;
import usersservice.enums.InterfaceLang;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDetailsRequestModel {
    @NotNull(message="User name cannot be null")
    @Size(min=2, message= "User name must not be less than two characters")
    private String firstName;

    @NotNull(message="Last name cannot be null"                                                                                                                                                                                                                                                                                                                                                                                                                                                             )
    @Size(min=2, message= "Last name must not be less than two characters")
    private String lastName;

    @NotNull(message="Email cannot be null")
    @Email(message = " you should put valid e-mail")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min=2, message= "password must not be less than two characters")
    private String password;

    @NotNull(message = "Language cannot be null")
    private InterfaceLang lang;
}
