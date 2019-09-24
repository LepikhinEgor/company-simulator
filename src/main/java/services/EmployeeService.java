package services;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aspects.annotations.Loggable;
import controller.messages.EmployeeCreateData;
import controller.messages.EmployeeUpdateData;
import dao.EmployeeDao;
import entities.Company;
import entities.Employee;
import entities.User;
import exceptions.DatabaseAccessException;

@Service
public class EmployeeService {
	
	private static final int EMPLOYEES_PAGE_LIMIT = 10;
	
	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	UserService userService;
	
	@Loggable
	public Employee[] getEmployeesList(long companyId, int orderNum,int pageNum) throws DatabaseAccessException {
		try {
			return employeeDao.getEmployeesList(companyId, orderNum, pageNum, EMPLOYEES_PAGE_LIMIT);
		} catch (SQLException e) {
			throw new DatabaseAccessException("Error trying to get employees list");
		}
	}
	
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
