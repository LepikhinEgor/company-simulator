package exceptions;

public class LoginAlreadyExistException extends Exception{
	public LoginAlreadyExistException() {
		super();
	}
	
	public LoginAlreadyExistException(String message) {
		super(message);
	}
}
