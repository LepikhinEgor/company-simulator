package services;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aspects.annotations.Loggable;
import controller.messages.CompanyInfoMessage;
import dao.CompanyDao;
import dao.EmployeeDao;
import entities.Company;
import entities.Employee;
import entities.User;
import exceptions.DatabaseAccessException;
import exceptions.employees.UserNotFoundException;

@Service
public class CompanyService {

	private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);
	
	private CompanyDao companyDao;
	private EmployeeDao employeeDao;
	
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}
	
	@Autowired
	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	
	@Loggable
	public Company getCompanyById(long companyId) throws DatabaseAccessException {
		Company company = null;
		
		try {
			company = companyDao.getCompanyById(companyId);
		} catch (SQLException e) {
			throw new DatabaseAccessException("Exception when getting comapny by id");
		}
		
		return company;
	}
	
	/**
	 * @param loginEmail user login or email
	 * @return company information
	 * @throws DatabaseAccessException, {@link UserNotFoundException}
	 */
	public CompanyInfoMessage getCompanyInfo(String loginEmail) throws DatabaseAccessException {
		CompanyInfoMessage companyInfo = new CompanyInfoMessage();
		
		User user = userService.getUserDataByLoginEmail(loginEmail);
		
		if (user == null) {
			throw new UserNotFoundException();
		}
		companyInfo.setOwnerName(user.getLogin());
		
		Company userCompany = getUserCompany(user.getId());
		companyInfo.setCash(userCompany.getCash());
		companyInfo.setId(userCompany.getId());
		companyInfo.setName(userCompany.getName());
		companyInfo.setDefaultCash(userCompany.getDefaultCash());
		
		companyInfo.setContractsCompleted(3);
		companyInfo.setContractsExecuting(2);
		companyInfo.setContractsFailed(5);
		
		return companyInfo;
	}

	@Loggable
	public Company getUserCompany(String loginEmail) throws DatabaseAccessException {
		Company company;
		
		try {
			company = companyDao.getUserCompany(loginEmail);
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new DatabaseAccessException(e.getMessage());
		}
		
		if (company == null) {
			company = createCompany(loginEmail); 
		}
		
		updateCompanyCash(company);
		
		return company;
	}
	
	/**
	 * @param userId Company owner ID
	 * @return user company if it exist, else will created new company for this user
	 * @throws DatabaseAccessException
	 */
	@Loggable
	public Company getUserCompany(long userId) throws DatabaseAccessException {
		Company userCompany = null;
		
		//search exist user company
		try {
			userCompany = companyDao.getUserCompany(userId);
		} catch (SQLException e) {
			throw new DatabaseAccessException("Error trying to read user company from database");
		}
		
		//if company not exist it will created
		if (userCompany == null) {
			userCompany = createCompany(userId); 
		}
		
		updateCompanyCash(userCompany);
				
		return userCompany;
	}
	
	public List<Employee> getCompanyEmployees(long companyId) throws DatabaseAccessException {
		List<Employee> companyEmployees = null;
		
		try {
			companyEmployees = employeeDao.getCompanyEmployees(companyId);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DatabaseAccessException("Error getting company employees");
		}
		
		return companyEmployees;
	}
	
	private void updateCompanyCash(Company company) throws DatabaseAccessException {
		long actualCash = getActualCompanyCash(company);
		
		try {
			if (company.getCash() != actualCash) {
				companyDao.updateCompanyCash(actualCash, company.getId());
				company.setCash(actualCash);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);

		}
	}
	//42277876 18:36
	private long getActualCompanyCash(Company company) throws DatabaseAccessException {
		Timestamp timing = company.getCashUpdatedTiming();
		
		int hrCosts = getCompanyHrCostsPerHour(company.getId());
		
		long curHour = getHourNum(new Timestamp(System.currentTimeMillis()));
		long cashUpdateHour = getHourNum(timing);
		int hoursDiff = (int)(curHour - cashUpdateHour);
		
		long actualCash = company.getCash() - hoursDiff * hrCosts;
		logger.info("Old cash - " + company.getCash() + " new cash - " + actualCash + 
				" hrCosts - " + hrCosts + " hoursDiffs -" + hoursDiff);
		
		return actualCash;
	}
	
	private long getHourNum(Timestamp timestamp) {
		return timestamp.getTime() / (60 * 60 * 1000);
	}
	
	private int getCompanyHrCostsPerHour(long companyId) throws DatabaseAccessException {
		int hrCosts = 0;
		
		List<Employee> employees = getCompanyEmployees(companyId);
		
		for (Employee employee: employees) {
			hrCosts += employee.getSalary() * 60;
		}
		
		return hrCosts;
	}
	
	private Company createCompany(String loginEmail) throws DatabaseAccessException {
		User user = userService.getUserDataByLoginEmail(loginEmail);
		
		return createCompany(user.getId());
	}
	
	private Company createCompany(long userId) throws DatabaseAccessException {
		Company newCompany = new Company();
		newCompany.setOwnerId(userId);
		
		long companyId;
		try {
			companyId = companyDao.recordCompany(newCompany);
			newCompany.setId(companyId);
		} catch (SQLException e) {
			throw new DatabaseAccessException("Error trying to record user company to database");
		}	
		
		return newCompany;
	}
	
}
