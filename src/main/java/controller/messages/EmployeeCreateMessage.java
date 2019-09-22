package controller.messages;

public class EmployeeCreateMessage {
	private int status;
	private String message;
	
	public static final int SUCCESS = 0;
	public static final int FAIL = 1;
	
	public EmployeeCreateMessage(int status) {
		this.status = status;
		switch(status) {
		case SUCCESS: this.message = "Success record employee";break;
		case FAIL: this.message = "Fail record employee";break;
		}
	}
	
	public EmployeeCreateMessage(int status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
