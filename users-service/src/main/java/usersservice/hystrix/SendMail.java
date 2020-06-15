package usersservice.hystrix;

import org.springframework.stereotype.Service;
import usersservice.dto.UserDto;


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
        System.out.println("Email sent!");
        return returnValue;
    }
}
