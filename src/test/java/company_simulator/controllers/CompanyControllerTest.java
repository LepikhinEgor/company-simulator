package company_simulator.controllers;

import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.http.Cookie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import company_simulator.TestsDaoBeansConfiguration;
import config.WebConfig;
import controller.messages.CompanyInfoMessage;
import entities.Company;
import entities.User;
import exceptions.DatabaseAccessException;
import services.CompanyService;
import services.UserService;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes={WebConfig.class, TestsDaoBeansConfiguration.class})
public class CompanyControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webContext;
	
	@Autowired
	@Qualifier("mockCompanyService")
	CompanyService companyServiceMock;
	
	@Autowired
	@Qualifier("mockUserService")
	UserService userServiceMock;
	
	private Cookie getLoginCookie() {
		Cookie loginCookie = new Cookie("signedUser", "admin");
		
		return loginCookie;
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
		
		validUser.setEmail("admin@mail.ru");
		validUser.setId(1);
		validUser.setLogin("admin");
		validUser.setPassword("qwerty");
		
		return validUser;
	}
	
	private CompanyInfoMessage getCompanyInfo() {
		CompanyInfoMessage companyInfo = new CompanyInfoMessage();
		
		companyInfo.setId(1);
		companyInfo.setCash(100);
		companyInfo.setContractsCompleted(3);
		companyInfo.setContractsExecuting(2);
		companyInfo.setContractsFailed(5);
		companyInfo.setDefaultCash(1000000);
		companyInfo.setEmployeesCount(0);
		companyInfo.setName("New company");
		companyInfo.setOwnerName("admin");
		
		return companyInfo;
	}
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
		reset(companyServiceMock);
		reset(userServiceMock);
	}
	
	@Test
	public void getHomePage_succesGetHomePage() throws Exception {
		Company validCompany = getValidCompany();
		User validUser  = getValidUser();
		
		when(companyServiceMock.getUserCompany(validUser.getLogin())).thenReturn(validCompany);
		when(userServiceMock.getUserDataByLoginEmail(validUser.getLogin())).thenReturn(validUser);
		
		mockMvc.perform(
				 get("/company").cookie(getLoginCookie()))
		        .andExpect(view().name("company"))
		        .andExpect(status().is(200))
		        .andExpect(model().attribute("login", validUser.getLogin()))
		        .andExpect(model().attribute("company", validCompany.getName()))
//		        .andDo(MockMvcResultHandlers.print())
		        .andReturn();
	}
	
	@Test
	public void getHomePage_ErrorGettingCompany() throws Exception {
		User validUser  = getValidUser();
		Company validCompany = getValidCompany();
		
		when(companyServiceMock.getUserCompany(validUser.getLogin())).thenReturn(validCompany);
		when(userServiceMock.getUserDataByLoginEmail(validUser.getLogin())).thenThrow(new DatabaseAccessException(""));
		
		mockMvc.perform(
				 get("/company").cookie(getLoginCookie()))
		        .andExpect(status().is3xxRedirection())
		        .andExpect(MockMvcResultMatchers.redirectedUrl("company"))
//		        .andDo(MockMvcResultHandlers.print())
		        .andReturn();
	}
	
	@Test
	public void getHomePage_ErrorGettingUser() throws Exception {
		User validUser  = getValidUser();
		
		when(companyServiceMock.getUserCompany(validUser.getLogin())).thenThrow(new DatabaseAccessException(""));
		when(userServiceMock.getUserDataByLoginEmail(validUser.getLogin())).thenReturn(validUser);
		
		mockMvc.perform(
				 get("/company").cookie(getLoginCookie()))
		        .andExpect(status().is3xxRedirection())
		        .andExpect(MockMvcResultMatchers.redirectedUrl("company"))
//		        .andDo(MockMvcResultHandlers.print())
		        .andReturn();
	}
	
	@Test
	public void getHomePage_ReturnLoginPageBecauseCookieIsNull() throws Exception {
		mockMvc.perform(
				 get("/company")
				 )
//				.andDo(MockMvcResultHandlers.print())
				.andExpect(view().name("login"))
		        .andExpect(status().is(200))
		        .andReturn();
	}
	
	@Test
	public void getCompanyPage_successRedirectWithCookie() throws Exception {
		mockMvc.perform(
				get("/").cookie(getLoginCookie())
				 )
//				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().is3xxRedirection())
		        .andExpect(MockMvcResultMatchers.redirectedUrl("company"))
		        .andReturn();
	}
	
	@Test
	public void getCompanyPage_successRedirectWithoutCookie() throws Exception {
		mockMvc.perform(
				get("/")
				 )
//				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().is3xxRedirection())
		        .andExpect(MockMvcResultMatchers.redirectedUrl("company"))
		        .andReturn();
	}
	
	@Test
	public void getCompanyInfoPage_success() throws Exception {
		mockMvc.perform(
				 get("/company/info")
				 )
//				.andDo(MockMvcResultHandlers.print())
				.andExpect(view().name("info"))
		        .andExpect(status().is(200))
		        .andReturn();
	}
	
	@Test
	public void getCompanyInfoPage_failByPostRequest() throws Exception {
		mockMvc.perform(
				 post("/company/info")
				 )
//				.andDo(MockMvcResultHandlers.print())
		        .andExpect(status().is(405))
		        .andReturn();
	}
	
	@Test
	public void getCompanyStats_successReturnCompanyInfo() throws Exception {
		CompanyInfoMessage expectedCompanyInfo = getCompanyInfo();
		
		when(companyServiceMock.getCompanyInfo("admin")).thenReturn(expectedCompanyInfo);
		
		mockMvc.perform(
				get("/company/info/get-company-stats")
				.cookie(getLoginCookie())
				)
		        .andExpect(status().is(200))
		        .andExpect(jsonPath("$.status").value(0))
		        .andExpect(jsonPath("$.id").value(expectedCompanyInfo.getId()))
		        .andExpect(jsonPath("$.name").value(expectedCompanyInfo.getName()))
		        .andExpect(jsonPath("$.cash").value(expectedCompanyInfo.getCash()))
		        .andExpect(jsonPath("$.defaultCash").value(expectedCompanyInfo.getDefaultCash()))
		        .andExpect(jsonPath("$.ownerName").value(expectedCompanyInfo.getOwnerName()))
		        .andExpect(jsonPath("$.employeesCount").value(expectedCompanyInfo.getEmployeesCount()))
		        .andExpect(jsonPath("$.contractsExecuting").value(expectedCompanyInfo.getContractsExecuting()))
		        .andExpect(jsonPath("$.contractsCompleted").value(expectedCompanyInfo.getContractsCompleted()))
		        .andExpect(jsonPath("$.contractsFailed").value(expectedCompanyInfo.getContractsFailed()))
		        .andReturn();
	}
	
	@Test
	public void getCompanyStats_failFromDatabase() throws Exception {
		when(companyServiceMock.getCompanyInfo("admin")).thenThrow(new DatabaseAccessException(""));
		
		mockMvc.perform(
				get("/company/info/get-company-stats")
				.cookie(getLoginCookie())
				)
		        .andExpect(status().is(200))
		        .andExpect(jsonPath("$.status").value(1))
		        .andReturn();
	}
}
