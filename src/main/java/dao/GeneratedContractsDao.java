package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

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
	public List<Contract> getGeneratedContracts(long companyId) throws SQLException {
		List<Contract> generatedContracts;
		
		try(Connection connection = connectionPool.getConnection()) {
			generatedContracts = getGeneratedContract(companyId, connection);
		}
		
		return generatedContracts;
	}

	@Loggable
	public List<Contract> recordGeneratedContracts(List<Contract> contracts, long companyId, long generationTiming)
			throws SQLException {
		List<Contract> generatedEmployees = null;

		try (Connection connection = connectionPool.getConnection()) {
			connection.setAutoCommit(false);

			deleteOldGeneratedContracts(companyId, connection);
			recordGeneratedContracts(contracts, companyId, connection);
			recordContractsGenerationTiming(generationTiming, connection);
			generatedEmployees = getGeneratedContract(companyId, connection);

			connection.commit();
		}

		return generatedEmployees;
	}

	@Loggable
	public long getContractGenerationTiming(long companyId) throws SQLException {
		String sql = "SELECT contracts_generate_timing FROM  timings WHERE company_id = ?";
		
		long timing = 0;
		
		try (Connection connection = connectionPool.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, companyId);
			
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				timing = rs.getLong(1);
			}
		}
		
		return timing;
	}
	
	private void deleteOldGeneratedContracts(long companyId, Connection connection) throws SQLException {
		String sql = "DELETE FROM generated_contracts WHERE company_id = ?";
		
		try(PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, companyId);
			
			statement.execute();
		}
	}

	private void recordContractsGenerationTiming(long timing, Connection connection) throws SQLException {
		String sql = "UPDATE timings SET contracts_generate_timing = ?";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, timing);

			statement.executeUpdate();
		}
	}

	private void recordGeneratedContracts(List<Contract> contracts, long companyId, Connection connection)
			throws SQLException {
		String querry = "INSERT INTO generated_contracts VALUES (NULL, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement statement = connection.prepareStatement(querry)) {

			for (Contract contract : contracts) {
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

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, companyId);

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
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
