package company_simulator.controllers;

import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import company_simulator.TestsDaoBeansConfiguration;
import config.WebConfig;
import controller.messages.entities.ContractRestData;
import entities.Contract;
import exceptions.DatabaseAccessException;
import services.CompanyService;
import services.ContractService;
import services.UserService;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes={WebConfig.class, TestsDaoBeansConfiguration.class})
public class ContractsControllerTest {
	
private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webContext;
	
	@Autowired
	ContractService contractServiceMock;
	
	private Cookie getLoginCookie() {
		Cookie loginCookie = new Cookie("signedUser", "admin");
		
		return loginCookie;
	}
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
		reset(contractServiceMock);
	}
	
	@Test
	public void getContractsPage_successReturnContractsPage() throws Exception {
		mockMvc.perform(
				 get("/company/contracts").cookie(getLoginCookie()))
//		        .andDo(MockMvcResultHandlers.print())
		        .andExpect(view().name("contracts"))
		        .andExpect(status().is(200))
		        .andReturn();
	}
	
	@Test
	public void getContractsPage_returnLoginPageByNullCookie() throws Exception {
		mockMvc.perform(
				 get("/company/contracts"))
//		        .andDo(MockMvcResultHandlers.print())
		        .andExpect(view().name("login"))
		        .andExpect(status().is(200))
		        .andReturn();
	}
	
	@Test
	public void getContractsList_successReturnContracts() throws Exception {
		List<ContractRestData> contracts = Arrays.asList(new ContractRestData(), new ContractRestData(), new ContractRestData());
		
		when(contractServiceMock.getUserActiveContracts(0, 0, getLoginCookie().getValue())).thenReturn(contracts);
		
		mockMvc.perform(
				 get("/company/contracts/get-active-contracts?sortOrder=0&pageNum=0")
				 .cookie(getLoginCookie()))
//		        .andDo(MockMvcResultHandlers.print())
		        .andExpect(jsonPath("$.status").value(0))
		        .andExpect(status().is(200))
		        .andReturn();
	}
	
	@Test
	public void getContractsList_failByNullCookie() throws Exception {
		List<ContractRestData> contracts = Arrays.asList(new ContractRestData(), new ContractRestData(), new ContractRestData());
		
		when(contractServiceMock.getUserActiveContracts(0, 0, getLoginCookie().getValue())).thenReturn(contracts);
		
		mockMvc.perform(
				 get("/company/contracts/get-active-contracts?sortOrder=0&pageNum=0"))
//		        .andDo(MockMvcResultHandlers.print())
		        .andExpect(jsonPath("$.status").value(1))
		        .andExpect(status().is(200))
		        .andReturn();
	}
	
	@Test
	public void getContractsList_failByDBException() throws Exception {
		when(contractServiceMock.getUserActiveContracts(0, 0, getLoginCookie().getValue())).thenThrow(new DatabaseAccessException(""));
		
		mockMvc.perform(
				 get("/company/contracts/get-active-contracts?sortOrder=0&pageNum=0")
				 .cookie(getLoginCookie()))
//		        .andDo(MockMvcResultHandlers.print())
		        .andExpect(jsonPath("$.status").value(1))
		        .andExpect(status().is(200))
		        .andReturn();
	}
	
	@Test
	public void resolveContract_success() throws Exception {
		doNothing().when(contractServiceMock).resolveContract(1);
		
		mockMvc.perform(
				 get("/company/contracts/resolve-contract?contractId=1"))
		        .andDo(MockMvcResultHandlers.print())
		        .andExpect(jsonPath("$.status").value(0))
		        .andExpect(status().is(200))
		        .andReturn();
	}
	
	@Test
	public void resolveContract_returnFailByDbException() throws Exception {
		doThrow(new DatabaseAccessException("")).when(contractServiceMock).resolveContract(1);
		
		mockMvc.perform(
				 get("/company/contracts/resolve-contract?contractId=1"))
		        .andDo(MockMvcResultHandlers.print())
		        .andExpect(jsonPath("$.status").value(1))
		        .andExpect(status().is(200))
		        .andReturn();
	}
}
