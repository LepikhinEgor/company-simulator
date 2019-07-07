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
	
	public boolean checkLoginAlreadyExist(String userLogin) throws SQLException {
		Connection connection = DBConnectionHelper.getConnection();
		
		String findUserQuerry = "SELECT * FROM users WHERE login = ?;";
		
		PreparedStatement findUserStatement = connection.prepareStatement(findUserQuerry);
		findUserStatement.setString(1, userLogin);
		
		logger.info(findUserStatement.toString());
		
		ResultSet findedUsers = findUserStatement.executeQuery();
		
		return findedUsers.next()? true : false;
	}
	
	public boolean checkEmailAlreadyExist(String userEmail) throws SQLException {
		Connection connection = DBConnectionHelper.getConnection();
		
		String findUserQuerry = "SELECT * FROM users WHERE email = ?;";
		
		PreparedStatement findUserStatement = connection.prepareStatement(findUserQuerry);
		findUserStatement.setString(1, userEmail);
		ResultSet findedUsers = findUserStatement.executeQuery();
		
		return findedUsers.next()? true : false;
	}
	
	public long recordUser(String login, String email, String password) throws SQLException {
		Connection connection = DBConnectionHelper.getConnection();
		
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
