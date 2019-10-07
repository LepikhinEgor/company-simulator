package controller.messages;

public class EmployeeUpdateMessage extends Message {
	
	public EmployeeUpdateMessage(int status) {
		this.status = status;
		
		switch(status) {
		case SUCCESS: this.message = "Success record employee";break;
		case FAIL: this.message = "Fail record employee";break;
		}
	}
	
	public EmployeeUpdateMessage(int status, String message) {
		super(status, message);
	}
}
