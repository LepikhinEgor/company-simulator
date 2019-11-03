package services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aspects.annotations.Loggable;
import controller.input.EmployeeCreateData;
import controller.input.EmployeeUpdateData;
import controller.input.EmployeesListQuerryData;
import dao.EmployeeDao;
import entities.Company;
import entities.Employee;
import entities.User;
import exceptions.DatabaseAccessException;
import exceptions.employees.DoubleEmployeeIdException;
import exceptions.employees.EmployeesListException;
import exceptions.employees.IncorrectOrderNumException;
import exceptions.employees.IncorrectPageNumException;
import services.localization.LocalizationService;
import services.utils.EntitiesConventer;

@Service
public class EmployeeService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
	
	private static final int EMPLOYEES_PAGE_LIMIT = 10;
	
	EmployeeDao employeeDao;
	
	CompanyService companyService;
	
	UserService userService;
	
	EntitiesConventer entitiesConventer;
	
	EmployeeRandomGenerator employeeGenerator;
	
	LocalizationService localizationService;
	
	@Autowired
	public void setLocalizationService(LocalizationService service) {
		this.localizationService = service;
	}
	
	@Autowired
	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	
	@Autowired
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setEntitiesConventer(EntitiesConventer conventer) {
		this.entitiesConventer = conventer;
	}
	
	@Autowired
	public void setEmployeeGenerator(EmployeeRandomGenerator employeeGenerator) {
		this.employeeGenerator = employeeGenerator;
	}
	
	@Loggable
	public List<Employee> generateNewEmployees(String login, TimeZone timezone, Locale locale) throws DatabaseAccessException {
		double companyPopularity = 0.5;
		double companyRespect = 0.5;
		
		Company company = companyService.getUserCompany(login);
		List<Employee> employeesList = employeeGenerator.getGeneratedEmployees(companyPopularity, companyRespect, company.getId(), timezone);
		
		List<Employee> locEmployees = localizationService.localizeEmployees(employeesList, locale);
		return locEmployees;
	}
	
	@Loggable
	public List<Employee> getEmployeesList(EmployeesListQuerryData querryData, String loginEmail) throws DatabaseAccessException, EmployeesListException, IncorrectOrderNumException, IncorrectPageNumException {
		
		if (!isOrderNumCorrect(querryData.getOrderNum()))
			throw new IncorrectOrderNumException("Number of sort order is over bounds");
		
		if (!isPageNumCorrect(querryData.getPageNum()))
			throw new IncorrectPageNumException("Page num must be more then 0");
		
		Company userCompany = companyService.getUserCompany(loginEmail);
		
		try {
			return employeeDao.getEmployeesList(userCompany.getId(), querryData.getOrderNum(), querryData.getPageNum(), EMPLOYEES_PAGE_LIMIT);
		} catch (SQLException e) {
			throw new DatabaseAccessException("Error trying to get employees list");
		}
	}
	
	private boolean isOrderNumCorrect(int orderNum) {
		return orderNum >= 0 && orderNum < 10? true : false;
	}
	
	private boolean isPageNumCorrect(int pageNum) {
		return pageNum >= 0? true : false;
	}
	
	@Loggable
	public Employee updateEmployee(EmployeeUpdateData employeeData, String userLoginEmail) throws DatabaseAccessException {
		Employee newEmployee = entitiesConventer.transformToEmployee(employeeData);
		
		try {
			User user = userService.getUserDataByLoginEmail(userLoginEmail);
			Company company = companyService.getUserCompany(userLoginEmail);
		
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
		Employee newEmployee = entitiesConventer.transformToEmployee(employeeData);
		
		try {
			Company company = companyService.getUserCompany(userLoginEmail);
		
			long createdEmployeeId = employeeDao.createEmployee(newEmployee, company.getId());
			newEmployee.setId(createdEmployeeId);
			
			return newEmployee;
		} catch(SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DatabaseAccessException("Error trying record new employee to database");
		}
	}
	
	@Loggable
	public List<Employee> getContractTeam(String login, long contractId) throws DatabaseAccessException {
		List<Employee> contractTeam = null;
		
		try {
			contractTeam = employeeDao.getContractEmployees(contractId);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DatabaseAccessException(e.getMessage());
		}
		
		return contractTeam;
	}
	
	@Loggable
	public List<Employee> getFreeEmployees(String login) throws DatabaseAccessException {
		List<Employee> freeEmployees = null;
		
		Company company = companyService.getUserCompany(login);
		
		try {
			freeEmployees = employeeDao.getFreeEmployees(company.getId());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DatabaseAccessException(e.getMessage());
		}
		
		return freeEmployees;
	}
}
