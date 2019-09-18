package config;

import java.sql.SQLException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import dao.CompanyDao;
import dao.UserDao;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"controller", "dao", "services", "aspects"})
public class SpringConfig {
	
	@Bean
	public UserDao userDao() throws SQLException {
		return new UserDao();
	}
	
	@Bean
	public CompanyDao companyDao() throws SQLException {
		return new CompanyDao();
	}
}
