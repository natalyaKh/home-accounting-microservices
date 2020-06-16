package billsservice.exceptions;

public class BillServiceException extends RuntimeException{
 
	private static final long serialVersionUID = 1348771109171435607L;

	public BillServiceException(String message)
	{
		super(message);
	}
}
