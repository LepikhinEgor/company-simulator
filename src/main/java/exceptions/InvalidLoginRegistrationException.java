package exceptions;

public class InvalidLoginRegistrationException extends Exception{
	private static final long serialVersionUID = -6890911862513342396L;
	
	public InvalidLoginRegistrationException() {
		super();
	}
	
	public InvalidLoginRegistrationException(String message) {
		super(message);
	}
	
}
