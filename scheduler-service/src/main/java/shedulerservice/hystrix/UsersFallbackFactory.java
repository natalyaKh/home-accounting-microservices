package shedulerservice.hystrix;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UsersFallbackFactory implements FallbackFactory<UsersServiceClient> {

    @Override
    public UsersServiceClient create(Throwable cause) {
        return new UsersServiceClientFallback(cause);
    }
}
