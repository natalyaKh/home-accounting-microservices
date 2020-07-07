package shedulerservice.shedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import shedulerservice.hystrix.UsersServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import shedulerservice.utils.Utils;


import java.util.Date;

@Component
@EnableScheduling
public class Scheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);

    @Value("${super.admin.id}")
    private String admin_id;

    @Autowired
    UsersServiceClient usersServiceClient;
    @Autowired
    Utils utils;

    /** checking in 00:00 on Sunday*/
    @Scheduled(cron="0 0 * * 7")
    /** checking 10 sec */
//    @Scheduled(fixedDelay = 1000)
    public void scheduleCleanNotConfirmedEmail() {

        LOGGER.info("scheduler started " + new Date());

        LOGGER.info("creating new admin");
        usersServiceClient.createAdmin();
        usersServiceClient.cleanNotConfirmedEmail(utils.generateSchedulerToken("admin_id"));
    }


}
