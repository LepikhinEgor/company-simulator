package company_simulator.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

import org.codehaus.jackson.map.ObjectMapper;
import org.hamcrest.core.Is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import controller.HomeController;
import controller.messages.Message;
import controller.messages.RegistrationMessage;
import services.UserService;


@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes={WebConfig.class, TestsDaoBeansConfiguration.class})
public class LoginControllerTest {

	private MockMvc mockMvc;
	private MockMvc mockMvcStandalone;
	
	@Autowired
	private WebApplicationContext webContex;
	
	@Autowired
	private HomeController homeController;
	
	@Autowired
	UserService userServiceMock;
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContex).build();
	}
	
	@Test
	public void getLoginPageSuccess() throws Exception {
		this.mockMvc.perform(
		 get("/login"))
        .andExpect(view().name("login"))
//        .andDo(MockMvcResultHandlers.print())
        .andReturn();
	}
	
	@Test
	public void getRegistrationPageSuccess() throws Exception {
		this.mockMvc.perform(
				 get("/registration"))
		        .andExpect(view().name("registration"))
		        .andReturn();
	}
	
	@Test
	public void checkLoginExist_returnLoginIsFree() throws Exception {
		when(userServiceMock.checkUserLoginAlreadyExist("admin")).thenReturn(false);
		this.mockMvc.perform(
				get("/checkLoginExist?login=admin"))
				.andExpect(jsonPath("$.status").value(6))
				.andReturn();
	}
}


