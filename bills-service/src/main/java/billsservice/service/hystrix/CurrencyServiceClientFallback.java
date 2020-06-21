package billsservice.service.hystrix;


import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

class CurrencyServiceClientFallback implements CurrencyServiceClient {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Throwable cause;

	public CurrencyServiceClientFallback(Throwable cause) {
		this.cause = cause;
	}



	@Override
	public List<CurrencyDTO> getAllCurrencyByUser(String userId, String req) {
		if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
			logger.error("404 error took place when getAllCurrencyByUser was called with userId: " + userId + ". Error message: "
					+ cause.getLocalizedMessage());
		} else {
			logger.error("Other error took place: " + cause.getLocalizedMessage());
		}
		return new ArrayList<>();
	}

}
