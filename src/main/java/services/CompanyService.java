package services;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aspects.annotations.Loggable;
import dao.CompanyDao;
import entities.Company;
import exceptions.DatabaseAccessException;

@Service
public class CompanyService {

	private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);
	
	private CompanyDao companyDao;
	
	@Autowired
	public void CompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
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
