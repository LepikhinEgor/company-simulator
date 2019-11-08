package company_simulator;

import java.sql.SQLException;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import dao.CompanyDao;
import dao.ConnectionPool;
import dao.ContractDao;
import dao.EmployeeDao;
import dao.GeneratedEmployeesDao;
import dao.UserDao;
import services.CompanyService;
import services.ContractService;
import services.EmployeeService;
import services.UserService;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"controller", "dao", "services", "aspects"})
public class TestsDaoBeansConfiguration {
	
	@Bean
	public UserDao userDao(){
		return mock(UserDao.class);
	}
	
	@Bean
	public CompanyDao companyDao(){
		return mock(CompanyDao.class);
	}
	
	@Bean
	public EmployeeDao employeeDao() {
		return mock(EmployeeDao.class);
	}
	
	@Bean
	public ContractDao contractDao() {
		return mock(ContractDao.class);
	}
	
	@Bean
	public ConnectionPool connectionPool() {
		return mock(ConnectionPool.class);
	}
	
	@Bean
	public GeneratedEmployeesDao generatedEmployeesDao() {
		return new GeneratedEmployeesDao();
	}
	
	@Bean
	@Qualifier("mockUserService")
	public UserService userService() {
		return mock(UserService.class);
	}
	
	@Bean
	@Qualifier("mockCompanyService")
	public CompanyService companyService() {
		return mock(CompanyService.class);
	}
	
	@Bean
	@Primary
	public ContractService contractService() {
		return mock(ContractService.class);
	}
	
	@Bean
	@Primary
	public EmployeeService employeesService() {
		return mock(EmployeeService.class);
	}
	
}
