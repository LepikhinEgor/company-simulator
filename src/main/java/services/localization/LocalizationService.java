package services.localization;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.omg.CORBA.LocalObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import aspects.annotations.Loggable;
import entities.Contract;
import entities.Employee;

@Service
public class LocalizationService {
	
	@Loggable
	public List<Contract> localizeContracts(List<Contract> contracts, Locale locale) {
		List<Contract> localizedContracts = new ArrayList<Contract>();
		
		LocalizationResources locResources = LocalizationResources.getLocalizationClass(locale);
		ResourceBundle contractsBundle = locResources.getContractsBundle();
		
		for (Contract contract : contracts) {
			localizedContracts.add(localizeContract(contract, contractsBundle));
		}
		
		return localizedContracts;
	}
	
	@Loggable
	public List<Employee> localizeEmployees(List<Employee> employees, Locale locale) {
		List<Employee> locEmployees = new ArrayList<Employee>();
		
		LocalizationResources locResources = LocalizationResources.getLocalizationClass(locale);
		ResourceBundle employeesBundle = locResources.getEmployeesBundle();
		
		for (Employee employee: employees) {
			locEmployees.add(localizeEmployee(employee, employeesBundle));
		}
		
		return locEmployees;
	}
	
	private Contract localizeContract(Contract contract, ResourceBundle bundle) {
		Contract locContract = new Contract(contract);
		
		locContract.setName(bundle.getString(contract.getName()));
		locContract.setDescription(bundle.getString(contract.getDescription()));
		
		return locContract;
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
