package services;

import java.sql.SQLException;
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
}
