package services;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aspects.annotations.Loggable;
import controller.input.CreateContractData;
import controller.messages.entities.ContractRestData;
import dao.ContractDao;
import entities.Company;
import entities.Contract;
import exceptions.DatabaseAccessException;
import exceptions.employees.DoubleIdException;
import services.localization.LocalizationService;
import services.utils.EntitiesConventer;

@Service
public class ContractService {
	
	private static final Logger logger = LoggerFactory.getLogger(ContractService.class);
	
	CompanyService companyService;
	EntitiesConventer entitiesConventer;
	
	ContractDao contractDao;
	
	ContractRandomGenerator contractGenerator;
	
	LocalizationService localizationService;
	
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
	
	@Autowired
	public void setContractGenerator(ContractRandomGenerator contractGenerator) {
		this.contractGenerator = contractGenerator;
	}
	
	@Autowired
	public void setLocalizationService(LocalizationService localizationService) {
		this.localizationService = localizationService;
	}
	
	@Loggable
	public void selectGeneratedContracts(long[] contractsId, String login) throws DoubleIdException, DatabaseAccessException {
		if (containsDoubleId(contractsId))
			throw new DoubleIdException("not allowed double contracts id");
		
		Company company = companyService.getUserCompany(login);
		
		try {
			contractDao.selectGeneratedContracts(contractsId, company.getId());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DatabaseAccessException("Error recording new contract from generated contracts");
		}
	}
	
	@Loggable
	public List<ContractRestData> getGeneratedContracts(String login, Locale locale, TimeZone timezone) throws DatabaseAccessException {
		double companyPopularity = 0.5;
		double companyRespect = 0.5;
		
		Company company = companyService.getUserCompany(login);
		
		List<Contract> generatedContracts = contractGenerator.getGeneratedContracts(companyPopularity, companyRespect, company.getId(), timezone);
		List<Contract> localizedContracts = localizationService.localizeContracts(generatedContracts, locale);
		
		List<ContractRestData> restContracts = new ArrayList<ContractRestData>();
		for (Contract contract : localizedContracts)
			restContracts.add(entitiesConventer.transformToContractRestData(contract));
		
		return restContracts;
	}
	
	@Loggable
	public void resolveContract(long contractId) throws DatabaseAccessException {
		try {
			Contract contract = contractDao.getContractById(contractId);
			Company company = companyService.getCompanyById(contract.getCompanyId());
			
			switch (contract.getActualStatus()) {
			case Contract.COMPLETED: 
				contract.setStatus(Contract.RESOLVED_COMPLETED);
				break;
			case Contract.FAILED: 
				contract.setStatus(Contract.RESOLVED_FAILED);
				contract.setFee(contract.calculateFailedFee());
				break;
			}
			
			contractDao.resolveContract(contract, company.getCash());
		} catch (SQLException e) {
			throw new DatabaseAccessException("Exception when resolving contract");
		}
	}
	
	@Loggable
	public Contract createContract(CreateContractData contractData, String userLogin) throws DatabaseAccessException {
		Contract newContract = entitiesConventer.trasformToContract(contractData);
		
		Company userCompany = companyService.getUserCompany(userLogin);
		
		try {
			long newId = contractDao.recordContract(newContract, userCompany.getId());
			newContract.setId(newId);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DatabaseAccessException(e.getMessage());
		}
		
		return newContract;
	}
	
	@Loggable
	public List<ContractRestData> getUserActiveContracts(int sortOrder, int pageNum, String login, Locale locale) throws DatabaseAccessException {
		
		Company userCompany = companyService.getUserCompany(login);
		
		List<Contract> contracts = null;
		List<ContractRestData> contractsRest = new ArrayList<ContractRestData>();
		try {
			contracts = contractDao.getContractsList(sortOrder, pageNum, PAGE_LIMIT, userCompany.getId());
			contracts = localizationService.localizeContracts(contracts, locale);
			
			for (Contract contract: contracts) {
				contractsRest.add(entitiesConventer.transformToContractRestData(contract));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DatabaseAccessException(e.getMessage());
		}
		
		return contractsRest;
	}
	
	@Loggable
	public void reassignEmployees(long[] newHiredEmployees, long[] newFreeEmployeees, long contractId) throws DoubleIdException, DatabaseAccessException {
		if (newHiredEmployees != null && newFreeEmployeees != null) {
			if (newHiredEmployees.length == 0 && newFreeEmployeees.length == 0)
				throw new IllegalArgumentException("Senselessly method call. Free and hired employees must be changed in same time");
			if (containsSameId(newHiredEmployees, newFreeEmployeees)) {
				throw new DoubleIdException("Employee can't be free and hired at the same time");
			}
			if (containsDoubleId(newHiredEmployees))
				throw new DoubleIdException("Hired employees can't contains double ids");
			if (containsDoubleId(newFreeEmployeees))
				throw new DoubleIdException("Hired employees can't contains double ids");
		} else
			throw new IllegalArgumentException("Senselessly method call. Free and hired employees must be changed in same time");
		
		try {
			Contract contract = contractDao.getContractById(contractId);
			
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
}
