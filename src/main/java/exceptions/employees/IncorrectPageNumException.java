package exceptions.employees;

public class IncorrectPageNumException extends Exception {
	public IncorrectPageNumException() {
		super();
	}
	
	public IncorrectPageNumException(String message) {
		super(message);
	}
}
