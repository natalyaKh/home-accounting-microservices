package billsservice.service.hystrix;


import feign.FeignException;
import feign.Headers;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "currency-service", fallbackFactory = CurrencyFallbackFactory.class)
public interface CurrencyServiceClient {
    @GetMapping("/currency/{userId}")
    @Headers("Authorization: {token}")
    public List<CurrencyDTO> getAllCurrencyByUser(@PathVariable String userId, @RequestHeader("Authorization")
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
        public List<CurrencyDTO> getAllCurrencyByUser(String id, String req) {

            if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
                logger.error("404 error took place when getAllCurrencyByUser was called with userId: " + id + ". Error message: "
                        + cause.getLocalizedMessage());
            } else {
                logger.error("Other error took place: " + cause.getLocalizedMessage());
            }

            return new ArrayList<>();
        }

    }

}
