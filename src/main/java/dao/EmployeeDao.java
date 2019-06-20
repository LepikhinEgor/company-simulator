package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import domain.Employee;
import domain.UserCompany;

public class EmployeeDao {
	public long createEmployee(Employee employee, long companyId) throws SQLException {
		Connection connection = DBConnectionHelper.getConnection();
		
		String recordEmployeeQuerry = "INSERT INTO employees (employee_id, name, age, sex, salary, performance, description, company_id) VALUES ("
				+ "NULL, ?, ?, ?, ?, ?, ?, ?);";
		
		PreparedStatement recordEmployeeStatement = null;
		try {
			recordEmployeeStatement = connection.prepareStatement(recordEmployeeQuerry, Statement.RETURN_GENERATED_KEYS);
						
			recordEmployeeStatement.setString(1, employee.getName());
			recordEmployeeStatement.setInt(2, employee.getAge());
			recordEmployeeStatement.setString(3, employee.getSex());
			recordEmployeeStatement.setInt(4, employee.getSalary());
			recordEmployeeStatement.setInt(5, employee.getPerfomance());
			recordEmployeeStatement.setString(6, employee.getDescription());
			recordEmployeeStatement.setLong(7, companyId);
			
			int employeesInsert = recordEmployeeStatement.executeUpdate();
			
			if (employeesInsert == 1)
				return getGeneratedId(recordEmployeeStatement);
			else
				throw new SQLException("Incorrect number of created companies. Required 1");
		} finally {
			if (recordEmployeeStatement != null)
				recordEmployeeStatement.close();
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
