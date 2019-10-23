package entities;

import java.sql.Timestamp;
import java.util.ArrayList;

import controller.input.CreateContractData;

public class Contract {
	private long id;
	private String name;
	private int perfomanceUnits;
	private int fee;
	private int workSpeed;
	private Timestamp teamChangedDate;
	private long deadline;
	private int lastProgress;
	private String description;
	private boolean isCompleted;
	
	public Contract() {
		super();
	}
	
	public Contract(String name, int fee, int performanceUnits, long deadline, String description) {
		this.name = name;
		this.fee = fee;
		this.perfomanceUnits = performanceUnits;
		this.deadline = deadline;
		this.description = description;
	}
	
	public int getTimeBeforeCompletion() {
		if (workSpeed == 0)
			return -1;
		
		int minutes = (int)Math.ceil((perfomanceUnits - calculateProgress()) / workSpeed);
		
		return minutes;
	}
	
	public int calculateProgress() {
		int progress = 0;
		
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		Timestamp teamChangeTime = teamChangedDate;
		
		int minuteDiff = (int)(currentTime.getTime() - teamChangeTime.getTime()) / (1000 * 60);
		
		progress = lastProgress + minuteDiff * workSpeed;
		
		return progress;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getDeadline() {
		return deadline;
	}

	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	public int getPerfomanceUnits() {
		return perfomanceUnits;
	}
	public void setPerfomanceUnits(int perfomanceUnits) {
		this.perfomanceUnits = perfomanceUnits;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public Timestamp getTeamChangedDate() {
		return teamChangedDate;
	}

	public void setTeamChangedDate(Timestamp teamChangedDate) {
		this.teamChangedDate = teamChangedDate;
	}

	public int getWorkSpeed() {
		return workSpeed;
	}

	public void setWorkSpeed(int workSpeed) {
		this.workSpeed = workSpeed;
	}

	public int getLastProgress() {
		return lastProgress;
	}

	public void setLastProgress(int lastProgress) {
		this.lastProgress = lastProgress;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	@Override
	public String toString() {
		return "Contract [id=" + id + ", name=" + name + ", perfomanceUnits=" + perfomanceUnits + ", fee=" + fee
				+ ", workSpeed=" + workSpeed + ", teamChangedDate=" + teamChangedDate + ", deadline=" + deadline
				+ ", lastProgress=" + lastProgress + ", description=" + description + ", isCompleted=" + isCompleted
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (deadline ^ (deadline >>> 32));
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + fee;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (isCompleted ? 1231 : 1237);
		result = prime * result + lastProgress;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + perfomanceUnits;
		result = prime * result + ((teamChangedDate == null) ? 0 : teamChangedDate.hashCode());
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
		Contract other = (Contract) obj;
		if (deadline != other.deadline)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (fee != other.fee)
			return false;
		if (id != other.id)
			return false;
		if (isCompleted != other.isCompleted)
			return false;
		if (lastProgress != other.lastProgress)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (perfomanceUnits != other.perfomanceUnits)
			return false;
		if (teamChangedDate == null) {
			if (other.teamChangedDate != null)
				return false;
		} else if (!teamChangedDate.equals(other.teamChangedDate))
			return false;
		if (workSpeed != other.workSpeed)
			return false;
		return true;
	}
	
	
	
}
