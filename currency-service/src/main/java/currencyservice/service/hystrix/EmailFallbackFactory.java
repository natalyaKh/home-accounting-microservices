package currencyservice.service.hystrix;


import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
class EmailFallbackFactory implements FallbackFactory<EmailServiceClient> {

	@Override
	public EmailServiceClient create(Throwable cause) {
		return new EmailServiceClientFallback(cause);
	}

}

