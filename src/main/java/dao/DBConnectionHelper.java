package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionHelper {
	
	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection connection = ConnectionPool.getInstance().getConnection();
		
		if (connection == null) {
			throw new SQLException("Connection has not been created");
		}
		
		return connection;
	}
}
