package controller.messages;

import java.util.ArrayList;
import java.util.List;

import entities.Employee;

public class EmployeesListMessage extends Message{
	
	private List<Employee> employees;
	
	public EmployeesListMessage(int status) {
		this.status = status;
		
		switch(status) {
		case SUCCESS: this.message = "Success return employees list"; break;
		case FAIL: this.message = "Fail when getting employees list"; break;
		}
		
		employees = new ArrayList<Employee>();
	}
	
	public EmployeesListMessage(int status, String message) {
		super(status, message);
		employees = new ArrayList<Employee>();
	}
	
	public EmployeesListMessage(int status, List<Employee> employees) {
		this.status = status;
		
		switch(status) {
		case SUCCESS: this.message = "Success return employees list"; break;
		case FAIL: this.message = "Fail when getting employees list"; break;
		}
		
		if (employees == null)
			throw new IllegalArgumentException("employees list must be not null");
		
		this.employees = employees;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		if (employees == null)
			throw new IllegalArgumentException("employees list must be not null");
		
		this.employees = employees;
	}
	
}
