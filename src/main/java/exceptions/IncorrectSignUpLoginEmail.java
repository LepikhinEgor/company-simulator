package exceptions;

public class IncorrectSignUpLoginEmail extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5458203470950149068L;

	public IncorrectSignUpLoginEmail() {
		super();
	}
	
	public IncorrectSignUpLoginEmail(String message) {
		super(message);
	}
	
	public IncorrectSignUpLoginEmail(String message, Throwable e) {
		super(message,e);
	}

}