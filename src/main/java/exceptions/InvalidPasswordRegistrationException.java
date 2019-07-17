package exceptions;

public class InvalidPasswordRegistrationException extends Exception{
	private static final long serialVersionUID = -6890911862513342396L;
	
	public InvalidPasswordRegistrationException() {
		super();
	}
	
	public InvalidPasswordRegistrationException(String message) {
		super(message);
	}
	
}
