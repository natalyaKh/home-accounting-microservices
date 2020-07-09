package sсhedulerservice.hystrix;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UsersServiceClientFallback implements UsersServiceClient {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Throwable cause;

    public UsersServiceClientFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public void cleanNotConfirmedEmail(String token) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            logger.error("404 error took place when cleanNotConfirmedEmail was called. Error message: "
                    + cause.getLocalizedMessage());
        } else {
            logger.error("Other error took place: " + cause.getLocalizedMessage());
        }
    }

    @Override
    public void createAdmin() {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            logger.error("404 error took place when createAdmin was called. Error message: "
                    + cause.getLocalizedMessage());
        } else {
            logger.error("Other error took place: " + cause.getLocalizedMessage());
        }

    }

}
