package exceptions;

public class InvalidEmailRegistrationException extends Exception {
	
	private static final long serialVersionUID = 3252603940669098821L;

	public InvalidEmailRegistrationException() {
		super();
	}
	
	public InvalidEmailRegistrationException(String message) {
		super(message);
	}
	
}
