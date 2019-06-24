package domain;

import java.util.ArrayList;

public class Contract {
	private String name;
	private int perfomanceUnits;
	private int fee;
	/**
	 * deadline - the count of minutes since taking the contract to its deadline
	 */
	private int deadline;
	private int progress;
	private String description;
	private ArrayList<Employee> performers;
	
	public Contract(String name, int fee, int performanceUnits, int deadline, String description) {
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

	public int getDeadline() {
		return deadline;
	}

	public void setDeadline(int deadline) {
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
	public ArrayList<Employee> getPerformers() {
		return performers;
	}
	public void setPerformers(ArrayList<Employee> performers) {
		this.performers = performers;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}
	
	
}
