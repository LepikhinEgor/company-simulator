package controller.messages;

public class EmployeeCreateMessage extends Message {
	
	public EmployeeCreateMessage(int status) {
		this.status = status;
		
		switch(status) {
		case SUCCESS: this.message = "Success record employee";break;
		case FAIL: this.message = "Fail record employee";break;
		}
	}
	
	public EmployeeCreateMessage(int status, String message) {
		super(status, message);
	}
}
