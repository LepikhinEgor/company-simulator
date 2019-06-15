package company_simulator;

import dao.CompanyDao;
import domain.User;
import domain.UserCompany;

public class Main {
	public static void main(String[] args) {
		User user = new User("EgorL", "root");
		CompanyDao companyDao = new CompanyDao();
		UserCompany newUserCompany = companyDao.createCompany(user.getId());
		
		System.out.println(newUserCompany.getName());
	}
}
