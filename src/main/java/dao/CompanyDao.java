package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entities.User;
import entities.Company;

public class CompanyDao {
	
	public Company recordCompany(long userId, String name) throws SQLException {
		Connection connection = DBConnectionHelper.getConnection();
		
		String recordCompanyQuerry = "INSERT INTO companies (company_id, name, cash, owner_id) VALUES ("
				+ "NULL, ?, ?, ?);";
		
		PreparedStatement createCompanyStatement = null;
		try {
			createCompanyStatement =  connection.prepareStatement(recordCompanyQuerry, Statement.RETURN_GENERATED_KEYS);
			
			Company newUserCompany = new Company(name);
			
			createCompanyStatement.setString(1, newUserCompany.getName());
			createCompanyStatement.setLong(2, newUserCompany.getCash());
			createCompanyStatement.setLong(3, userId);
			
			int companiesInsert = createCompanyStatement.executeUpdate();
			
			if (companiesInsert == 1) {
				newUserCompany.setId(getGeneratedId(createCompanyStatement));
				return newUserCompany;
			}
			else
				throw new SQLException("Incorrect number of created companies. Required 1");
		} finally {
			if (createCompanyStatement != null)
				createCompanyStatement.close();
			if (connection != null)
				connection.close();
		}
	}
	
	private long getGeneratedId(PreparedStatement statement) throws SQLException {
		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
            else {
                throw new SQLException("Creating company failed, no ID obtained.");
            }
        }
	}
	
	
}
