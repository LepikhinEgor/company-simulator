package entities;

import java.util.ArrayList;

public class CompanyTeam {
	private ArrayList<Employee> employees;
	
	public CompanyTeam() {
		employees = new ArrayList<Employee>();
	}
	
	public ArrayList<Employee> getEmployees() {
		return employees;
	}
	
	public void addEmployee(Employee employee) {
		employees.add(employee);
	}
}
