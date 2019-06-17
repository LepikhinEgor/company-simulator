package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionHelper {
	
	private final static String URL = "jdbc:mysql://localhost:3306/company_simulator"+
            "?verifyServerCertificate=false"+
            "&useSSL=false"+
            "&requireSSL=false"+
            "&useLegacyDatetimeCode=false"+
            "&amp"+
            "&serverTimezone=UTC";
	private final static String username = "egor";
	private final static String password = "1111";
	
	public static Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(URL, "egor", "1111");
		
		if (connection == null) {
			throw new SQLException("Connection has not been created");
		}
		
		return connection;
	}
}
