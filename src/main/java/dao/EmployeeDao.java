package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import domain.Employee;
import domain.UserCompany;

public class EmployeeDao {
	public long createEmployee(Employee employee, long userId) throws SQLException {
		Connection connection = DBConnectionHelper.getConnection();
		
		String addUserQuerry = "INSERT INTO employees (employee_id, name, age, salary, performance, description, company_id) VALUES ("
				+ "NULL, ?, ?, ?, ?, ?, ?);";
		
		long result = 0;
		PreparedStatement createCompanyStatement = null;
		try {
			createCompanyStatement =  connection.prepareStatement(addUserQuerry, Statement.RETURN_GENERATED_KEYS);
						
			createCompanyStatement.setString(1, employee.getName());
			createCompanyStatement.setInt(2, employee.getAge());
			createCompanyStatement.setInt(3, employee.getSalary());
			createCompanyStatement.setInt(4, employee.getPerfomance());
			createCompanyStatement.setString(5, employee.getDescription());
			createCompanyStatement.setLong(6, userId);
			
			int companiesInsert = createCompanyStatement.executeUpdate();
			
			if (companiesInsert == 1)
				return getGeneratedId(createCompanyStatement);
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
