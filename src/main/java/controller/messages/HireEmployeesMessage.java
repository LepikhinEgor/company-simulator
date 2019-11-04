package controller.messages;

public class HireEmployeesMessage extends Message {
	public HireEmployeesMessage() {
		super();
	}
	
	public HireEmployeesMessage(int status) {
		super(status);
	}
	
	public HireEmployeesMessage(int status, String message) {
		super(status, message);
	}
}
