package dao;

import domain.UserCompany;

public class CompanyDao {
	public UserCompany createCompany(long userId) {
		return new UserCompany("Horns and hooves");
	}
}
