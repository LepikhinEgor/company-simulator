package controller.messages;

public class RegistrationMessage extends Message {
	
	public final static int INCORRECT_LOGIN = 2;
	public final static int INCORRECT_EMAIL = 3;
	public final static int INCORRECT_PASSWORD = 4;
	public final static int LOGIN_ALREADY_EXIST = 5;
	public final static int LOGIN_IS_FREE = 6;
	public final static int EMAIL_ALREADY_EXIST = 7;
	public final static int EMAIL_IS_FREE = 8;

	public RegistrationMessage(String message) {
		this.message = message;
	}
	
	public RegistrationMessage(int status) {
		this.status = status;
		
		switch(status) {
		case SUCCESS: this.message = "Success registration";break;
		case FAIL: this.message = "Registration failed";break;
		case INCORRECT_LOGIN: this.message = "Invalid login";break;
		case INCORRECT_EMAIL: this.message = "Invalid email";break;
		case INCORRECT_PASSWORD: this.message = "Invalid password";break;
		case LOGIN_ALREADY_EXIST: this.message = "Login already exist";break;
		case LOGIN_IS_FREE: this.message = "Login is free";break;
		case EMAIL_ALREADY_EXIST: this.message = "Email already exist";break;
		case EMAIL_IS_FREE: this.message = "Email is free";break;
		}
	}
	
}
