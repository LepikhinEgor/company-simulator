package entities;

import java.util.ArrayList;

import controller.input.CreateContractData;

public class Contract {
	private int id;
	private String name;
	private int perfomanceUnits;
	private int fee;
	/**
	 * deadline - the count of millis since taking the contract to its deadline
	 */
	private long deadline;
	private long progress;
	private String description;
	
	public Contract() {
		super();
	}
	
	public Contract(String name, int fee, int performanceUnits, long deadline, String description) {
		this.name = name;
		this.fee = fee;
		this.perfomanceUnits = performanceUnits;
		this.deadline = deadline;
		this.progress = 0;
		this.description = description;
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

	public long getProgress() {
		return progress;
	}

	public void setProgress(long progress) {
		this.progress = progress;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
