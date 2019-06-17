package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import domain.User;
import domain.UserCompany;

public class CompanyDao {
	
	public int createCompany(Connection connection, User user, String name) throws SQLException {
		
		String addUserQuerry = "INSERT INTO companies (company_id, name, cash, owner_id) VALUES ("
				+ "NULL, ?, ?, ?);";
		
		int result = 0;
		PreparedStatement createCompanyStatement = null;
		try {
			createCompanyStatement =  connection.prepareStatement(addUserQuerry);
			
			UserCompany newUserCompany = new UserCompany(name);
			
			createCompanyStatement.setString(1, newUserCompany.getName());
			createCompanyStatement.setLong(2, newUserCompany.getCash());
			createCompanyStatement.setLong(3, user.getId());
			
			result = createCompanyStatement.executeUpdate();
		} finally {
			if (createCompanyStatement != null)
				createCompanyStatement.close();
		}
		
		return result;
		
	}
	
	
}
