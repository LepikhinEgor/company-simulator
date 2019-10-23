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
	@Override
	public String toString() {
		return "ContractRestData [id=" + id + ", name=" + name + ", size=" + size + ", fee=" + fee + ", workSpeed="
				+ workSpeed + ", deadline=" + deadline + ", progress=" + progress + ", description=" + description
				+ ", expectedCompletionTime=" + expectedCompletionTime + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (deadline ^ (deadline >>> 32));
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + expectedCompletionTime;
		result = prime * result + fee;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + progress;
		result = prime * result + size;
		result = prime * result + workSpeed;
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
		ContractRestData other = (ContractRestData) obj;
		if (deadline != other.deadline)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (expectedCompletionTime != other.expectedCompletionTime)
			return false;
		if (fee != other.fee)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (progress != other.progress)
			return false;
		if (size != other.size)
			return false;
		if (workSpeed != other.workSpeed)
			return false;
		return true;
	}
}
