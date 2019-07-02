package exceptions;

public class IncorrectRegistrationDataException extends Exception{
	private static final long serialVersionUID = -6890911862513342396L;
	
	private String[] mistakesDescription;

	public IncorrectRegistrationDataException() {
		super();
	}
	
	public IncorrectRegistrationDataException(String message) {
		super(message);
	}
	
	public IncorrectRegistrationDataException(String[] mistakes) {
		super();
		mistakesDescription = mistakes;
	}
	
	public String[] getMistakesDescription() {
		return mistakesDescription;
	}

	public void setMistakesDescription(String[] mistakesDescription) {
		this.mistakesDescription = mistakesDescription;
	}
	
}
