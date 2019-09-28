package controller;

import java.util.Locale;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.messages.EmployeeCreateData;
import controller.messages.EmployeeCreateMessage;
import controller.messages.EmployeeUpdateData;
import controller.messages.EmployeesListMessage;
import controller.messages.EmployeesListQuerryData;
import entities.Company;
import entities.Employee;
import entities.User;
import exceptions.DatabaseAccessException;
import exceptions.employees.EmployeesListException;
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
	public String home(@CookieValue(value = "signedUser", required = false) Cookie cookie) {
		
		if (cookie == null) {
			return "login";
		} else {
			String loginEmail = cookie.getValue();
			return "company";			
		}
		
	}
	
	@RequestMapping(value = "/company/hr", method = RequestMethod.GET)
	public String getHrPage(@CookieValue(value = "signedUser", required = false) Cookie cookie) {
		
		if (cookie == null) {
			return "login";
		} else {
			String loginEmail = cookie.getValue();
			return "hr";			
		}
		
	}
	
	@RequestMapping(value = "/company/hr/get-employees", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public EmployeesListMessage getEmployees(
			@CookieValue(value = "signedUser", required = false) Cookie cookie, 
			@RequestBody EmployeesListQuerryData requestData) {
		
		Employee[] employees = null;
		
		try {
			employees = employeeService.getEmployeesList(requestData, cookie.getValue());
			
		} catch (DatabaseAccessException e) {
			logger.error("employees list not received", e);
		} catch (EmployeesListException e) {
			logger.error("employees list not received", e);
		}
		
		return new EmployeesListMessage(0, employees);
	}
	
	@RequestMapping(value = "/company/hr/create-employee", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public EmployeeCreateMessage createEmployee(
			@CookieValue(value = "signedUser", required = false) Cookie cookie,
			@RequestBody EmployeeCreateData employeeData) {
		
		String userLogin = cookie.getValue();
		
		try {
			Employee createdEmployee = employeeService.createEmployee(employeeData, userLogin);
			
		} catch (DatabaseAccessException e) {
			logger.error("employee not created", e);
		}
		
		return new EmployeeCreateMessage(0);
	}
	
	@RequestMapping(value = "/company/hr/update-employee", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public EmployeeCreateMessage updateEmployee(
			@CookieValue(value = "signedUser", required = false) Cookie cookie,
			@RequestBody EmployeeUpdateData employeeData) {
		
		String loginEmail = cookie.getValue();
		
		try {
			Employee updatedEmployee = employeeService.updateEmployee(employeeData, loginEmail);
			
		} catch (DatabaseAccessException e) {
			logger.error("employee not update", e);
		}
		
		return new EmployeeCreateMessage(0);
	}
	
}
