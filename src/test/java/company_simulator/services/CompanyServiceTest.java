package company_simulator.services;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import controller.messages.CompanyInfoMessage;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.sql.SQLException;

import javax.management.RuntimeErrorException;

import dao.CompanyDao;
import entities.Company;
import entities.User;
import exceptions.DatabaseAccessException;
import services.CompanyService;
import services.UserService;

public class CompanyServiceTest {
	
	private CompanyService companyService;
	
	private UserService userServiceMock;
	private CompanyDao companyDaoMock;
	
	private String loginEmail = "admin";

	@Before
	public void init() {
		this.companyService = new CompanyService();
		
		this.userServiceMock = mock(UserService.class);
		this.companyDaoMock = mock(CompanyDao.class);
	}
	
	private void injectDependensies() {
		companyService.setUserService(userServiceMock);
		companyService.setCompanyDao(companyDaoMock);
	}
	
	private Company getValidCompany() {
		Company company = new Company();
		
		company.setId(1);
		company.setName("New company");
		company.setCash(100);
		
		return company;
	}
	
	private User getValidUser() {
		User validUser = new User();
		
		validUser.setId(1);
		validUser.setLogin("Ivan");
		
		return validUser;
	}
	
	private CompanyInfoMessage getValidCompanyInfo() {
		CompanyInfoMessage companyInfo = new CompanyInfoMessage();
		
		companyInfo.setId(1);
		companyInfo.setCash(100);
		companyInfo.setContractsCompleted(3);
		companyInfo.setContractsExecuting(2);
		companyInfo.setContractsFailed(5);
		companyInfo.setDefaultCash(1000000);
		companyInfo.setEmployeesCount(0);
		companyInfo.setName("New company");
		companyInfo.setOwnerName("Ivan");
		
		return companyInfo;
	}
	
	@Test
	public void getUserCompanyByLoginSuccess() throws DatabaseAccessException, SQLException {
		Company validCompany = getValidCompany();
		
		when(companyDaoMock.getUserCompany(loginEmail)).thenReturn(validCompany);
		
		injectDependensies();
		
		Company actualCompany = companyService.getUserCompany(loginEmail);
		
		assertTrue(actualCompany.equals(validCompany));
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void getUserCompanyByLoginThrowDatabaseException() throws DatabaseAccessException, SQLException {
		Company validCompany = getValidCompany();
		
		when(companyDaoMock.getUserCompany(loginEmail)).thenThrow(new SQLException());
		
		injectDependensies();
		
		Company actualCompany = companyService.getUserCompany(loginEmail);
		
		assertTrue(actualCompany.equals(validCompany));
	}
	
	@Test
	public void getUserCompanyByLoginSuccessReturnNewCompany() throws DatabaseAccessException, SQLException {
		User validUser = getValidUser();
		
		Company expectedNewCompany = new Company();
		expectedNewCompany.setOwnerId(1);
		
		when(userServiceMock.getUserDataByLoginEmail(loginEmail)).thenReturn(validUser);
		when(companyDaoMock.getUserCompany(loginEmail)).thenReturn(null);
		when(companyDaoMock.recordCompany(expectedNewCompany)).thenReturn(1L);
		
		injectDependensies();
		
		Company actualNewCompany = companyService.getUserCompany(loginEmail);
		
		expectedNewCompany.setId(1);
			
		assertTrue(actualNewCompany.equals(expectedNewCompany));
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void getUserCompanyByLoginThrowDBExceptionCreatingNewCompany() throws DatabaseAccessException, SQLException {
		User validUser = getValidUser();
		
		Company expectedNewCompany = new Company();
		expectedNewCompany.setOwnerId(1);
		
		when(userServiceMock.getUserDataByLoginEmail(loginEmail)).thenReturn(validUser);
		when(companyDaoMock.getUserCompany(loginEmail)).thenReturn(null);
		when(companyDaoMock.recordCompany(expectedNewCompany)).thenThrow(new SQLException());
		
		injectDependensies();
		
		Company actualNewCompany = companyService.getUserCompany(loginEmail);
		
		expectedNewCompany.setId(1);
			
		assertTrue(actualNewCompany.equals(expectedNewCompany));
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void getUserCompanyByLoginThrowDBExceptionGettingUser() throws DatabaseAccessException, SQLException {
		Company expectedNewCompany = new Company();
		expectedNewCompany.setOwnerId(1);
		
		when(userServiceMock.getUserDataByLoginEmail(loginEmail)).thenThrow(new DatabaseAccessException());
		when(companyDaoMock.getUserCompany(loginEmail)).thenReturn(null);
		when(companyDaoMock.recordCompany(expectedNewCompany)).thenReturn(1L);
		
		injectDependensies();
		
		Company actualNewCompany = companyService.getUserCompany(loginEmail);
		
		expectedNewCompany.setId(1);
			
		assertTrue(actualNewCompany.equals(expectedNewCompany));
	}
	
	@Test
	public void getUserCompanyByIdSuccess() throws DatabaseAccessException, SQLException {
		Company validCompany = getValidCompany();
		
		when(companyDaoMock.getUserCompany(1)).thenReturn(validCompany);
		
		injectDependensies();
		
		Company actualCompany = companyService.getUserCompany(1);
		
		assertTrue(actualCompany.equals(validCompany));
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void getUserCompanyByIdThrowDatabaseException() throws DatabaseAccessException, SQLException {
		Company validCompany = getValidCompany();
		
		when(companyDaoMock.getUserCompany(1)).thenThrow(new SQLException());
		
		injectDependensies();
		
		Company actualCompany = companyService.getUserCompany(1);
		
		assertTrue(actualCompany.equals(validCompany));
	}
	
	@Test
	public void getUserCompanyByIdSuccessReturnNewCompany() throws DatabaseAccessException, SQLException {
		User validUser = getValidUser();
		long userId = 1;
		
		Company expectedNewCompany = new Company();
		expectedNewCompany.setOwnerId(1);
		
		when(companyDaoMock.getUserCompany(userId)).thenReturn(null);
		when(companyDaoMock.recordCompany(expectedNewCompany)).thenReturn(userId);
		
		injectDependensies();
		
		Company actualNewCompany = companyService.getUserCompany(userId);
		
		expectedNewCompany.setId(1);
			
		assertTrue(actualNewCompany.equals(expectedNewCompany));
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void getUserCompanyByIdThrowDBExceptionCreatingNewCompany() throws DatabaseAccessException, SQLException {
		long userId = 1;
		
		Company expectedNewCompany = new Company();
		expectedNewCompany.setOwnerId(userId);
		
		when(companyDaoMock.getUserCompany(userId)).thenReturn(null);
		when(companyDaoMock.recordCompany(expectedNewCompany)).thenThrow(new SQLException());
		
		injectDependensies();
		
		Company actualNewCompany = companyService.getUserCompany(userId);
		
		expectedNewCompany.setId(1);
			
		assertTrue(actualNewCompany.equals(expectedNewCompany));
	}
	
	@Test
	public void getCompanyInfoSuccess() throws DatabaseAccessException {
		User validUser = getValidUser();
		Company validCompany = getValidCompany();
		CompanyInfoMessage expectedCompanyInfo = getValidCompanyInfo();
		
		CompanyService companyServiceSpy = spy(CompanyService.class);
		when(userServiceMock.getUserDataByLoginEmail(loginEmail)).thenReturn(validUser);
		doReturn(validCompany).when(companyServiceSpy).getUserCompany(validUser.getId());
		
		companyServiceSpy.setUserService(userServiceMock);
		
		CompanyInfoMessage actualCompanyInfo = companyServiceSpy.getCompanyInfo(loginEmail);
		System.out.println(expectedCompanyInfo);
		System.out.println(actualCompanyInfo);
		
		assertTrue(expectedCompanyInfo.equals(actualCompanyInfo));
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void getCompanyInfoThrowDBExceptionGettingUser() throws DatabaseAccessException {
		User validUser = getValidUser();
		Company validCompany = getValidCompany();
		CompanyInfoMessage expectedCompanyInfo = getValidCompanyInfo();
		
		CompanyService companyServiceSpy = spy(CompanyService.class);
		when(userServiceMock.getUserDataByLoginEmail(loginEmail)).thenThrow(new DatabaseAccessException());
		doReturn(validCompany).when(companyServiceSpy).getUserCompany(validUser.getId());
		
		companyServiceSpy.setUserService(userServiceMock);
		
		CompanyInfoMessage actualCompanyInfo = companyServiceSpy.getCompanyInfo(loginEmail);
		System.out.println(expectedCompanyInfo);
		System.out.println(actualCompanyInfo);
		
		assertTrue(expectedCompanyInfo.equals(actualCompanyInfo));
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void getCompanyInfoThrowDBExceptionGettingUserCompany() throws DatabaseAccessException {
		User validUser = getValidUser();
		Company validCompany = getValidCompany();
		CompanyInfoMessage expectedCompanyInfo = getValidCompanyInfo();
		
		CompanyService companyServiceSpy = spy(CompanyService.class);
		when(userServiceMock.getUserDataByLoginEmail(loginEmail)).thenReturn(validUser);
		doThrow(new DatabaseAccessException()).when(companyServiceSpy).getUserCompany(validUser.getId());
		
		companyServiceSpy.setUserService(userServiceMock);
		
		CompanyInfoMessage actualCompanyInfo = companyServiceSpy.getCompanyInfo(loginEmail);
		System.out.println(expectedCompanyInfo);
		System.out.println(actualCompanyInfo);
		
		assertTrue(expectedCompanyInfo.equals(actualCompanyInfo));
	}
}
