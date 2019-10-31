package company_simulator.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.Matchers.*;

import company_simulator.TestsDaoBeansConfiguration;
import config.SpringConfig;
import config.WebConfig;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hamcrest.core.Is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import controller.HomeController;
import controller.input.NewUserData;
import controller.messages.Message;
import controller.messages.RegistrationMessage;
import exceptions.DatabaseAccessException;
import exceptions.EmailAlreadyExistException;
import exceptions.InvalidEmailRegistrationException;
import exceptions.InvalidLoginRegistrationException;
import exceptions.InvalidPasswordRegistrationException;
import exceptions.LoginAlreadyExistException;
import services.UserService;


@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes={WebConfig.class, TestsDaoBeansConfiguration.class})
public class LoginControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webContext;
	
	@Autowired
	@Qualifier("mockUserService")
	UserService userServiceMock;
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
		reset(userServiceMock);
	}
	
	private String toJson(Object object) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}
	
	private NewUserData getUserData() {
		NewUserData userData = new NewUserData();
		
		userData.setEmail("admin@mail.ru");
		userData.setLogin("login");
		userData.setPassword("qwerty");
		
		return userData;
	}
	
	@Test
	public void getLoginPageSuccess() throws Exception {
		mockMvc.perform(
		 get("/login"))
        .andExpect(view().name("login"))
        .andExpect(status().is(200))
//        .andDo(MockMvcResultHandlers.print())
        .andReturn();
	}
	
	@Test
	public void getRegistrationPageSuccess() throws Exception {
		mockMvc.perform(
				 get("/registration"))
		        .andExpect(view().name("registration"))
		        .andExpect(status().is(200))
		        .andReturn();
	}
	
	@Test
	public void checkLoginExist_returnLoginIsFree() throws Exception {
		when(userServiceMock.checkUserLoginAlreadyExist("admin")).thenReturn(false);
		mockMvc.perform(
				get("/checkLoginExist?login=admin"))
				.andExpect(jsonPath("$.status").value(6))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andReturn();
	}
	
	@Test
	public void checkLoginExist_returnLoginAlreadyExist() throws Exception {
		when(userServiceMock.checkUserLoginAlreadyExist("admin")).thenReturn(true);
		mockMvc.perform(
				get("/checkLoginExist?login=admin"))
				.andExpect(jsonPath("$.status").value(5))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200))
				.andReturn();
	}
	
	@Test
	public void checkLoginExist_returnLoginReturnFail() throws Exception {
		doThrow(new DatabaseAccessException()).when(userServiceMock).checkUserLoginAlreadyExist("admin");
		mockMvc.perform(
				get("/checkLoginExist?login=admin"))
				.andExpect(jsonPath("$.status").value(1))
				.andExpect(status().is(200))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
	}
	
	
	
	@Test
	public void receiveNewUser_returnSuccess() throws JsonGenerationException, JsonMappingException, IOException, Exception {
		NewUserData userData = getUserData();
		when(userServiceMock.createNewUser(userData)).thenReturn(1L);
		mockMvc.perform(
				post("/receiveNewUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(userData))
				)
				.andExpect(jsonPath("$.status").value(0))
				.andExpect(status().is(200))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
	}
	
	@Test
	public void receiveNewUser_returnIncorrectLogin() throws JsonGenerationException, JsonMappingException, IOException, Exception {
		NewUserData userData = getUserData();
		when(userServiceMock.createNewUser(userData)).thenThrow(new InvalidLoginRegistrationException());
		mockMvc.perform(
				post("/receiveNewUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(userData))
				)
		.andDo(MockMvcResultHandlers.print())

				.andExpect(jsonPath("$.status").value(2))
				.andExpect(status().is(200))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
	}
	
	@Test
	public void receiveNewUser_returnIncorrectEmail() throws JsonGenerationException, JsonMappingException, IOException, Exception {
		NewUserData userData = getUserData();
		when(userServiceMock.createNewUser(userData)).thenThrow(new InvalidEmailRegistrationException());
		mockMvc.perform(
				post("/receiveNewUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(userData))
				)
				.andExpect(jsonPath("$.status").value(3))
				.andExpect(status().is(200))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
	}
	
	@Test
	public void receiveNewUser_returnLoginAlreadyExist() throws JsonGenerationException, JsonMappingException, IOException, Exception {
		NewUserData userData = getUserData();
		when(userServiceMock.createNewUser(userData)).thenThrow(new LoginAlreadyExistException());
		mockMvc.perform(
				post("/receiveNewUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(userData))
				)
				.andExpect(jsonPath("$.status").value(5))
				.andExpect(status().is(200))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
	}
	
	@Test
	public void receiveNewUser_returnEmailAlreadyExist() throws JsonGenerationException, JsonMappingException, IOException, Exception {
		NewUserData userData = getUserData();
		when(userServiceMock.createNewUser(userData)).thenThrow(new EmailAlreadyExistException());
		mockMvc.perform(
				post("/receiveNewUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(userData))
				)
				.andExpect(jsonPath("$.status").value(7))
				.andExpect(status().is(200))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
	}
	@Test
	public void receiveNewUser_returnIncorrectPassword() throws JsonGenerationException, JsonMappingException, IOException, Exception {
		NewUserData userData = getUserData();
		when(userServiceMock.createNewUser(userData)).thenThrow(new InvalidPasswordRegistrationException());
		mockMvc.perform(
				post("/receiveNewUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(userData))
				)
				.andExpect(jsonPath("$.status").value(4))
				.andExpect(status().is(200))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
	}
	
	@Test
	public void receiveNewUser_returnFailByDatabase() throws JsonGenerationException, JsonMappingException, IOException, Exception {
		NewUserData userData = getUserData();
		when(userServiceMock.createNewUser(userData)).thenThrow(new DatabaseAccessException());
		mockMvc.perform(
				post("/receiveNewUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(userData))
				)
				.andExpect(jsonPath("$.status").value(1))
				.andExpect(status().is(200))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
	}
}


