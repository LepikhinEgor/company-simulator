package company_simulator.controllers;

import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.Cookie;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import company_simulator.TestsDaoBeansConfiguration;
import config.WebConfig;
import controller.input.ChangeContractTeamData;
import controller.messages.entities.ContractRestData;
import entities.Contract;
import exceptions.DatabaseAccessException;
import exceptions.employees.DoubleIdException;
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
	
	private String toJson(Object object) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}
	
	private Cookie getLoginCookie() {
		Cookie loginCookie = new Cookie("signedUser", "admin");
		
		return loginCookie;
	}
	
	private ChangeContractTeamData getChangeContractTeamData() {
		ChangeContractTeamData changeData = new ChangeContractTeamData();
		long[] freeEmployees = {0, 1, 2};
		long[] hiredEmployees = {4, 5, 6};
		
		changeData.setContractId(1);
		changeData.setFreeEmployees(freeEmployees);
		changeData.setHiredEmployees(hiredEmployees);
		
		return changeData;
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
//		        .andDo(MockMvcResultHandlers.print())
		        .andExpect(jsonPath("$.status").value(0))
		        .andExpect(status().is(200))
		        .andReturn();
	}
	
	@Test
	public void resolveContract_returnFailByDbException() throws Exception {
		doThrow(new DatabaseAccessException("")).when(contractServiceMock).resolveContract(1);
		
		mockMvc.perform(
				 get("/company/contracts/resolve-contract?contractId=1"))
//		        .andDo(MockMvcResultHandlers.print())
		        .andExpect(jsonPath("$.status").value(1))
		        .andExpect(status().is(200))
		        .andReturn();
	}
	
	@Test
	public void getGeneratedContracts_success() throws Exception {
		List<ContractRestData> generatedContracts = Arrays.asList(new ContractRestData(), new ContractRestData(), new ContractRestData());
		Locale locale = Locale.ENGLISH;
		TimeZone timezone = TimeZone.getDefault();
		when(contractServiceMock.getGeneratedContracts("admin", locale, timezone)).thenReturn(generatedContracts);
		
		mockMvc.perform(
				 get("/company/contracts/get-generated-contracts")
				 .cookie(getLoginCookie())
				 .locale(locale))
		        .andExpect(jsonPath("$.status").value(0))
		        .andExpect(status().is(200))
		        .andReturn();
	}
	
	@Test
	public void getGeneratedContracts_failByDatabaseException() throws Exception {
		Locale locale = Locale.ENGLISH;
		TimeZone timezone = TimeZone.getDefault();
		when(contractServiceMock.getGeneratedContracts("admin", locale, timezone)).thenThrow(new DatabaseAccessException());
		
		mockMvc.perform(
				 get("/company/contracts/get-generated-contracts")
				 .cookie(getLoginCookie())
				 .locale(locale))
//		        .andDo(MockMvcResultHandlers.print())
		        .andExpect(jsonPath("$.status").value(1))
		        .andExpect(status().is(200))
		        .andReturn();
	}
	
	@Test
	public void changeContractTeam_success() throws JsonGenerationException, JsonMappingException, IOException, Exception {
		ChangeContractTeamData changeData = getChangeContractTeamData();
		doNothing().when(contractServiceMock).reassignEmployees(
				changeData.getHiredEmployees(),
				changeData.getFreeEmployees(),
				changeData.getContractId());
		
		mockMvc.perform(
				 post("/company/contracts/change-contract-team")
				 .cookie(getLoginCookie())
				 .contentType(MediaType.APPLICATION_JSON)
				.content(toJson(changeData)))
//		        .andDo(MockMvcResultHandlers.print())
		        .andExpect(jsonPath("$.status").value(0))
		        .andExpect(status().is(200))
		        .andReturn();
	}
	
	@Test
	public void changeContractTeam_failByDBException() throws JsonGenerationException, JsonMappingException, IOException, Exception {
		ChangeContractTeamData changeData = getChangeContractTeamData();
		doThrow(new DatabaseAccessException()).when(contractServiceMock).reassignEmployees(
				changeData.getHiredEmployees(),
				changeData.getFreeEmployees(),
				changeData.getContractId());
		
		mockMvc.perform(
				 post("/company/contracts/change-contract-team")
				 .cookie(getLoginCookie())
				 .contentType(MediaType.APPLICATION_JSON)
				.content(toJson(changeData)))
//		        .andDo(MockMvcResultHandlers.print())
		        .andExpect(jsonPath("$.status").value(1))
		        .andExpect(status().is(200))
		        .andReturn();
	}
	
	@Test
	public void changeContractTeam_failByDoubleIdException() throws JsonGenerationException, JsonMappingException, IOException, Exception {
		ChangeContractTeamData changeData = getChangeContractTeamData();
		doThrow(new DoubleIdException("")).when(contractServiceMock).reassignEmployees(
				changeData.getHiredEmployees(),
				changeData.getFreeEmployees(),
				changeData.getContractId());
		
		mockMvc.perform(
				 post("/company/contracts/change-contract-team")
				 .cookie(getLoginCookie())
				 .contentType(MediaType.APPLICATION_JSON)
				.content(toJson(changeData)))
		        .andDo(MockMvcResultHandlers.print())
		        .andExpect(jsonPath("$.status").value(1))
		        .andExpect(status().is(200))
		        .andReturn();
	}
}
