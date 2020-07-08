package sendemail.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailDto {

    String tokenValue;
    String userName;
    String userLastName;
    String email;
}
