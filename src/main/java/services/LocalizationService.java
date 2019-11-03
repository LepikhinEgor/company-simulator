package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import aspects.annotations.Loggable;
import entities.Employee;

@Service
public class LocalizationService {
	
	@Loggable
	public List<Employee> localizeEmployees(List<Employee> employees, Locale locale) {
		List<Employee> locEmployees = new ArrayList<Employee>();
		
		ResourceBundle bundle = ResourceBundle.getBundle("locale/employees/employees",  locale);
		
		for (Employee employee: employees) {
			locEmployees.add(localizeEmployee(employee, bundle));
		}
		
		return locEmployees;
	}
	
	private Employee localizeEmployee(Employee employee, ResourceBundle bundle) {
		Employee locEmployee = new Employee(employee);
		
		
		String prefix = "";
		switch(employee.getSex()) {
		case Employee.MALE: prefix = "male."; break;
		case Employee.FEMALE: prefix = "female."; break;
		}
		
		String fullLocName = buildEmployeeLocalizeName(employee.getName(), prefix, bundle);
		
		locEmployee.setName(fullLocName);
		locEmployee.setDescription(bundle.getString(prefix + employee.getDescription()));
		
		return locEmployee;
	}
	
	private String buildEmployeeLocalizeName(String fullNameKey, String prefix, ResourceBundle bundle) {
		int spacePos = fullNameKey.indexOf(" ");
		
		String employeeNameKey = prefix + fullNameKey.substring(0, spacePos);
		String employeeSurnameKey = prefix + fullNameKey.substring(spacePos + 1);
		
		String employeeName = bundle.getString(employeeNameKey);
		String employeeSurname = bundle.getString(employeeSurnameKey);
		
		String fullLocName = employeeName + " " + employeeSurname;
		
		return fullLocName;
	}
}
