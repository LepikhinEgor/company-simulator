package exceptions.employees;

public class DoubleIdException extends Exception{
	
	public DoubleIdException(String message) {
		super(message);
	}
	
	public DoubleIdException(Throwable cause) {
		super(cause);
	}

}
