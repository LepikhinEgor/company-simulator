package controller.messages;

public class CreateContractMessage {
	
	public static final int SUCCESS = 0;
	public static final int FAIL = 1;
	
	private int status;
	private String message;
	
	public CreateContractMessage(int status) {
		this.status = status;
		
		switch(status) {
		case SUCCESS: this.message = "Success record new contract";break;
		case FAIL: this.message = "Fail recording new contract";break;
		}
	}
	
	public CreateContractMessage(int status, String message) {
		this.message = message;
		this.status = status;
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
