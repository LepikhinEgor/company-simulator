package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import domain.Contract;

public class ContractDao {
	public long recordContract(Contract contract, long companyId) throws SQLException {
		Connection connection = DBConnectionHelper.getConnection();
		
		String recordContractQuerry = "INSERT INTO contracts (contract_id, name, performance_units, fee, deadline, progress, description, company_id) VALUES ("
				+ "NULL, ?, ?, ?, ?, ?, ?, ?);";
		
		PreparedStatement recordContractStatement = null;
		try {
			recordContractStatement = connection.prepareStatement(recordContractQuerry, Statement.RETURN_GENERATED_KEYS);
						
			recordContractStatement.setString(1, contract.getName());
			recordContractStatement.setInt(2, contract.getPerfomanceUnits());
			recordContractStatement.setInt(3, contract.getFee());
			recordContractStatement.setInt(4, contract.getDeadline());
			recordContractStatement.setInt(5, contract.getProgress());
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
