package usersservice.hystrix;

import org.springframework.stereotype.Service;
import usersservice.dto.UserDto;


import javax.print.attribute.standard.Destination;
import java.util.HashMap;
import java.util.Map;

@Service
public class SendMail {
    public boolean verifyEmail(UserDto userDto) {
        final int MAP_SIZE = 4;
        boolean returnValue = false;
        //TODO impolementation of sending mail
        Map<String, String> sendBody = new HashMap<>(MAP_SIZE);
        sendBody.put("tokenValue", userDto.getEmailVerificationToken());
        sendBody.put("userName", userDto.getFirstName());
        sendBody.put("userLastName", userDto.getLastName());
        sendBody.put("email", userDto.getEmail());
        System.out.println("verification email sent!");
        return returnValue;
    }

    public boolean sendPasswordResetRequest(String firstName, String email, String token) {
        final int MAP_SIZE = 3;
        //TODO impolementation of sending mail
        Map<String, String> sendBody = new HashMap<>(MAP_SIZE);
        sendBody.put("tokenValue", token);
        sendBody.put("firstName", firstName);
        sendBody.put("email", email);
        System.out.println("Change password email sent!");
        boolean returnValue = true;
        return returnValue;
    }
}
