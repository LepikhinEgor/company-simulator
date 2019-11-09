package company_simulator.controllers;

import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;
import java.sql.SQLException;
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
import controller.input.EmployeeCreateData;
import controller.input.EmployeesListQuerryData;
import entities.Employee;
import exceptions.DatabaseAccessException;
import exceptions.employees.EmployeesListException;
import exceptions.employees.IncorrectOrderNumException;
import exceptions.employees.IncorrectPageNumException;
import services.EmployeeService;
import static org.hamcrest.core.Is.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes={WebConfig.class, TestsDaoBeansConfiguration.class})
public class EmployeesControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webContext;
	
	@Autowired
	EmployeeService employeeServiceMock;
	
	private Cookie getLoginCookie() {
		Cookie loginCookie = new Cookie("signedUser", "admin");
		
		return loginCookie;
	}
	
	private String toJson(Object object) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}
	
	private EmployeesListQuerryData getEmployeesListQuerryData() {
		EmployeesListQuerryData querryData = new EmployeesListQuerryData();
		
		querryData.setOrderNum(0);
		querryData.setPageNum(0);
		
		return querryData;
	}
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
		reset(employeeServiceMock);
	}
	
	@Test
	public void getHrPage_succesReturnHrPage() throws Exception {
		mockMvc.perform(
				 get("/company/hr").cookie(getLoginCookie()))
		        .andExpect(view().name("hr"))
		        .andExpect(status().is(200))
//		        .andDo(MockMvcResultHandlers.print())
		        .andReturn();
	}
	
	@Test
	public void getHrPage_returnLoginPage() throws Exception {
		mockMvc.perform(
				 get("/company/hr"))
		        .andExpect(view().name("login"))
		        .andExpect(status().is(200))
//		        .andDo(MockMvcResultHandlers.print())
		        .andReturn();
	}
	
	@Test
	public void getEmployees_successReturnEmployees() throws Exception {
		EmployeesListQuerryData querryData = getEmployeesListQuerryData();
		
		List<Employee> employees = Arrays.asList(new Employee(), new Employee(), new Employee(), new Employee());
		
		when(employeeServiceMock.getEmployeesList(querryData, "admin", Locale.ENGLISH)).thenReturn(employees);
		
		mockMvc.perform(
				post("/company/hr/get-employees")
				.cookie(getLoginCookie())
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(querryData)
				))
//		.andDo(MockMvcResultHandlers.print())
		        .andExpect(status().is(200))
		        .andExpect(jsonPath("$.status").value(0))
		        .andReturn();
		
	}
	
	@Test
	public void getEmployees_failByDatabaseAcessException() throws Exception {
		EmployeesListQuerryData querryData = getEmployeesListQuerryData();
		
		when(employeeServiceMock.getEmployeesList(querryData, "admin",Locale.ENGLISH)).thenThrow(new DatabaseAccessException(""));
		
		mockMvc.perform(
				post("/company/hr/get-employees")
				.cookie(getLoginCookie())
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(querryData)
				))
				.andDo(MockMvcResultHandlers.print())
		        .andExpect(status().is(200))
		        .andExpect(jsonPath("$.status").value(1))
		        .andReturn();
		
	}
	
	@Test
	public void getEmployees_failByEmployeesListException() throws Exception {
		EmployeesListQuerryData querryData = getEmployeesListQuerryData();
		
		when(employeeServiceMock.getEmployeesList(querryData, "admin", Locale.ENGLISH)).thenThrow(new EmployeesListException(""));
		
		mockMvc.perform(
				post("/company/hr/get-employees")
				.cookie(getLoginCookie())
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(querryData)
				))
		        .andExpect(status().is(200))
		        .andExpect(jsonPath("$.status").value(1))
		        .andReturn();
		
	}
	
	@Test
	public void getEmployees_failByIncorrectOrderNumException() throws Exception {
		EmployeesListQuerryData querryData = getEmployeesListQuerryData();
		
		when(employeeServiceMock.getEmployeesList(querryData, "admin", Locale.ENGLISH)).thenThrow(new IncorrectOrderNumException(""));
		
		mockMvc.perform(
				post("/company/hr/get-employees")
				.cookie(getLoginCookie())
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(querryData)
				))
		        .andExpect(status().is(200))
		        .andExpect(jsonPath("$.status").value(1))
		        .andReturn();
	}
	
	@Test
	public void getEmployees_failByIncorrectPageNumException() throws Exception {
		EmployeesListQuerryData querryData = getEmployeesListQuerryData();
		
		when(employeeServiceMock.getEmployeesList(querryData, "admin", Locale.ENGLISH)).thenThrow(new IncorrectPageNumException(""));
		
		mockMvc.perform(
				post("/company/hr/get-employees")
				.cookie(getLoginCookie())
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(querryData)
				))
		        .andExpect(status().is(200))
		        .andExpect(jsonPath("$.status").value(1))
		        .andReturn();
		
	}
	
	@Test
	public void getEmployees_failBecauseCookieIsNull() throws Exception {
		EmployeesListQuerryData querryData = getEmployeesListQuerryData();
		
		when(employeeServiceMock.getEmployeesList(querryData, "admin", Locale.getDefault())).thenThrow(new IncorrectPageNumException(""));
		
		mockMvc.perform(
				post("/company/hr/get-employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(querryData)
				))
//		.andDo(MockMvcResultHandlers.print())
		        .andExpect(status().is(400))
		        .andReturn();
		
	}
	
	@Test
	public void getEmployees_failBecauseRequestBodeIsNull() throws Exception {
		EmployeesListQuerryData querryData = getEmployeesListQuerryData();
		
		when(employeeServiceMock.getEmployeesList(querryData, "admin", Locale.getDefault())).thenThrow(new IncorrectPageNumException(""));
		
		mockMvc.perform(
				post("/company/hr/get-employees")
				)
//		.andDo(MockMvcResultHandlers.print())
		        .andExpect(status().is(415))
		        .andReturn();
		
	}
	
	@Test
	public void generateEmployees_successReturnEmployees() throws Exception {
		TimeZone zone = TimeZone.getDefault();
		Locale locale = Locale.ENGLISH;
		List<Employee> employees = Arrays.asList(new Employee(), new Employee(), new Employee());
		
		when(employeeServiceMock.generateNewEmployees(getLoginCookie().getValue(), zone, locale)).thenReturn(employees);
		mockMvc.perform(
				get("/company/hr/get-generated-employees")
				.locale(locale)
				.cookie(getLoginCookie())
				.contentType(MediaType.APPLICATION_JSON)
				)
//		.andDo(MockMvcResultHandlers.print())
		        .andExpect(status().is(200))
		        .andExpect(jsonPath("$.status").value(0))
		        .andReturn();
	}
	
	@Test
	public void generateEmployees_failByNullCookie() throws Exception {
		TimeZone zone = TimeZone.getDefault();
		Locale locale = Locale.ENGLISH;
		List<Employee> employees = Arrays.asList(new Employee(), new Employee(), new Employee());
		
		when(employeeServiceMock.generateNewEmployees(getLoginCookie().getValue(), zone, locale)).thenReturn(employees);
		mockMvc.perform(
				get("/company/hr/get-generated-employees")
				.locale(locale)
				.contentType(MediaType.APPLICATION_JSON)
				)
		        .andExpect(status().is(400))
		        .andReturn();
	}
	
	@Test
	public void generateEmployees_failFromDB() throws Exception {
		TimeZone zone = TimeZone.getDefault();
		Locale locale = Locale.ENGLISH;
		
		when(employeeServiceMock.generateNewEmployees(getLoginCookie().getValue(), zone, locale)).thenThrow(new DatabaseAccessException(""));
		mockMvc.perform(
				get("/company/hr/get-generated-employees")
				.locale(locale)
				.cookie(getLoginCookie())
				.contentType(MediaType.APPLICATION_JSON)
				)
		        .andExpect(status().is(200))
		        .andExpect(jsonPath("$.status").value(1))
		        .andReturn();
	}
	
	@Test
	public void hireGeneratedEmployees_success() throws Exception {
		long[] employeesId = {1, 2, 3, 4};
		
		doNothing().when(employeeServiceMock).hireGeneratedEmployees(employeesId);
		
		mockMvc.perform(
				post("/company/hr/hire-generated-employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(employeesId)
				))
		.andDo(MockMvcResultHandlers.print())
		        .andExpect(status().is(200))
		        .andExpect(jsonPath("$.status").value(0))
		        .andReturn();
	}
	
	@Test
	public void hireGeneratedEmployees_failByDBException() throws Exception {
		long[] employeesId = {1, 2, 3, 4};
		
		doThrow(new DatabaseAccessException("")).when(employeeServiceMock).hireGeneratedEmployees(employeesId);
		
		mockMvc.perform(
				post("/company/hr/hire-generated-employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(employeesId)
				))
		.andDo(MockMvcResultHandlers.print())
		        .andExpect(status().is(200))
		        .andExpect(jsonPath("$.status").value(1))
		        .andReturn();
	}
}
