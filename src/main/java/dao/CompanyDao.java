package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import aspects.annotations.Loggable;
import entities.User;
import entities.Company;

public class CompanyDao {
	
	/**
	 * @param userId Company owner ID
	 * @return Return user company if it exist, else return null
	 * @throws SQLException
	 */
	@Loggable
	public Company getUserCompany(long userId) throws SQLException {
		String getCompanyQuerry = "SELECT * FROM companies WHERE owner_id = ?";
		
		try(Connection connection = DBConnectionHelper.getConnection()) {
			PreparedStatement getCompanyStatement = connection.prepareStatement(getCompanyQuerry);
			getCompanyStatement.setLong(1, userId);
			
			ResultSet foundCompaniesSet = getCompanyStatement.executeQuery();
			
			if (!foundCompaniesSet.next())
				return null;
			
			Company foundCompany = new Company();
			foundCompany.setId(foundCompaniesSet.getLong(1));
			foundCompany.setName(foundCompaniesSet.getString(2));
			foundCompany.setCash(foundCompaniesSet.getLong(3));
			foundCompany.setOwnerId(foundCompaniesSet.getLong(4));
			
			foundCompaniesSet.next();
			
			return foundCompany;
		}
		
	}
	
	/**
	 * 
	 * @param loginEmail
	 * @return user company, if it's not found return null
	 * @throws SQLException
	 */
	@Loggable
	public Company getUserCompany(String loginEmail) throws SQLException {
		String getCompanyQuerry = "SELECT c.company_id, c.name, c.cash, c.owner_id FROM companies c INNER JOIN users u ON u.user_id = c.owner_id WHERE u.login = ? OR u.email = ?";
		
		try(Connection connection = DBConnectionHelper.getConnection()) {
			PreparedStatement getCompanyStatement = connection.prepareStatement(getCompanyQuerry);
			getCompanyStatement.setString(1, loginEmail);
			getCompanyStatement.setString(2, loginEmail);
			
			ResultSet foundCompaniesSet = getCompanyStatement.executeQuery();
			
			if (!foundCompaniesSet.next())
				return null;
			
			Company foundCompany = new Company();
			foundCompany.setId(foundCompaniesSet.getLong(1));
			foundCompany.setName(foundCompaniesSet.getString(2));
			foundCompany.setCash(foundCompaniesSet.getLong(3));
			foundCompany.setOwnerId(foundCompaniesSet.getLong(4));
			
			foundCompaniesSet.next();
			
			return foundCompany;
		}
	}
	
	@Loggable
	public long recordCompany(Company newCompany) throws SQLException {
		Connection connection = DBConnectionHelper.getConnection();
		
		String recordCompanyQuerry = "INSERT INTO companies (company_id, name, cash, owner_id) VALUES ("
				+ "NULL, ?, ?, ?);";
		
		PreparedStatement createCompanyStatement = null;
		try {
			createCompanyStatement =  connection.prepareStatement(recordCompanyQuerry, Statement.RETURN_GENERATED_KEYS);
			
			createCompanyStatement.setString(1, newCompany.getName());
			createCompanyStatement.setLong(2, newCompany.getCash());
			createCompanyStatement.setLong(3, newCompany.getOwnerId());
			
			int companiesInsert = createCompanyStatement.executeUpdate();
			
			if (companiesInsert == 1) {
				return getGeneratedId(createCompanyStatement);
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
