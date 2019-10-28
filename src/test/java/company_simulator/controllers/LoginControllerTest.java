package company_simulator.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import config.SpringConfig;
import config.WebConfig;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import controller.HomeController;
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes={WebConfig.class, SpringConfig.class})
public class LoginControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webContex;
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContex).build();
	}
	
	@Test
	public void checkLoginExist_returnTrue() throws Exception {
		this.mockMvc.perform(
		 get("/login"))
        .andExpect(view().name("login"))
//        .andDo(MockMvcResultHandlers.print())
        .andReturn();
	}
}
