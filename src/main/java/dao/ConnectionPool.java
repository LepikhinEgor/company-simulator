package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.PooledConnection;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

public class ConnectionPool {
	private final static Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
	
	private final static String URL = "jdbc:mysql://localhost:3306/company_simulator"+
            "?verifyServerCertificate=false"+
            "&useSSL=false"+
            "&requireSSL=false"+
            "&useLegacyDatetimeCode=false"+
            "&amp"+
            "&serverTimezone=UTC";
	
	private BasicDataSource dataSource;
	
	public ConnectionPool() {
			dataSource = new BasicDataSource();
			dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
			dataSource.setUrl(URL);
			dataSource.setUsername("egor");
			dataSource.setInitialSize(4);
			dataSource.setPassword("1111");
			dataSource.setMinIdle(5);
			dataSource.setMaxIdle(10);
			dataSource.setMaxOpenPreparedStatements(100);
	}
	
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
}
