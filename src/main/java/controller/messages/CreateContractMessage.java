package controller.messages;

public class CreateContractMessage extends Message {

	public CreateContractMessage(int status) {
		this.status = status;
		
		switch(status) {
		case SUCCESS: this.message = "Success record new contract";break;
		case FAIL: this.message = "Fail recording new contract";break;
		}
	}
	
	public CreateContractMessage(int status, String message) {
		super(status, message);
	}
}
