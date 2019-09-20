package controller.messages;

import entities.Employee;

public class EmployeesListMessage {
	
	public static final int SUCCESS = 0;
	public static final int FAIL = 1;
	
	private int status;
	private String description;
	private Employee[] employees;
	
	public EmployeesListMessage(int status) {
		this.status = status;
		
		switch(status) {
		case SUCCESS: this.description = "Success return employees list"; break;
		case FAIL: this.description = "Fail when getting employees list"; break;
		}
		
		employees = new Employee[0];
	}
	
	public EmployeesListMessage(int status, String description) {
		this.status = status;
		this.description = description;
		employees = new Employee[0];
	}
	
	public EmployeesListMessage(int status, Employee[] employees) {
		this.status = status;
		
		switch(status) {
		case SUCCESS: this.description = "Success return employees list"; break;
		case FAIL: this.description = "Fail when getting employees list"; break;
		}
		
		this.employees = employees;
	}
	
}
