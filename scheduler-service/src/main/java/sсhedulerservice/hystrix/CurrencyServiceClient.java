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


@FeignClient(name = "currency-service", fallbackFactory = CurrencyFallbackFactory.class)
public interface CurrencyServiceClient {
    @GetMapping("/parse/israel")
    @Headers("Authorization: {token}")
    public void scheduleParseIsraBank( @RequestHeader("Authorization")
            String token);

    @GetMapping("users/create-admin")
    public void createAdmin();

    @GetMapping("/parse/ukraine")
    @Headers("Authorization: {token}")
    public void scheduleParseUkrBank( @RequestHeader("Authorization")
                                               String token);

    @Component
    class CurrencyFallbackFactory implements FallbackFactory<CurrencyServiceClient> {

        @Override
        public CurrencyServiceClient create(Throwable cause) {
            return new CurrencyServiceClientFallback(cause);
        }

    }

    class CurrencyServiceClientFallback implements CurrencyServiceClient {

        Logger logger = LoggerFactory.getLogger(this.getClass());

        private final Throwable cause;

        public CurrencyServiceClientFallback(Throwable cause) {
            this.cause = cause;
        }

        @Override
        public void scheduleParseIsraBank(String token) {
            if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
                logger.error("404 error took place when scheduleParseIsraBank was called. Error message: "
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

        @Override
        public void scheduleParseUkrBank(String token) {
            if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
                logger.error("404 error took place when scheduleParseUkrBank was called. Error message: "
                        + cause.getLocalizedMessage());
            } else {
                logger.error("Other error took place: " + cause.getLocalizedMessage());
            }
        }
    }

}
