package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.swing.SortOrder;

import org.springframework.beans.factory.annotation.Autowired;

import aspects.annotations.Loggable;
import entities.Contract;

public class ContractDao {
	
	@Autowired
	private ConnectionPool connectionPool;
	
	@Loggable
	public Contract getContractById(long contractId) throws SQLException {
		String querry = "SELECT * FROM contracts WHERE contract_id = ?";
		
		try(Connection connection = connectionPool.getConnection()) {
			
			PreparedStatement statement = connection.prepareStatement(querry);
			statement.setLong(1, contractId);
			
			ResultSet resultSet = statement.executeQuery();
			
			Contract contract = null;
			if (resultSet.next()) {
				contract = new Contract();
				contract.setId(resultSet.getInt(1));
				contract.setName(resultSet.getString(2));
				contract.setPerfomanceUnits(resultSet.getInt(3));
				contract.setFee(resultSet.getInt(4));
				contract.setTeamChangedDate(resultSet.getTimestamp(5));
				contract.setDeadline(resultSet.getLong(6));
				contract.setDescription(resultSet.getString(7));
			}
			
			int contractSpeed = getContractPerfomance(contractId, connection);
			contract.setWorkSpeed(contractSpeed);
			
			return contract;
		}
	}
	
	@Loggable
	public long recordContract(Contract contract, long companyId) throws SQLException {
		Connection connection = connectionPool.getConnection();
		
		String recordContractQuerry = "INSERT INTO contracts (contract_id, name, performance_units, fee, start_date ,deadline, description, company_id) VALUES ("
				+ "NULL, ?, ?, ?, ?, ?, ?, ?);";
		
		PreparedStatement recordContractStatement = null;
		try {
			recordContractStatement = connection.prepareStatement(recordContractQuerry, Statement.RETURN_GENERATED_KEYS);
						
			recordContractStatement.setString(1, contract.getName());
			recordContractStatement.setInt(2, contract.getPerfomanceUnits());
			recordContractStatement.setInt(3, contract.getFee());
			recordContractStatement.setTimestamp(4, contract.getTeamChangedDate());
			recordContractStatement.setLong(5, contract.getDeadline());
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
	public List<Contract> getContractsList(int sortOrder, int pageNum, int pageLimit, long companyId) throws SQLException {
		
		List<Contract> contracts = new ArrayList<Contract>();
		
		String order = getSortOrderStr(sortOrder);
		
		String querry = "SELECT * FROM contracts WHERE company_id = ? " + order +
				" LIMIT " + pageNum*pageLimit + ", " + pageLimit;
		
		try(Connection connection = connectionPool.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(querry);
			statement.setLong(1, companyId);
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				Contract contract = new Contract();
				
				Calendar calendar = Calendar.getInstance();
				
				contract.setId(resultSet.getInt(1));
				contract.setName(resultSet.getString(2));
				contract.setPerfomanceUnits(resultSet.getInt(3));
				contract.setFee(resultSet.getInt(4));
				contract.setTeamChangedDate(resultSet.getTimestamp(5,calendar));
				contract.setDeadline(resultSet.getLong(6));
				contract.setLastProgress(resultSet.getInt(7));
				contract.setDescription(resultSet.getString(8));
				
				contracts.add(contract);
			}
			contracts = getContractListWithPerfomance(contracts, connection);
		}		
		
		return contracts;
	}
	
	private List<Contract> getContractListWithPerfomance(List<Contract> contractsList, Connection connection) throws SQLException {
		List<Contract> contracts = new ArrayList<Contract>(contractsList);
		
		String querry = "SELECT e.performance FROM work_positions wp INNER JOIN employees e ON e.employee_id = wp.employee_id "
				+ "WHERE contract_id = ?";
		PreparedStatement statement = connection.prepareStatement(querry);
		
		for (Contract contract : contractsList) {
			statement.setLong(1, contract.getId());
			
			ResultSet rs = statement.executeQuery();
			int perfomance = 0;
			while(rs.next()) {
				perfomance += rs.getInt(1);
			}
			contract.setWorkSpeed(perfomance);
		}
		
		return contracts;
	}
	
	private int getContractPerfomance(long contractId, Connection connection) throws SQLException {
		
		String querry = "SELECT e.performance FROM work_positions wp INNER JOIN employees e ON e.employee_id = wp.employee_id "
				+ "WHERE contract_id = ?";
		PreparedStatement statement = connection.prepareStatement(querry);
		
		statement.setLong(1, contractId);
		
		ResultSet rs = statement.executeQuery();
		
		int perfomance = 0;
		while(rs.next()) {
			perfomance += rs.getInt(1);
		}
		
		return perfomance;
	}
	
	private String getSortOrderStr(int sortOrderNum) {
		if (sortOrderNum < 0 || sortOrderNum > 14)
			throw new IllegalArgumentException("Incorrect sort order number");
		
		String sortOrder = "ORDER BY ";
		
		switch (sortOrderNum) {
		case 0 : sortOrder += "name";break;
		case 1 : sortOrder += "name DESC";break;
		case 2 : sortOrder += "performance_units";break;
		case 3 : sortOrder += "performance_units DESC";break;
		case 4 : sortOrder += "fee";break;
		case 5 : sortOrder += "fee DESC";break;
		case 12 : sortOrder += "deadline";break;
		case 13: sortOrder += "deadline DESC";break;
		}
		
		return sortOrder;
		
	}
	
	@Loggable
	public void reassignEmployees(long[] hiredEmployeesId, long[] freeEmployeesId, Contract contract) throws SQLException {
		try (Connection connection = connectionPool.getConnection()) {
			connection.setAutoCommit(false);
			
			recordContractProgress(contract.getId(), contract.getProgress());
			
			hireEmployeesToContract(hiredEmployeesId, contract.getId(), connection);
			freeEmployeesFromContract(freeEmployeesId, contract.getId(), connection);
			
			connection.commit();
		}
	}
	
	private void recordContractProgress(long contractId, int progress) throws SQLException {
		String querry = "UPDATE contracts SET last_progress = ?, team_changed_date = NOW() WHERE contract_id = ?";
		
		try(Connection connection = connectionPool.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(querry);
			
			statement.setInt(1, progress);
			statement.setLong(2, contractId);
			
			int contractChanged = statement.executeUpdate();
			if (contractChanged == 0) {
				throw new RuntimeException("Contract not changed");
			}
		}
	}
	
	private void hireEmployeesToContract(long[] hiredEmployeesId, long contractId, Connection connection) throws SQLException {
		String querry = "INSERT INTO work_positions (position_id, employee_id, contract_id) "
				+ "VALUES (NULL, ?, ?)";
		
		PreparedStatement statement = connection.prepareStatement(querry);
		
		for (int i = 0; i < hiredEmployeesId.length; i++) {
			statement.setLong(1, hiredEmployeesId[i]);
			statement.setLong(2, contractId);
			statement.executeUpdate();
		}
	}
	
	private void freeEmployeesFromContract(long[] freeEmployeesId, long contractId, Connection connection) throws SQLException {
		String querry = "DELETE FROM work_positions WHERE employee_id = ? AND contract_id = ?";
		
		PreparedStatement statement = connection.prepareStatement(querry);
		
		for (int i = 0; i < freeEmployeesId.length; i++) {
			statement.setLong(1, freeEmployeesId[i]);
			statement.setLong(2, contractId);
			statement.executeUpdate();
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
