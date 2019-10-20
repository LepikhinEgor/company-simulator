package services;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aspects.annotations.Loggable;
import controller.input.CreateContractData;
import dao.ContractDao;
import entities.Company;
import entities.Contract;
import exceptions.DatabaseAccessException;
import exceptions.employees.DoubleEmployeeIdException;
import services.utils.EntitiesConventer;

@Service
public class ContractService {
	
	private static final Logger logger = LoggerFactory.getLogger(ContractService.class);
	
	CompanyService companyService;
	EntitiesConventer entitiesConventer;
	
	ContractDao contractDao;
	
	private final int PAGE_LIMIT = 10; 
	
	@Autowired
	public void setContractDao(ContractDao contractDao) {
		this.contractDao = contractDao;
	}
	
	@Autowired
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@Autowired
	public void setEntitiesConventer(EntitiesConventer conventer) {
		this.entitiesConventer = conventer;
	}
	
	@Loggable
	public Contract createContract(CreateContractData contractData, String userLogin) throws DatabaseAccessException {
		Contract newContract = entitiesConventer.trasformToContract(contractData);
		
		Company userCompany = companyService.getUserCompany(userLogin);
		
		try {
			contractDao.recordContract(newContract, userCompany.getId());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DatabaseAccessException(e.getMessage());
		}
		
		return newContract;
	}
	
	@Loggable
	public List<Contract> getUserActiveContracts(int sortOrder, int pageNum, String login) throws DatabaseAccessException {
		
		Company userCompany = companyService.getUserCompany(login);
		
		List<Contract> contracts = null;
		
		try {
			contracts = contractDao.getContractsList(sortOrder, pageNum, PAGE_LIMIT, userCompany.getId());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DatabaseAccessException(e.getMessage());
		}
		
		return contracts;
	}
	
	@Loggable
	public void reassignEmployees(long[] newHiredEmployees, long[] newFreeEmployeees, long contractId) throws DoubleEmployeeIdException, DatabaseAccessException {
		if (newHiredEmployees != null && newFreeEmployeees != null) {
			if (containsSameId(newHiredEmployees, newFreeEmployeees)) {
				throw new DoubleEmployeeIdException("Employee can't be free and hired at the same time");
			}
			if (containsDoubleId(newHiredEmployees))
				throw new DoubleEmployeeIdException("Hired employees can't contains double ids");
			if (containsDoubleId(newFreeEmployeees))
				throw new DoubleEmployeeIdException("Hired employees can't contains double ids");
		}
		
		try {
			Contract contract = contractDao.getContractById(contractId);
			
			contract.setProgress(calculateContractProgress(contract));
			
			contractDao.reassignEmployees(newHiredEmployees, newFreeEmployeees, contract);
		} catch (SQLException e) {
			throw new DatabaseAccessException("Error, when record hired employees to db");
		}
	}
	
	private boolean containsSameId(long[] hiredEmployeesId, long[] freeEmployeesId) {
		
		for (int i = 0; i < hiredEmployeesId.length; i++) {
			for (int j = 0; j < freeEmployeesId.length ;j++) {
				if (hiredEmployeesId[i] == freeEmployeesId[j])
					return true;
			}
		}
		
		return false;
	}
	
	private boolean containsDoubleId(long[] employeesId) {
		for (int i = 0; i < employeesId.length; i++) {
			for (int j = i + 1; j < employeesId.length ;j++) {
				if (employeesId[i] == employeesId[j])
					return true;
			}
		}
		
		return false;
	}
	
	private int calculateContractProgress(Contract contract) {
		int progress = 0;
		
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		Timestamp teamChangeTime = contract.getTeamChangedDate();
		
		int minuteDiff = (int)(currentTime.getTime() - teamChangeTime.getTime()) / (1000 * 60);
		
		progress = minuteDiff * contract.getWorkSpeed();
		
		return progress;
	}
}
