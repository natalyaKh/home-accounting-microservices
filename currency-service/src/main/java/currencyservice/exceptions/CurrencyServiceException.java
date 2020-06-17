package currencyservice.exceptions;

public class CurrencyServiceException extends RuntimeException{
 
	private static final long serialVersionUID = 1348771109171435607L;

	public CurrencyServiceException(String message)
	{
		super(message);
	}
}
