package usersservice.service.hystrix;


import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usersservice.enums.ErrorMessages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class EmailServiceClientFallback implements EmailServiceClient {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Throwable cause;

	public EmailServiceClientFallback(Throwable cause) {
		this.cause = cause;
	}



	@Override
	public String sendVerificationEmail(Map<String, String> sendBody, String token) {
		if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
			logger.error("404 error took place when sendVerificationEmail was called with email: "
					+ sendBody.get("email") + ". Error message: "
					+ cause.getLocalizedMessage());
		} else {
			logger.error("Other error took place: " + cause.getLocalizedMessage());
		}
		return "verification email has been send";
	}

	@Override
	public Boolean sendChangePasswordEmail(Map<String, String> sendBody, String token) {
		if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
			logger.error("404 error took place when sendVerificationEmail was called with email: "
					+ sendBody.get("email") + ". Error message: "
					+ cause.getLocalizedMessage());
		} else {
			logger.error("Other error took place: " + cause.getLocalizedMessage());
		}
		return true;
	}

	@Override
	public String cleanNotConfirmEmails(String token) {
		if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
			logger.error("404 error took place when cleanNotConfirmEmails was called . Error message: "
					+ cause.getLocalizedMessage());
		} else {
			logger.error("Other error took place: " + cause.getLocalizedMessage());
		}
		return "send";
	}

	@Override
	public String errorMessage(ErrorMessages cleanDatabaseError, String token) {
		if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
			logger.error("404 error took place when errorMessage was called . Error message: "
					+ cause.getLocalizedMessage());
		} else {
			logger.error("Other error took place: " + cause.getLocalizedMessage());
		}
		return "send";
	}

}
