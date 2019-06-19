package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDao {
	
	private final String URL = "jdbc:mysql://localhost:3306/company_simulator"+
            "?verifyServerCertificate=false"+
            "&useSSL=false"+
            "&requireSSL=false"+
            "&useLegacyDatetimeCode=false"+
            "&amp"+
            "&serverTimezone=UTC";
	private final String username = "egor";
	private final String password = "1111";
	
	public long createNewUser(String nickname, String password) throws SQLException {
		Connection connection = DriverManager.getConnection(URL, "egor", "1111");
		
		String addUserQuerry = "INSERT INTO users (user_id, name, password) VALUES ("
				+ "NULL, ?, ?);";
		
		PreparedStatement statement =  connection.prepareStatement(addUserQuerry, Statement.RETURN_GENERATED_KEYS);
		
		statement.setString(1, "egorl");
		statement.setString(2, "1111");
		
		try {
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
