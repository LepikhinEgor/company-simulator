package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDao {
	
	private final static Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	
	public UserDao() {
	}
	
	public boolean signIn(String loginEmail, String password) throws SQLException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		
		String findUserQuerry = "SELECT * FROM users WHERE (email = ? OR login = ?) AND password = ?;";
		
		PreparedStatement findUserStatement = connection.prepareStatement(findUserQuerry);
		findUserStatement.setString(1, loginEmail);
		findUserStatement.setString(2, loginEmail);
		findUserStatement.setString(3, password);
		
		boolean loginExist = true;
		try {
			ResultSet findedUsers = findUserStatement.executeQuery();
			loginExist = findedUsers.next();
		} finally {
			connection.close();
		}
		
		return loginExist;
	}
	
	public boolean signUpByLogin(String login, String password) {
		
		//!!need to complite
		return false;
	}
	
	public boolean checkLoginAlreadyExist(String userLogin) throws SQLException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		
		String findUserQuerry = "SELECT * FROM users WHERE login = ?;";
		
		PreparedStatement findUserStatement = connection.prepareStatement(findUserQuerry);
		findUserStatement.setString(1, userLogin);
		
		logger.info(findUserStatement.toString());
		
		boolean loginExist = true;
		try {
			ResultSet findedUsers = findUserStatement.executeQuery();
			loginExist = findedUsers.next();
		} finally {
			connection.close();
		}
		
		return loginExist;
	}
	
	public boolean checkEmailAlreadyExist(String userEmail) throws SQLException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		
		String findUserQuerry = "SELECT * FROM users WHERE email = ?;";
		
		PreparedStatement findUserStatement = connection.prepareStatement(findUserQuerry);
		findUserStatement.setString(1, userEmail);
		
		boolean emailExist = true;
		try {
			ResultSet findedUsers = findUserStatement.executeQuery();
			emailExist = findedUsers.next();
		} finally {
			connection.close();
		}
		
		return emailExist;
	}
	
	public long recordUser(String login, String email, String password) throws SQLException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		
		String addUserQuerry = "INSERT INTO users (user_id, login, password, email) VALUES ("
				+ "NULL, ?, ?, ?);";
		
		PreparedStatement statement =  connection.prepareStatement(addUserQuerry, Statement.RETURN_GENERATED_KEYS);
		
		statement.setString(1, login);
		statement.setString(2, password);
		statement.setString(3, email);
		
		try {
			logger.info(statement.toString());
			int result = statement.executeUpdate();
			
			if (result != 1)
				throw new SQLException("Incorrect insert user");
			
			return getGeneratedId(statement);
		} finally {
			if (statement != null)
				statement.close();
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
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
	}
}
