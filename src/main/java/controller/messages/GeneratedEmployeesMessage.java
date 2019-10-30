package controller.messages;

import java.util.List;

import entities.Employee;

public class GeneratedEmployeesMessage extends Message{
	
	private List<Employee> employees;
	
	public GeneratedEmployeesMessage(int status) {
		super(status);
	}
	
	public GeneratedEmployeesMessage(int status, String message) {
		super(status, message);
	}
	
	public GeneratedEmployeesMessage(int status, List<Employee> employees) {
		super(status);
		this.employees = employees;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
}
