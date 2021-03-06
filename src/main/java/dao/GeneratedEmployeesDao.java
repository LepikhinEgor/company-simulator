package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
	public List<Employee> getOldGeneratedEmployees(long companyId) throws SQLException {
		String querry = "SELECT * FROM generated_employees WHERE company_id = ?";
		
		try(Connection connection = connectionPool.getConnection();) {
			
			PreparedStatement getEmployeesStatement = connection.prepareStatement(querry);
			getEmployeesStatement.setLong(1, companyId);
			
			ResultSet foundEmployees = getEmployeesStatement.executeQuery();
			
			ArrayList<Employee> employees = new ArrayList<Employee>(); 
			
			while(foundEmployees.next()) {
				Employee employee = new Employee();
				
				employee.setId(foundEmployees.getLong(1));
				employee.setName(foundEmployees.getString(2));
				employee.setAge(foundEmployees.getInt(3));
				employee.setSex(foundEmployees.getString(4));
				employee.setSalary(foundEmployees.getInt(5));
				employee.setPerfomance(foundEmployees.getInt(6));
				employee.setDescription(foundEmployees.getString(7));
				
				employees.add(employee);
			}
			
			return employees;
		}
	}
	
	@Loggable
	public long getEmployeesRecordTiming(long companyId) throws SQLException {
		String querry = "SELECT employees_generate_timing FROM  timings WHERE company_id = ?";
		
		long timing = 0;
		
		try(Connection connection = connectionPool.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(querry);
			statement.setLong(1, companyId);
			
			ResultSet rs = statement.executeQuery();
			if (rs.next())
				timing = rs.getLong(1);
		}
		
		return timing;
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
		String querry = "INSERT INTO timings(id, company_id, employees_generate_timing, contracts_generate_timing) VALUES (NULL, ?, 0, 0)";
		
		try(PreparedStatement statement = connection.prepareStatement(querry)) {
			statement.setLong(1, companyId);
			
			statement.executeUpdate();
		}
	}
}
