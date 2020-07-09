package sсhedulerservice.hystrix;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class CurrencyFallbackFactory implements FallbackFactory<CurrencyServiceClient> {

    @Override
    public CurrencyServiceClient create(Throwable cause) {
        return new CurrencyServiceClientFallback(cause);
    }
}
