package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.PooledConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

public class ConnectionPool {
	private final static Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
	
	private static ConnectionPool instance;
	private static MysqlConnectionPoolDataSource dataSource;
	
	public static ConnectionPool getInstance() {
		if (instance == null)
			instance = new ConnectionPool();
		return instance;
	}
	
	private ConnectionPool() {
		try {
			Context ctx = new InitialContext();
			//dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/testPool");
			dataSource = new MysqlConnectionPoolDataSource();
			dataSource.setUser("egor");
			dataSource.setPassword("1111");
			dataSource.setDatabaseName("company_simulator");
			dataSource.setServerName("localhost");
			dataSource.setPortNumber(3306);
			dataSource.setServerTimezone("UTC");
		} catch (NamingException e) {
			logger.error("Error, when creating connection pool", e);
			throw new RuntimeException("Error, when creating connection pool", e);
		} catch (SQLException e) {
			logger.error("Error, when setting timezone", e);
			throw new RuntimeException("Error, when setting timezone", e);
		}
	}
	
	public PooledConnection getConnection() throws SQLException {
		return dataSource.getPooledConnection();
	}
	
}
