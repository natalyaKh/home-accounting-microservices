package currencyservice.service.hystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class SendMail {

    @Autowired
    EmailServiceClient emailServiceClient;
    @Autowired
    Environment environment;




    public String parseIsraBank(){
        String token = environment.getProperty("admin.token");
        return emailServiceClient.parseIsraBank(token);
    }

    public String parseUkrBank(){
        String token = environment.getProperty("admin.token");
        return emailServiceClient.parseUkrBank(token);
    }

//    public String errorMessage(ErrorMessages cleanDatabaseError) {
//        String token = environment.getProperty("admin.token");
//        return emailServiceClient.errorMessage(cleanDatabaseError, token);
//    }
}
