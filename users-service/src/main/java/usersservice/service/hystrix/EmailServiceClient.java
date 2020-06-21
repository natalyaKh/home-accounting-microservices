package usersservice.service.hystrix;


import feign.FeignException;
import feign.Headers;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "send-email-service", fallbackFactory = EmailFallbackFactory.class)

public interface EmailServiceClient {
    @PostMapping("/verification-email")
    @Headers("Authorization: {token}")
    public String sendVerificationEmail(@RequestBody Map<String, String> sendBody,
                                        @RequestHeader("Authorization")
                                                String token);

    @PostMapping(path = "/password-reset-request")
    @Headers("Authorization: {token}")
    Boolean sendChangePasswordEmail(@RequestBody Map<String, String> sendBody,
                                    @RequestHeader("Authorization")
                                            String token);

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
        public String sendVerificationEmail(Map<String, String>  emailDto, String token) {

            if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
                logger.error("404 error took place when sendVerificationEmail was called with email: " + emailDto.get("email") + ". Error message: "
                        + cause.getLocalizedMessage());
            } else {
                logger.error("Other error took place: " + cause.getLocalizedMessage());
            }

            return "Verification email has been send";
        }

        @Override
        public Boolean sendChangePasswordEmail(Map<String, String> sendBody, String token) {
            if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
                logger.error("404 error took place when sendChangePasswordEmail was called with email: " + sendBody.get("email") + ". Error message: "
                        + cause.getLocalizedMessage());
            } else {
                logger.error("Other error took place: " + cause.getLocalizedMessage());
            }
            return true;
        }

    }

}
