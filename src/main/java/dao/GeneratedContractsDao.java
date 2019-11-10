package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import aspects.annotations.Loggable;
import entities.Contract;

public class GeneratedContractsDao {

	private ConnectionPool connectionPool;
	
	@Autowired
	public void setConnectionPool(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}
	
	@Loggable
	public List<Contract> recordGeneratedContracts(List<Contract> contracts, long companyId) throws SQLException {
		List<Contract> generatedEmployees = null;
		
		try(Connection connection = connectionPool.getConnection()) {
			connection.setAutoCommit(false);
			
			recordGeneratedContracts(contracts, companyId, connection);
			generatedEmployees = getGeneratedContract(companyId, connection);
			
			connection.commit();
		}
		
		return generatedEmployees;
	}
	
	private void recordGeneratedContracts(List<Contract> contracts, long companyId, Connection connection) throws SQLException {
		String querry = "INSERT INTO generated_contracts VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)";
		
		try(PreparedStatement statement = connection.prepareStatement(querry)) {
			
			for (Contract contract: contracts) {
				statement.setString(1, contract.getName());
				statement.setInt(2, contract.getPerfomanceUnits());
				statement.setInt(3, contract.getFee());
				statement.setLong(4, contract.getDeadline().getTime());
				statement.setString(5, contract.getDescription());
				statement.setLong(6, companyId);
				
				statement.addBatch();
			}
			
			statement.executeBatch();
		}
	}
	
	private List<Contract> getGeneratedContract(long companyId, Connection connection) throws SQLException {
		String sql = "SELECT * FROM generated_contracts WHERE company_id = ?";
		
		List<Contract> generatedContracts = new ArrayList<Contract>();
		
		try(PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, companyId);
			
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Contract contract = new Contract();
				
				contract.setId(rs.getLong(1));
				contract.setName(rs.getString(2));
				contract.setPerfomanceUnits(rs.getInt(3));
				contract.setFee(rs.getInt(4));
				contract.setDeadline(new Timestamp(rs.getLong(5)));
				contract.setDescription(rs.getString(6));
				contract.setCompanyId(rs.getLong(7));
				
				generatedContracts.add(contract);
			}
		}
		
		return generatedContracts;
	}
	
}
