package exceptions;

public class NotRecordToDBException extends Exception {

	public NotRecordToDBException() {
		super();
	}
	
	public NotRecordToDBException(String message) {
		super(message);
	}
	
	public NotRecordToDBException(String message, Throwable cause) {
		super();
	}
	
}
