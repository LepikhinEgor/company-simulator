package company_simulator;

import java.sql.SQLException;
import static org.mockito.Mockito.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import dao.CompanyDao;
import dao.ConnectionPool;
import dao.ContractDao;
import dao.EmployeeDao;
import dao.UserDao;

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
}
