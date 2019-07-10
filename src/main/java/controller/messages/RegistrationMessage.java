package controller.messages;

public class RegistrationMessage {
	private String text;
	private int status;
	
	public final static int SUCCES_REGISTRATION = 1;
	public final static int INCORRECT_LOGIN = 2;
	public final static int INCORRECT_EMAIL = 3;
	public final static int INCORRECT_LOGIN_AND_EMAIL = 4;
	public final static int LOGIN_ALREADY_EXIST = 5;
	public final static int LOGIN_IS_FREE = 6;
	public final static int EMAIL_ALREADY_EXIST = 7;
	public final static int EMAIL_IS_FREE = 8;

	public RegistrationMessage(String message) {
		this.text = message;
	}
	
	public RegistrationMessage(String message, int status) {
		this.text = message;
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
