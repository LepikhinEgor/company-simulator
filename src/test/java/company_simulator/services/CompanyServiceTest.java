package company_simulator.services;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.sql.SQLException;

import dao.CompanyDao;
import entities.Company;
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
		
		return company;
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
	
	@Test(expected = DatabaseAccessException.class)
	public void getUserCompanyByLoginThrowDatabaseExceptio() throws DatabaseAccessException, SQLException {
		Company validCompany = getValidCompany();
		
		when(companyDaoMock.getUserCompany(loginEmail)).thenReturn(null);
		
		injectDependensies();
		
		Company actualCompany = companyService.getUserCompany(loginEmail);
		
		assertTrue(actualCompany.equals(validCompany));
	}
	
}
