package exceptions.employees;

public class DoubleEmployeeIdException extends Exception{
	
	public DoubleEmployeeIdException(String message) {
		super(message);
	}
	
	public DoubleEmployeeIdException(Throwable cause) {
		super(cause);
	}

}
