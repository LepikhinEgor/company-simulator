package controller.messages;

/**
 * Parent class for all rest response classes
 * @author egor
 *
 */
public class Message {
	
	public static final int SUCCESS = 0;
	public static final int FAIL = 1;
	
	protected int status;
	protected String message;
	
	public Message() {
		super();
		this.status = 1;
		this.message = "";
	}
	
	public Message(int status) {
		this.status = status;
		
		switch(status) {
		case SUCCESS: this.message = "Success";break;
		case FAIL: this.message = "Fail";break;
		default: this.message = "Status undefined";break;
		}
	}
	
	public Message(int status, String message) {
		if (message == null)
			throw new IllegalArgumentException("message must be not null");
		
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
		if (message == null)
			throw new IllegalArgumentException("message must be not null");
		
		this.message = message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + status;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Message [status=" + status + ", message=" + message + "]";
	}

}
