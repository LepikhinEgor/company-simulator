package exceptions;

public class DatabaseAccessException extends Exception{

	private static final long serialVersionUID = 3274516715868284257L;
	
	public DatabaseAccessException() {
		super("No message");
	}
	
	public DatabaseAccessException(String message) {
		super(message);
	}
	
	public DatabaseAccessException(Throwable e) {
		super(e);
	}
}
