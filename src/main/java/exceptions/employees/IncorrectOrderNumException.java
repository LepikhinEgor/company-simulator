package exceptions.employees;

public class IncorrectOrderNumException extends Exception{
	public IncorrectOrderNumException() {
		super();
	}
	
	public IncorrectOrderNumException(String message) {
		super(message);
	}

}
