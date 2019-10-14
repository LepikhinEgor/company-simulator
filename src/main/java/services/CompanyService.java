package services;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aspects.annotations.Loggable;
import controller.messages.CompanyInfoMessage;
import dao.CompanyDao;
import entities.Company;
import entities.User;
import exceptions.DatabaseAccessException;
import exceptions.employees.UserNotFoundException;

@Service
public class CompanyService {

	private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);
	
	private CompanyDao companyDao;
	
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
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
			long companyId;
			try {
				companyId = companyDao.recordCompany(userCompany);
			} catch (SQLException e) {
				throw new DatabaseAccessException("Error trying to record user company to database");
			}	
			userCompany.setId(companyId);
		}
				
		return userCompany;
	}
	
	private Company createCompany(long userId) {
		Company newCompany = new Company();
		newCompany.setOwnerId(userId);
		
		return newCompany;
	}
	
}
