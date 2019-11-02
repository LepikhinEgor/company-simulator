package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import aspects.annotations.Loggable;
import entities.Employee;

public class GeneratedEmployeesDao {
	
	private ConnectionPool connectionPool;
	
	@Autowired
	public void setConnecionPool(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}
	
	@Loggable
	public List<Employee> recordGeneratedEmployees(List<Employee> employees, long companyId, long timing) throws SQLException {
		List<Employee> employeesWithId = null;
		
		Connection connection = connectionPool.getConnection();
		
		connection.setAutoCommit(false);
		
		deleteOldGeneratedEmployees(companyId, connection);
		employeesWithId = recordNewGeneratedEmployees(employees, companyId, connection);
		changeEmployeesUpdatedTiming(timing, companyId, connection);
		
		connection.commit();
		
		return employeesWithId;
	}

	private void deleteOldGeneratedEmployees(long companyId, Connection connection) throws SQLException {
		String querry = "DELETE FROM generated_employees WHERE company_id = ?";
		
		PreparedStatement statement =  null;
		try {
			statement = connection.prepareStatement(querry);
			statement.setLong(1, companyId);
			
			statement.executeUpdate();
		} finally {
			statement.close();
		}
	}
	
	private List<Employee> recordNewGeneratedEmployees(List<Employee> employees, long companyId, Connection connection) throws SQLException {
		String querry = "INSERT INTO generated_employees(employee_id, name, age, sex, salary, performance, description, company_id) "
				+ "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)";
		
		try(PreparedStatement statement = connection.prepareStatement(querry, Statement.RETURN_GENERATED_KEYS)) {
			for (Employee employee : employees) {
				statement.setString(1, employee.getName());
				statement.setInt(2, employee.getAge());
				statement.setString(3, employee.getSex());
				statement.setInt(4, employee.getSalary());
				statement.setInt(5, employee.getPerfomance());
				statement.setString(6, employee.getDescription());
				statement.setLong(7, companyId);
				
				statement.executeUpdate();
				
				ResultSet generatedIds = statement.getGeneratedKeys();
				generatedIds.next();
				employee.setId(generatedIds.getLong(1));
			}
			
		}
		
		return employees;
	}
	
	private void changeEmployeesUpdatedTiming(long timing, long companyId, Connection connection) throws SQLException {
		String querry = "UPDATE timings SET employees_generate_timing = ? WHERE company_id = ?";
		
		try(PreparedStatement statement = connection.prepareStatement(querry)) {
			statement.setLong(1, timing);
			statement.setLong(2, companyId);
			
			statement.executeUpdate();
		}
	}
	
	@Loggable
	public void createCompaniesTiming(long companyId, Connection connection) throws SQLException {
		String querry = "INSERT INTO timings(id, company_id, employees_generate_timing, contracts_generate_timing) "
				+ "VALUES (NULL, ?, 0, 0)";
		
		try(PreparedStatement statement = connection.prepareStatement(querry)) {
			statement.setLong(1, companyId);
			
			statement.executeUpdate();
		}
	}
}
