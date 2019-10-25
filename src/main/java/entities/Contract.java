package entities;

import java.sql.Timestamp;
import java.util.ArrayList;

import controller.input.CreateContractData;

public class Contract {
	public static final String PERFORMED = "Performed";
	public static final String COMPLETED = "Completed";
	public static final String FAILED = "Failed";
	public static final String RESOLVED_COMPLETED = "Resolved completed";
	public static final String RESOLVED_FAILED = "Resolved failed";
	
	private long id;
	private String name;
	private int perfomanceUnits;
	private int fee;
	private int workSpeed;
	private Timestamp teamChangedDate;
	private Timestamp deadline;
	private int lastProgress;
	private String description;
	private String status;
	
	public Contract() {
		super();
	}
	
	public int getTimeBeforeCompletion() {
		if (workSpeed == 0)
			return -1;
		
		int minutes = (int)Math.ceil((perfomanceUnits - calculateProgress()) / workSpeed);
		
		return minutes;
	}
	
	public int calculateProgress() {//todo calculate by deadline
		int progress = 0;
		
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		Timestamp teamChangeTime = teamChangedDate;
		
		int minuteDiff = (int)(currentTime.getTime() - teamChangeTime.getTime()) / (1000 * 60);
		
		progress = lastProgress + minuteDiff * workSpeed;
		
		return progress;
	}
	
	public String getActualStatus() {
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		if (deadline.before(currentTime)) {
			if (calculateProgress() >= perfomanceUnits)
				return COMPLETED;
			else
				return FAILED;
		} else {
			if (calculateProgress() >= perfomanceUnits)
				return COMPLETED;
			else 
				return PERFORMED;
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getDeadline() {
		return deadline;
	}

	public void setDeadline(Timestamp deadline) {
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

	public String getStatus() {
		return getActualStatus();
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Contract [id=" + id + ", name=" + name + ", perfomanceUnits=" + perfomanceUnits + ", fee=" + fee
				+ ", workSpeed=" + workSpeed + ", teamChangedDate=" + teamChangedDate + ", deadline=" + deadline
				+ ", lastProgress=" + lastProgress + ", description=" + description + ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deadline == null) ? 0 : deadline.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + fee;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + lastProgress;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + perfomanceUnits;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		if (deadline == null) {
			if (other.deadline != null)
				return false;
		} else if (!deadline.equals(other.deadline))
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
		if (lastProgress != other.lastProgress)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (perfomanceUnits != other.perfomanceUnits)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
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
