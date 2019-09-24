package services;

import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aspects.annotations.Loggable;
import controller.messages.EmployeeCreateData;
import controller.messages.EmployeeUpdateData;
import controller.messages.EmployeesListQuerryData;
import dao.EmployeeDao;
import entities.Company;
import entities.Employee;
import entities.User;
import exceptions.DatabaseAccessException;
import exceptions.employees.EmployeesListException;

@Service
public class EmployeeService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
	
	private static final int EMPLOYEES_PAGE_LIMIT = 10;
	
	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	UserService userService;
	
	@Loggable
	public Employee[] getEmployeesList(EmployeesListQuerryData querryData, String loginEmail) throws DatabaseAccessException, EmployeesListException {
		User userData = null;
		Employee[] employees = null;
		Company userCompany = null;
		
		userData = userService.getUserDataByLoginEmail(loginEmail);
		if (userData == null)
			throw new EmployeesListException("User with login " + loginEmail + " has not been found");
		
		userCompany = companyService.getUserCompany(userData.getId());
		
		try {
			return employeeDao.getEmployeesList(userCompany.getId(), querryData.getOrderNum(), querryData.getPageNum(), EMPLOYEES_PAGE_LIMIT);
		} catch (SQLException e) {
			throw new DatabaseAccessException("Error trying to get employees list");
		}
	}
	
	@Loggable
	public Employee updateEmployee(EmployeeUpdateData employeeData, String userLoginEmail) throws DatabaseAccessException {
		Employee newEmployee = new Employee(employeeData);
		
		try {
			User user = userService.getUserDataByLoginEmail(userLoginEmail);
			Company company = companyService.getUserCompany(user.getId());
		
			long createdEmployeeId = employeeDao.updateEmployee(newEmployee, company.getId());
			newEmployee.setId(createdEmployeeId);
			
			return newEmployee;
		} catch(SQLException e) {
			throw new DatabaseAccessException("Error trying record new employee to database");
		} catch(DatabaseAccessException e) {
			throw new DatabaseAccessException("Error trying record new employee to database");
		}
	}
	
	@Loggable
	public Employee createEmployee(EmployeeCreateData employeeData, String userLoginEmail) throws DatabaseAccessException {
		Employee newEmployee = new Employee(employeeData);
		
		try {
			User user = userService.getUserDataByLoginEmail(userLoginEmail);
			Company company = companyService.getUserCompany(user.getId());
		
			long createdEmployeeId = employeeDao.createEmployee(newEmployee, company.getId());
			newEmployee.setId(createdEmployeeId);
			
			return newEmployee;
		} catch(SQLException e) {
			throw new DatabaseAccessException("Error trying record new employee to database");
		} catch(DatabaseAccessException e) {
			throw new DatabaseAccessException("Error trying record new employee to database");
		}
	}
}
