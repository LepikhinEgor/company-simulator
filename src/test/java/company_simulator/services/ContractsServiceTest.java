package company_simulator.services;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import dao.ContractDao;
import services.CompanyService;
import services.ContractService;
import services.utils.EntitiesConventer;

public class ContractsServiceTest {
	
	private ContractService contractService;

	private EntitiesConventer entitiesConventerMock;
	
	private CompanyService companyServiceMock;
	private ContractDao contractDaoMock;

	@Before
	public void init() {
		contractDaoMock = mock(ContractDao.class);
		companyServiceMock = mock(CompanyService.class);
		entitiesConventerMock = mock(EntitiesConventer.class);
		
		contractService = new ContractService();
	}
	
	private void injectDependensies() {
		contractService.setCompanyService(companyServiceMock);
		contractService.setContractDao(contractDaoMock);
		contractService.setEntitiesConventer(entitiesConventerMock);
	}
	
	@Test
	public void createContractSuccess() {
		
	}
}
