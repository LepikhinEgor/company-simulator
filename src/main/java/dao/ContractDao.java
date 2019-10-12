package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import aspects.annotations.Loggable;
import entities.Contract;

public class ContractDao {
	
	@Autowired
	private ConnectionPool connectionPool;
	
	public long recordContract(Contract contract, long companyId) throws SQLException {
		Connection connection = connectionPool.getConnection();
		
		String recordContractQuerry = "INSERT INTO contracts (contract_id, name, performance_units, fee, deadline, progress, description, company_id) VALUES ("
				+ "NULL, ?, ?, ?, ?, ?, ?, ?);";
		
		PreparedStatement recordContractStatement = null;
		try {
			recordContractStatement = connection.prepareStatement(recordContractQuerry, Statement.RETURN_GENERATED_KEYS);
						
			recordContractStatement.setString(1, contract.getName());
			recordContractStatement.setInt(2, contract.getPerfomanceUnits());
			recordContractStatement.setInt(3, contract.getFee());
			recordContractStatement.setLong(4, contract.getDeadline());
			recordContractStatement.setLong(5, contract.getProgress());
			recordContractStatement.setString(6, contract.getDescription());
			recordContractStatement.setLong(7, companyId);
			
			int contractsInsert = recordContractStatement.executeUpdate();
			
			if (contractsInsert == 1)
				return getGeneratedId(recordContractStatement);
			else
				throw new SQLException("Incorrect number of created companies. Required 1");
		} finally {
			if (recordContractStatement != null)
				recordContractStatement.close();
			if (connection != null)
				connection.close();
		}
	}
	
	/**
	 * 
	 * @param pageNum 
	 * @param pageLimit limit of contracts in one page
	 * @param companyId
	 * @return List of company contracts
	 * @throws SQLException
	 */
	@Loggable
	public List<Contract> getContractsList(int pageNum,int pageLimit, long companyId) throws SQLException {
		
		List<Contract> contracts = new ArrayList<Contract>();
		
		String querry = "SELECT * FROM contracts WHERE company_id = ?" +
				" LIMIT " + pageNum*pageLimit + ", " + pageLimit;
		
		try(Connection connection = connectionPool.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(querry);
			statement.setLong(1, companyId);
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				Contract contract = new Contract();
				
				contract.setId(resultSet.getInt(1));
				contract.setName(resultSet.getString(2));
				contract.setPerfomanceUnits(resultSet.getInt(3));
				contract.setFee(resultSet.getInt(4));
				contract.setDeadline(resultSet.getLong(5));
				contract.setDescription(resultSet.getString(7));
				
				contracts.add(contract);
			}
		}
		
		return contracts;
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
