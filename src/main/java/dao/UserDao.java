package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
	
	public int createNewUser(String nickname, String password) throws SQLException {
		Connection connection = DriverManager.getConnection(URL, "egor", "1111");
		
		String addUserQuerry = "INSERT INTO users (user_id, name, password, company_id) VALUES ("
				+ "NULL, ?, ?, NULL);";
		
		PreparedStatement statement =  connection.prepareStatement(addUserQuerry);
		
		statement.setString(1, "egorl");
		statement.setString(2, "1111");
		
		return statement.executeUpdate();
		
	}
}
