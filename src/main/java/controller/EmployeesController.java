package controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.messages.EmployeesListMessage;
import controller.messages.EmployeesListQuerryData;
import entities.Company;
import entities.Employee;
import entities.User;
import exceptions.DatabaseAccessException;
import services.CompanyService;
import services.EmployeeService;
import services.UserService;

@Controller
public class EmployeesController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeesController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	EmployeeService employeeService;
	
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
	
	@RequestMapping(value = "/company/hr/get-employees", method = RequestMethod.GET)
	@ResponseBody
	public EmployeesListMessage getEmployees(@RequestBody EmployeesListQuerryData requestData) {
		
		User userData = null;
		Employee[] employees = null;
		try {
			userData = userService.getUserDataByLoginEmail(requestData.getLoginEmail());
			if (userData != null)
				logger.info(userData.toString());
			
			Company userCompany = null;
			userCompany = companyService.getUserCompany(userData.getId());
			logger.info(userCompany.toString());
			
			employees = employeeService.getEmployeesList(userCompany.getId(), requestData.getOrderNum(), requestData.getPageNum());
			
		} catch (DatabaseAccessException e) {
			logger.error("user not received");
		}
		return new EmployeesListMessage(0, employees);
	}
	
}
