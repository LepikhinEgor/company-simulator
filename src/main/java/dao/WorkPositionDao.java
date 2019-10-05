package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;

import entities.Contract;

public class WorkPositionDao {
	
	@Autowired
	private ConnectionPool connectionPool;
	
	public long recordWorkPosition(long employeeId, long contractId) throws SQLException {
		Connection connection = connectionPool.getConnection();
		
		String recordWorkPositionQuerry = "INSERT INTO work_positions (position_id, employee_id, contract_id) VALUES ("
				+ "NULL, ?, ?);";
		
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(recordWorkPositionQuerry, Statement.RETURN_GENERATED_KEYS);
						
			statement.setLong(1, employeeId);
			statement.setLong(2, contractId);
			
			int contractsInsert = statement.executeUpdate();
			
			if (contractsInsert == 1)
				return getGeneratedId(statement);
			else
				throw new SQLException("Incorrect number of recorded rows. Required 1");
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
                throw new SQLException("Creating company failed, no ID obtained.");
            }
        }
	}
}
