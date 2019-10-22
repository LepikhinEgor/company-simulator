package controller.messages.entities;

import java.sql.Timestamp;

public class ContractRestData {
	private long id;
	private String name;
	private int size;
	private int fee;
	private int workSpeed;
	private long deadline;
	private int progress;
	private String description;
	private int expectedCompletionTime;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
	public int getWorkSpeed() {
		return workSpeed;
	}
	public void setWorkSpeed(int workSpeed) {
		this.workSpeed = workSpeed;
	}
	public long getDeadline() {
		return deadline;
	}
	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getExpectedCompletionTime() {
		return expectedCompletionTime;
	}
	public void setExpectedCompletionTime(int expectedCompletionTime) {
		this.expectedCompletionTime = expectedCompletionTime;
	}
	
	
}
