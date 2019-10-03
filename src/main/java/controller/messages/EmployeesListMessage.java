package controller.messages;

import java.util.ArrayList;
import java.util.List;

import entities.Employee;

public class EmployeesListMessage {
	
	public static final int SUCCESS = 0;
	public static final int FAIL = 1;
	
	private int status;
	private String description;
	private List<Employee> employees;
	
	public EmployeesListMessage(int status) {
		this.status = status;
		
		switch(status) {
		case SUCCESS: this.description = "Success return employees list"; break;
		case FAIL: this.description = "Fail when getting employees list"; break;
		}
		
		employees = new ArrayList<Employee>();
	}
	
	public EmployeesListMessage(int status, String description) {
		this.status = status;
		this.description = description;
		employees = new ArrayList<Employee>();
	}
	
	public EmployeesListMessage(int status, List<Employee> employees) {
		this.status = status;
		
		switch(status) {
		case SUCCESS: this.description = "Success return employees list"; break;
		case FAIL: this.description = "Fail when getting employees list"; break;
		}
		
		this.employees = employees;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
}
