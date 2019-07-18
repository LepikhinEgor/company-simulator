package controller.messages;

public class RegistrationMessage {
	private String text;
	private int status;
	
	public final static int SUCCES_REGISTRATION = 1;
	public final static int INCORRECT_LOGIN = 2;
	public final static int INCORRECT_EMAIL = 3;
	public final static int INCORRECT_PASSWORD = 4;
	public final static int LOGIN_ALREADY_EXIST = 5;
	public final static int LOGIN_IS_FREE = 6;
	public final static int EMAIL_ALREADY_EXIST = 7;
	public final static int EMAIL_IS_FREE = 8;

	public RegistrationMessage(String message) {
		this.text = message;
	}
	
	public RegistrationMessage(int status) {
		this.status = status;
		switch(status) {
		case SUCCES_REGISTRATION: this.text = "Success registration";break;
		case INCORRECT_LOGIN: this.text = "Invalid login";break;
		case INCORRECT_EMAIL: this.text = "Invalid email";break;
		case INCORRECT_PASSWORD: this.text = "Invalid password";break;
		case LOGIN_ALREADY_EXIST: this.text = "Login already exist";break;
		case LOGIN_IS_FREE: this.text = "Login is free";break;
		case EMAIL_ALREADY_EXIST: this.text = "Email already exist";break;
		case EMAIL_IS_FREE: this.text = "Email is free";break;
		}
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
