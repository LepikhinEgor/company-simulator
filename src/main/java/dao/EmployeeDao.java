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
import entities.Company;

public class EmployeeDao {
	
	private final int ORDER_BY_NAME = 0;
	
	@Autowired
	ConnectionPool connectionPool;
	
	@Loggable
	public List<Employee> getEmployeesList(long companyId, int orderNum, int pageNum, int pageLimit) throws SQLException {
		
		String orderBy = "";
		switch(orderNum) {
		case ORDER_BY_NAME: orderBy = "name";break;
		}
		
		try(Connection connection = connectionPool.getConnection();) {
			String getEmployeesQuerry = "SELECT * FROM employees WHERE company_id = ? "
					+ "ORDER BY " + orderBy + " LIMIT " + pageNum*pageLimit + ", " + pageLimit;
			
			PreparedStatement getEmployeesStatement = connection.prepareStatement(getEmployeesQuerry);
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
	public long createEmployee(Employee employee, long companyId) throws SQLException {
		Connection connection = connectionPool.getConnection();
		
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
	
	@Loggable
	public long updateEmployee(Employee employee, long companyId) throws SQLException {
	
		String recordEmployeeQuerry = "UPDATE employees SET name = ?, age = ?, sex = ?, "
				+ "salary = ?, performance = ?, description = ? WHERE employee_id = ?";
		
		PreparedStatement recordEmployeeStatement = null;
		try(Connection connection = connectionPool.getConnection();) {
			recordEmployeeStatement = connection.prepareStatement(recordEmployeeQuerry, Statement.RETURN_GENERATED_KEYS);
						
			recordEmployeeStatement.setString(1, employee.getName());
			recordEmployeeStatement.setInt(2, employee.getAge());
			recordEmployeeStatement.setString(3, employee.getSex());
			recordEmployeeStatement.setInt(4, employee.getSalary());
			recordEmployeeStatement.setInt(5, employee.getPerfomance());
			recordEmployeeStatement.setString(6, employee.getDescription());
			recordEmployeeStatement.setLong(7, employee.getId());
			
			int employeesInsert = recordEmployeeStatement.executeUpdate();
			
			if (employeesInsert == 1)
				return getGeneratedId(recordEmployeeStatement);
			else
				throw new SQLException("Incorrect number of created companies. Required 1");
		}
	}
	
	public List<Employee> getContractEmployees(long contractId) throws SQLException {
		String querry = "SELECT * FROM employees e INNER JOIN work_positions wp ON e.employee_id = wp.employee_id WHERE wp.contract_id = ?"; 
		
		ArrayList<Employee> contractEmployees = new ArrayList<Employee>();
		
		try (Connection connection = connectionPool.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(querry);
			statement.setLong(1, contractId);
			
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				Employee employee = new Employee();
				
				employee.setId(rs.getLong(1));
				employee.setName(rs.getString(2));
				employee.setAge(rs.getInt(3));
				employee.setSex(rs.getString(4));
				employee.setSalary(rs.getInt(5));
				employee.setPerfomance(rs.getInt(6));
				employee.setDescription(rs.getString(7));
				
				contractEmployees.add(employee);
			}
		}
		
		return contractEmployees;
	}
	
	public List<Employee> getFreeEmployees(long companyId) throws SQLException {
		String querry = "SELECT * FROM employees e LEFT JOIN work_positions wp ON e.employee_id = wp.employee_id WHERE wp.contract_id is NULL AND e.company_id = ?"; 
		
		ArrayList<Employee> contractEmployees = new ArrayList<Employee>();
		
		try (Connection connection = connectionPool.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(querry);
			statement.setLong(1, companyId);
			
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				Employee employee = new Employee();
				
				employee.setId(rs.getLong(1));
				employee.setName(rs.getString(2));
				employee.setAge(rs.getInt(3));
				employee.setSex(rs.getString(4));
				employee.setSalary(rs.getInt(5));
				employee.setPerfomance(rs.getInt(6));
				employee.setDescription(rs.getString(7));
				
				contractEmployees.add(employee);
			}
		}
		
		return contractEmployees;
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
