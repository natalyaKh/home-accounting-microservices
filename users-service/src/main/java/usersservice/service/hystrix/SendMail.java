package usersservice.service.hystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import usersservice.dto.UserDto;
import usersservice.enums.ErrorMessages;
import usersservice.service.hystrix.EmailServiceClient;


import javax.print.attribute.standard.Destination;
import java.util.HashMap;
import java.util.Map;

@Service
public class SendMail {

    @Autowired
    EmailServiceClient emailServiceClient;
    @Autowired
    Environment environment;

    public Boolean verifyEmail(UserDto userDto) {
        final int MAP_SIZE = 4;
        Map<String, String> sendBody = new HashMap<>(MAP_SIZE);
        sendBody.put("tokenValue", userDto.getEmailVerificationToken());
        sendBody.put("userName", userDto.getFirstName());
        sendBody.put("userLastName", userDto.getLastName());
        sendBody.put("email", userDto.getEmail());
        String token = environment.getProperty("admin.token");
        emailServiceClient.sendVerificationEmail(sendBody, token);
        return true;
    }

    public Boolean sendPasswordResetRequest(String firstName, String email, String token) {
        final int MAP_SIZE = 3;
        Map<String, String> sendBody = new HashMap<>(MAP_SIZE);
        sendBody.put("tokenValue", token);
        sendBody.put("firstName", firstName);
        sendBody.put("email", email);
        System.out.println("Change password email sent!");
        return emailServiceClient.sendChangePasswordEmail(sendBody, token);
    }

    public String cleanNotConfirmEmails(){
        String token = environment.getProperty("admin.token");
        return emailServiceClient.cleanNotConfirmEmails(token);
    }

    public String errorMessage(ErrorMessages cleanDatabaseError) {
        String token = environment.getProperty("admin.token");
        return emailServiceClient.errorMessage(cleanDatabaseError, token);
    }
}
