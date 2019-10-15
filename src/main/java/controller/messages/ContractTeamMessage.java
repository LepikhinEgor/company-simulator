package controller.messages;

import java.util.List;

import entities.Employee;

public class ContractTeamMessage extends Message{
	private List<Employee> hiredEmployees;
	private List<Employee> freeEmployees;
	
	public ContractTeamMessage(int status) {
		super(status);
	}
	
	public ContractTeamMessage(int status, String message) {
		super(status, message);
	}
	
	public ContractTeamMessage(int status, String message, List<Employee> hiredEmployees, List<Employee> freeEmployees) {
		super(status, message);
		
		this.hiredEmployees = hiredEmployees;
		this.freeEmployees = freeEmployees;
	}
	
	public List<Employee> getHiredEmployees() {
		return hiredEmployees;
	}
	public void setHiredEmployees(List<Employee> hiredEmployees) {
		this.hiredEmployees = hiredEmployees;
	}
	public List<Employee> getFreeEmployees() {
		return freeEmployees;
	}
	public void setFreeEmployees(List<Employee> freeEmployees) {
		this.freeEmployees = freeEmployees;
	}
}
