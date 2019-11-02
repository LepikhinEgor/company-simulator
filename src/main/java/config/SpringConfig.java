package config;

import java.sql.SQLException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import dao.CompanyDao;
import dao.ConnectionPool;
import dao.ContractDao;
import dao.EmployeeDao;
import dao.GeneratedEmployeesDao;
import dao.UserDao;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"controller", "dao", "services", "aspects"})
public class SpringConfig {
	
	@Bean
	public UserDao userDao(){
		return new UserDao();
	}
	
	@Bean
	public CompanyDao companyDao(){
		return new CompanyDao();
	}
	
	@Bean
	public EmployeeDao employeeDao() {
		return new EmployeeDao();
	}
	
	@Bean
	public ContractDao contractDao() {
		return new ContractDao();
	}
	
	@Bean
	public ConnectionPool connectionPool() {
		return new ConnectionPool();
	}
	
	@Bean
	public GeneratedEmployeesDao generatedEmployeesDao() {
		return new GeneratedEmployeesDao();
	}
}
