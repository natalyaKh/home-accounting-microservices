package sсhedulerservice.hystrix;

import feign.FeignException;
import feign.Headers;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "users-service", fallbackFactory = UsersFallbackFactory.class)
public interface UsersServiceClient {
    @GetMapping("/clean-data/clean-not-conf")
    @Headers("Authorization: {token}")
    public void cleanNotConfirmedEmail( @RequestHeader("Authorization")
            String token);

    @GetMapping("users/create-admin")
    public void createAdmin();

    @Component
    class UsersFallbackFactory implements FallbackFactory<UsersServiceClient> {

        @Override
        public UsersServiceClient create(Throwable cause) {
            return new UsersServiceClientFallback(cause);
        }

    }

    class UserServiceClientFallback implements UsersServiceClient {

        Logger logger = LoggerFactory.getLogger(this.getClass());

        private final Throwable cause;

        public UserServiceClientFallback(Throwable cause) {
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

}
