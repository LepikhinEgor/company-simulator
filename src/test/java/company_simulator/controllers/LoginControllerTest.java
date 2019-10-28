package company_simulator.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import controller.HomeController;
public class LoginControllerTest {

	private MockMvc mockMvc;
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(new HomeController()).build();
	}
	
	@Test
	public void checkLoginExist_returnTrue() throws Exception {
		this.mockMvc.perform(
		 get("/login"))
//        .andExpect(view().name("login.ftl"))
        .andDo(MockMvcResultHandlers.print())
        .andReturn();
	}
}
