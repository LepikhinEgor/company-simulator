package company_simulator.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import controller.input.CreateContractData;
import controller.messages.entities.ContractRestData;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.ContractDao;
import entities.Company;
import entities.Contract;
import exceptions.DatabaseAccessException;
import exceptions.employees.DoubleEmployeeIdException;
import services.CompanyService;
import services.ContractService;
import services.utils.EntitiesConventer;

public class ContractsServiceTest {
	
	private ContractService contractService;

	private EntitiesConventer entitiesConventerMock;
	
	private CompanyService companyServiceMock;
	private ContractDao contractDaoMock;
	
	private String loginEmail;

	@Before
	public void init() {
		contractDaoMock = mock(ContractDao.class);
		companyServiceMock = mock(CompanyService.class);
		entitiesConventerMock = mock(EntitiesConventer.class);
		
		contractService = new ContractService();
		
		loginEmail = "admin";
	}
	
	private void injectDependensies() {
		contractService.setCompanyService(companyServiceMock);
		contractService.setContractDao(contractDaoMock);
		contractService.setEntitiesConventer(entitiesConventerMock);
	}
	
	private CreateContractData getValidContractCreateData() {
		CreateContractData contractData = new CreateContractData();
		
		contractData.setSize(100);
		contractData.setDeadline(400);
		contractData.setName("windows");
		contractData.setFee(100);
		
		return contractData;
	}
	
	private Contract getValidContract() {
		Contract contract = new Contract();
		
		contract.setName("windows");
		contract.setPerfomanceUnits(100);
		contract.setFee(100);
		contract.setDescription("No description");
		
		return contract;
	}
	
	private Company getValidCompany() {
		Company company = new Company();
		
		company.setId(1);
		company.setName("New company");
		company.setCash(100);
		
		return company;
	}
	
	@Test
	public void createContractSuccess() throws DatabaseAccessException, SQLException {
		CreateContractData contractData = getValidContractCreateData();
		Company company = getValidCompany();
		Contract expectedContract = getValidContract();
		long returnedId = 1;
		
		when(companyServiceMock.getUserCompany(loginEmail)).thenReturn(company);
		when(contractDaoMock.recordContract(expectedContract, company.getId())).thenReturn(returnedId);
		when(entitiesConventerMock.trasformToContract(contractData)).thenReturn(expectedContract);
		
		injectDependensies();
		
		Contract actualContract = contractService.createContract(contractData, loginEmail);
		expectedContract.setId(returnedId);
		
		assertTrue(expectedContract.equals(actualContract));
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void createContractThrowDBExceptionGettingCompany() throws DatabaseAccessException, SQLException {
		CreateContractData contractData = getValidContractCreateData();
		Company company = getValidCompany();
		Contract expectedContract = getValidContract();
		long returnedId = 1;
		
		when(companyServiceMock.getUserCompany(loginEmail)).thenThrow(new DatabaseAccessException(""));
		when(contractDaoMock.recordContract(expectedContract, company.getId())).thenReturn(returnedId);
		
		injectDependensies();
		
		Contract actualContract = contractService.createContract(contractData, loginEmail);
		expectedContract.setId(returnedId);
		
		assertTrue(expectedContract.equals(actualContract));
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void createContractThrowSQLExceptionFromDao() throws DatabaseAccessException, SQLException {
		CreateContractData contractData = getValidContractCreateData();
		Company company = getValidCompany();
		Contract expectedContract = getValidContract();
		long returnedId = 1;
		
		when(companyServiceMock.getUserCompany(loginEmail)).thenReturn(company);
		when(contractDaoMock.recordContract(expectedContract, company.getId())).thenThrow(new SQLException());
		when(entitiesConventerMock.trasformToContract(contractData)).thenReturn(expectedContract);
		
		injectDependensies();
		
		Contract actualContract = contractService.createContract(contractData, loginEmail);
		expectedContract.setId(returnedId);
		
		assertTrue(expectedContract.equals(actualContract));
	}
	
	@Test
	public void getUserActiveContractsSuccess() throws DatabaseAccessException, SQLException {
		int sortOrder = 1;
		int pageNum = 1;
		int pageLimit = 10;
		Company validCompany = getValidCompany();
		List<Contract> contracts = Arrays.asList(new Contract(), new Contract(), new Contract());
		
		when(companyServiceMock.getUserCompany(loginEmail)).thenReturn(validCompany);
		when(contractDaoMock.getContractsList(sortOrder, pageNum, pageLimit, validCompany.getId())).thenReturn(contracts);
		when(entitiesConventerMock.transformToContractRestData(new Contract())).thenReturn(new ContractRestData());
		
		injectDependensies();
		
		List<ContractRestData> expectedContractRestData = new ArrayList<ContractRestData>();
		for (Contract contract : contracts)
			expectedContractRestData.add(new ContractRestData());
		
		List<ContractRestData> actualContractsRestData = contractService.getUserActiveContracts(sortOrder, pageNum, loginEmail);
		
		assertTrue(expectedContractRestData.equals(actualContractsRestData));
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void getUserActiveContractsThrowDBExceptionGettingUserCompany() throws DatabaseAccessException, SQLException {
		int sortOrder = 1;
		int pageNum = 1;
		int pageLimit = 10;
		Company validCompany = getValidCompany();
		List<Contract> contracts = Arrays.asList(new Contract(), new Contract(), new Contract());
		
		when(companyServiceMock.getUserCompany(loginEmail)).thenThrow(new DatabaseAccessException(""));
		when(contractDaoMock.getContractsList(sortOrder, pageNum, pageLimit, validCompany.getId())).thenReturn(contracts);
		when(entitiesConventerMock.transformToContractRestData(new Contract())).thenReturn(new ContractRestData());
		
		injectDependensies();
		
		List<ContractRestData> expectedContractRestData = new ArrayList<ContractRestData>();
		for (Contract contract : contracts)
			expectedContractRestData.add(new ContractRestData());
		
		List<ContractRestData> actualContractsRestData = contractService.getUserActiveContracts(sortOrder, pageNum, loginEmail);
		
		assertTrue(expectedContractRestData.equals(actualContractsRestData));
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void getUserActiveContractsThrowSQLExceptionFromDao() throws DatabaseAccessException, SQLException {
		int sortOrder = 1;
		int pageNum = 1;
		int pageLimit = 10;
		Company validCompany = getValidCompany();
		List<Contract> contracts = Arrays.asList(new Contract(), new Contract(), new Contract());
		
		when(companyServiceMock.getUserCompany(loginEmail)).thenReturn(validCompany);
		when(contractDaoMock.getContractsList(sortOrder, pageNum, pageLimit, validCompany.getId())).thenThrow(new SQLException());
		when(entitiesConventerMock.transformToContractRestData(new Contract())).thenReturn(new ContractRestData());
		
		injectDependensies();
		
		List<ContractRestData> expectedContractRestData = new ArrayList<ContractRestData>();
		for (Contract contract : contracts)
			expectedContractRestData.add(new ContractRestData());
		
		List<ContractRestData> actualContractsRestData = contractService.getUserActiveContracts(sortOrder, pageNum, loginEmail);
		
		assertTrue(expectedContractRestData.equals(actualContractsRestData));
	}
	
	@Test(expected = RuntimeException.class)
	public void reassignEmployeesSuccessCallReasignDaoMethod() throws SQLException, DoubleEmployeeIdException, DatabaseAccessException {
		long[] hiredEmployees = {1,2,3,4,5}; 
		long[] freeEmployees = {6,7,8,9};
		long contractId = 1;
		
		Contract validContract = getValidContract();
		
		when(contractDaoMock.getContractById(contractId)).thenReturn(validContract);
		doThrow(new RuntimeException()).when(contractDaoMock).reassignEmployees(hiredEmployees, freeEmployees, validContract);
		
		injectDependensies();
		
		contractService.reassignEmployees(hiredEmployees, freeEmployees, contractId);
	}
	
	@Test
	public void reassignEmployeesSuccessGetArgsForReassignDaoMethod() throws SQLException, DoubleEmployeeIdException, DatabaseAccessException {
		long[] hiredEmployees = {1,2,3,4,5}; 
		long[] freeEmployees = {6,7,8,9};
		long contractId = 1;
		
		Contract validContract = getValidContract();
		
		when(contractDaoMock.getContractById(contractId)).thenReturn(validContract);
		doAnswer(new Answer<Object>() {

	        @Override
	        public Object answer(final InvocationOnMock invocation) throws Throwable {
	            long[] hiredEmployeesArg = (long[])(invocation.getArguments())[0];
	            long[] freeEmployeesArg = (long[])(invocation.getArguments())[1];
	            Contract contractArg = (Contract)(invocation.getArguments())[2];
	            
	            assertTrue(hiredEmployees.equals(hiredEmployeesArg));
	            assertTrue(freeEmployees.equals(freeEmployeesArg));
	            assertTrue(validContract.equals(contractArg));
	            
	            return null;
	        }
	}).when(contractDaoMock).reassignEmployees(hiredEmployees, freeEmployees, validContract);
		
		injectDependensies();
		
		contractService.reassignEmployees(hiredEmployees, freeEmployees, contractId);
	}
	
	@Test(expected = DoubleEmployeeIdException.class)
	public void reassignEmployeesThrowDoubleIdExceptionFromHiredEmployeees() throws SQLException, DoubleEmployeeIdException, DatabaseAccessException {
		long[] hiredEmployees = {1,2,2}; 
		long[] freeEmployees = {6,7,8,9};
		long contractId = 1;
		
		Contract validContract = getValidContract();
		
		when(contractDaoMock.getContractById(contractId)).thenReturn(validContract);
		doNothing().when(contractDaoMock).reassignEmployees(hiredEmployees, freeEmployees, validContract);
		
		injectDependensies();
		
		contractService.reassignEmployees(hiredEmployees, freeEmployees, contractId);
	}
	
	@Test(expected = DoubleEmployeeIdException.class)
	public void reassignEmployeesThrowDoubleIdExceptionFromFreeEmployees() throws SQLException, DoubleEmployeeIdException, DatabaseAccessException {
		long[] hiredEmployees = {1,2,3}; 
		long[] freeEmployees = {6,6, 7};
		long contractId = 1;
		
		Contract validContract = getValidContract();
		
		when(contractDaoMock.getContractById(contractId)).thenReturn(validContract);
		doNothing().when(contractDaoMock).reassignEmployees(hiredEmployees, freeEmployees, validContract);
		
		injectDependensies();
		
		contractService.reassignEmployees(hiredEmployees, freeEmployees, contractId);
	}
	
	@Test(expected = DoubleEmployeeIdException.class)
	public void reassignEmployeesThrowDoubleIdExceptionBySameFreeAndHiredEmployeesId() throws SQLException, DoubleEmployeeIdException, DatabaseAccessException {
		long[] hiredEmployees = {1,2,3}; 
		long[] freeEmployees = {1, 6, 7};
		long contractId = 1;
		
		Contract validContract = getValidContract();
		
		when(contractDaoMock.getContractById(contractId)).thenReturn(validContract);
		doNothing().when(contractDaoMock).reassignEmployees(hiredEmployees, freeEmployees, validContract);
		
		injectDependensies();
		
		contractService.reassignEmployees(hiredEmployees, freeEmployees, contractId);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void reassignEmployeesThrowIllegalArgsExceptionByNullHiredEmployees() throws SQLException, DoubleEmployeeIdException, DatabaseAccessException {
		long[] hiredEmployees = null; 
		long[] freeEmployees = {1, 6, 7};
		long contractId = 1;
		
		Contract validContract = getValidContract();
		
		when(contractDaoMock.getContractById(contractId)).thenReturn(validContract);
		doNothing().when(contractDaoMock).reassignEmployees(hiredEmployees, freeEmployees, validContract);
		
		injectDependensies();
		
		contractService.reassignEmployees(hiredEmployees, freeEmployees, contractId);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void reassignEmployeesThrowIllegalArgsExceptionByNullFreeEmployees() throws SQLException, DoubleEmployeeIdException, DatabaseAccessException {
		long[] hiredEmployees = {1,2,3}; 
		long[] freeEmployees = null;
		long contractId = 1;
		
		Contract validContract = getValidContract();
		
		when(contractDaoMock.getContractById(contractId)).thenReturn(validContract);
		doNothing().when(contractDaoMock).reassignEmployees(hiredEmployees, freeEmployees, validContract);
		
		injectDependensies();
		
		contractService.reassignEmployees(hiredEmployees, freeEmployees, contractId);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void reassignEmployeesThrowIllegalArgsExceptionByEmptyFreeAndHiredEmployees() throws SQLException, DoubleEmployeeIdException, DatabaseAccessException {
		long[] hiredEmployees = {}; 
		long[] freeEmployees = {};
		long contractId = 1;
		
		Contract validContract = getValidContract();
		
		when(contractDaoMock.getContractById(contractId)).thenReturn(validContract);
		doNothing().when(contractDaoMock).reassignEmployees(hiredEmployees, freeEmployees, validContract);
		
		injectDependensies();
		
		contractService.reassignEmployees(hiredEmployees, freeEmployees, contractId);
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void reassignEmployeesThrowSQLExceptionGettingContractById() throws SQLException, DoubleEmployeeIdException, DatabaseAccessException {
		long[] hiredEmployees = {1,2,3}; 
		long[] freeEmployees = {5,6,7};
		long contractId = 1;
		
		Contract validContract = getValidContract();
		
		when(contractDaoMock.getContractById(contractId)).thenThrow(new SQLException());
		doNothing().when(contractDaoMock).reassignEmployees(hiredEmployees, freeEmployees, validContract);
		
		injectDependensies();
		
		contractService.reassignEmployees(hiredEmployees, freeEmployees, contractId);
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void reassignEmployeesThrowSQLExceptionReassigningEmployees() throws SQLException, DoubleEmployeeIdException, DatabaseAccessException {
		long[] hiredEmployees = {1,2,3}; 
		long[] freeEmployees = {5,6,7};
		long contractId = 1;
		
		Contract validContract = getValidContract();
		
		when(contractDaoMock.getContractById(contractId)).thenReturn(validContract);
		doThrow(new SQLException()).when(contractDaoMock).reassignEmployees(hiredEmployees, freeEmployees, validContract);
		
		injectDependensies();
		
		contractService.reassignEmployees(hiredEmployees, freeEmployees, contractId);
	}
}
