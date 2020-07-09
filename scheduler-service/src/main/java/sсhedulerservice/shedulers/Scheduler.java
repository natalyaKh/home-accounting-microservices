package sсhedulerservice.shedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import sсhedulerservice.hystrix.CurrencyServiceClient;
import sсhedulerservice.hystrix.UsersServiceClient;
import sсhedulerservice.utils.Utils;

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
    CurrencyServiceClient currencyServiceClient;
    @Autowired
    Utils utils;

    /** checking in 00:00 on Sunday*/
//    @Scheduled(cron=@weekly)
    @Scheduled(cron="0 0 * * 7 * ")
    /** checking 10 sec */
//    @Scheduled(fixedDelay = 1000000000)
    public void scheduleCleanNotConfirmedEmail() {

        LOGGER.info("scheduler  clean not confirmed email started " + new Date());
        LOGGER.info("creating new admin");
        usersServiceClient.createAdmin();
        usersServiceClient.cleanNotConfirmedEmail(utils.generateSchedulerToken("admin_id"));
    }

    /** checking in 03:00 every day*/
    @Scheduled(cron="0 3 * * * * ")
    /** checking 10 sec */
//    @Scheduled(fixedDelay = 100)
    public void scheduleParseIsraBank() {

        LOGGER.info("scheduler parse isra bank started " + new Date());
        LOGGER.info("creating new admin");
        System.err.println("hi israel");
        usersServiceClient.createAdmin();
        currencyServiceClient.scheduleParseIsraBank(utils.generateSchedulerToken("admin_id"));
    }

    /** checking in 03:00 every day*/
    @Scheduled(cron="0 3 * * * * ")
    /** checking 10 sec */
//    @Scheduled(fixedDelay = 100)
    public void scheduleParseUkrBank() {

        LOGGER.info("scheduler parse ukr bank started " + new Date());
        System.err.println("hi ukraine");
        LOGGER.info("creating new admin");
        usersServiceClient.createAdmin();
        currencyServiceClient.scheduleParseUkrBank(utils.generateSchedulerToken("admin_id"));
    }

}
