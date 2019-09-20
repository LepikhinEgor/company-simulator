package controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import entities.Company;
import entities.User;
import exceptions.DatabaseAccessException;
import services.CompanyService;
import services.UserService;

@Controller
public class EmployeesController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeesController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	CompanyService companyService;
	
	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public String home(@RequestParam String loginEmail) {
		
		User userData = null;
		try {
			userData = userService.getUserDataByLoginEmail(loginEmail);
		} catch (DatabaseAccessException e) {
			logger.error("user not received");
		}
		
		if (userData != null)
			logger.info(userData.toString());
		
		try {
			Company userCompany = companyService.getUserCompany(userData.getId());
			logger.info(userCompany.toString());
		} catch (DatabaseAccessException e) {
			logger.error(e.getMessage(), e);
		}
		
		return "company";
	}
	
}
