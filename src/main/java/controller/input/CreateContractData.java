package controller.input;

public class CreateContractData {
	private String name;
	private int size;
	private int fee;
	private int deadline;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	public int getDeadline() {
		return deadline;
	}
	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}
	@Override
	public String toString() {
		return "CreateContractMessage [name=" + name + ", size=" + size + ", fee=" + fee + ", deadline=" + deadline
				+ "]";
	}
	
	
}
