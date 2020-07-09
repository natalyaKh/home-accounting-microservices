package currencyservice.service.hystrix;


import feign.FeignException;
import feign.Headers;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "send-email-service", fallbackFactory = EmailFallbackFactory.class)

public interface EmailServiceClient {

    @PostMapping("/parse/israel")
    @Headers("Authorization: {token}")
    String parseIsraBank( @RequestHeader("Authorization")
                                  String token);

    @PostMapping("/parse/ukraine")
    @Headers("Authorization: {token}")
    String parseUkrBank( @RequestHeader("Authorization")
                                 String token);
}

    @Component
    class CurrencyFallbackFactory implements FallbackFactory<EmailServiceClient> {
        @Override
        public EmailServiceClient create(Throwable cause) {
            return new EmailServiceClientFallback(cause);
        }
    }

    class EmailServiceClientFallback implements EmailServiceClient {

        Logger logger = LoggerFactory.getLogger(this.getClass());

        private final Throwable cause;

        public EmailServiceClientFallback(Throwable cause) {
            this.cause = cause;
        }

        @Override
        public String parseIsraBank(String token) {
            if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
                logger.error("404 error took place when parseIsraBank was called. Error message: "
                        + cause.getLocalizedMessage());
            } else {
                logger.error("Other error took place: " + cause.getLocalizedMessage());
            }
            return "send";
        }

        @Override
        public String parseUkrBank(String token) {
            if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
                logger.error("404 error took place when parseIsraBank was called. Error message: "
                        + cause.getLocalizedMessage());
            } else {
                logger.error("Other error took place: " + cause.getLocalizedMessage());
            }
            return "send";
        }
    }


