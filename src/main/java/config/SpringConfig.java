package config;

import java.sql.SQLException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import dao.UserDao;

@Configuration
@ComponentScan(basePackages = {"controller", "dao", "services"})
public class SpringConfig {
	
	@Bean
	public UserDao userDao() throws SQLException {
		return new UserDao();
	}
}
