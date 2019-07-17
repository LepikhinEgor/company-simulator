package exceptions;

public class EmailAlreadyExistException extends Exception{
	
	private static final long serialVersionUID = -5691606766063588267L;

	public EmailAlreadyExistException() {
		super();
	}
	
	public EmailAlreadyExistException(String message) {
		super(message);
	}
}

