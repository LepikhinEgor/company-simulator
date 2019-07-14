package exceptions;

public class InvalidSignInLoginEmail extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5458203470950149068L;

	public InvalidSignInLoginEmail() {
		super();
	}
	
	public InvalidSignInLoginEmail(String message) {
		super(message);
	}
	
	public InvalidSignInLoginEmail(String message, Throwable e) {
		super(message,e);
	}

}