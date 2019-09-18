package services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import dao.CompanyDao;
import entities.Company;

public class CompanyService {

	private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);
	
	private CompanyDao companyDao;
	
	@Autowired
	public void CompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}
	
	public Company getUserCompany(long userId) {
		Company userCompany = null;
		userCompany = companyDao.getUserCompany(userId);
		
		if (userCompany == null)
			userCompany = companyDao.recordCompany(userId, "My company");
		
		return userCompany;
	}
	
}
