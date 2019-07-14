package controller.messages;

public class SignInMessage {
	
	public static final int SUCCESS_SIGN_IN = 0;
	public static final int INVALID_LOGIN = 1;
	public static final int INVALID_PASSWORD = 2;
	public static final int INCORRECT_LOGIN_OR_PASSWORD = 3;
	public static final int OTHER_MISTAKE = 4;
	
	private int status;
	private String description;
	
	public SignInMessage(int status) {
		this.status = status;
		switch(status) {
		case SUCCESS_SIGN_IN: description = "Success sign up";break;
		case INVALID_LOGIN: description = "Unacceptable symbols in field login";break;
		case INVALID_PASSWORD: description = "Unacceptable symbols in field password";break;
		case INCORRECT_LOGIN_OR_PASSWORD: description = "Incorrect login or password";break;
		case OTHER_MISTAKE: description = "Sign In error"; break;
		}
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
