package usersservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usersservice.enums.ErrorMessages;
import usersservice.enums.InterfaceLang;
import usersservice.models.entity.UserEntity;
import usersservice.repository.UserRepository;
import usersservice.service.hystrix.SendMail;

import java.util.Date;
import java.util.List;

@Service
public class UsersSchedulerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersSchedulerService.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    SendMail sendMail;


    public void cleanNotConfirmedEmail() {
        List<UserEntity> result = userRepository.findUserByEmailVerificationStatus( false);
        if (result != null) {
            userRepository.deleteAll(result);
            LOGGER.info("database cleaned: " + new Date());
            sendMail.cleanNotConfirmEmails();
            LOGGER.info("e-mail from cleaner send " + new Date());
        } else {
            sendMail.errorMessage(ErrorMessages.CLEAN_DATABASE_ERROR);
            LOGGER.error("error e-mail send " + new Date());
        }
    }

}
