package controller.messages;

public class SignInMessage extends Message{
	
	public static final int INVALID_LOGIN = 2;
	public static final int INVALID_PASSWORD = 3;
	public static final int INCORRECT_LOGIN_OR_PASSWORD = 4;
	
	private String login;
	
	public SignInMessage(int status) {
		this.status = status;
		
		switch(status) {
		case SUCCESS: message = "Success sign up";break;
		case FAIL: message = "Sign In error"; break;
		case INVALID_LOGIN: message = "Unacceptable symbols in field login";break;
		case INVALID_PASSWORD: message = "Unacceptable symbols in field password";break;
		case INCORRECT_LOGIN_OR_PASSWORD: message = "Incorrect login or password";break;
		}
		
		this.login = "";
	}
	
	public SignInMessage(int status, String login) {
		this(status);
		
		this.login = login;
	}
	
	public SignInMessage(int status,String message, String login) {
		this.status = status;
		this.message = message;
		this.login = login;
	}
	

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	
}
