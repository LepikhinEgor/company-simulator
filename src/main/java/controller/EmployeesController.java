package controller;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.LocaleResolver;

import controller.input.EmployeeCreateData;
import controller.messages.EmployeeCreateMessage;
import controller.input.EmployeeUpdateData;
import controller.messages.EmployeesListMessage;
import controller.messages.GeneratedEmployeesMessage;
import controller.messages.HireEmployeesMessage;
import controller.messages.Message;
import controller.input.EmployeesListQuerryData;
import entities.Company;
import entities.Employee;
import entities.User;
import exceptions.DatabaseAccessException;
import exceptions.employees.EmployeesListException;
import exceptions.employees.IncorrectOrderNumException;
import exceptions.employees.IncorrectPageNumException;
import services.CompanyService;
import services.EmployeeService;
import services.UserService;

@Controller
public class EmployeesController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeesController.class);
	
	@Autowired
	EmployeeService employeeService;
	
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
			@CookieValue(value = "signedUser", required = true) Cookie cookie, 
			@RequestBody EmployeesListQuerryData requestData,
			Locale locale) {
		
		List<Employee> employees = null;
		
		try {
			employees = employeeService.getEmployeesList(requestData, cookie.getValue(), locale);
		} catch (DatabaseAccessException | 
				EmployeesListException | 
				IncorrectOrderNumException |
				IncorrectPageNumException e) {
			return new EmployeesListMessage(EmployeesListMessage.FAIL, e.getMessage());
		}
		
		return new EmployeesListMessage(EmployeesListMessage.SUCCESS, employees);
	}
	
	@RequestMapping(value = "/company/hr/create-employee", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public EmployeeCreateMessage createEmployee(
			@CookieValue(value = "signedUser", required = true) Cookie cookie,
			@RequestBody EmployeeCreateData employeeData) {
		
		String userLogin = cookie.getValue();
		
		try {
			Employee createdEmployee = employeeService.createEmployee(employeeData, userLogin);
			
		} catch (DatabaseAccessException e) {
			logger.error("employee not created", e);
			return new EmployeeCreateMessage(Message.FAIL);
		}
		
		return new EmployeeCreateMessage(Message.SUCCESS);
	}
	
	@RequestMapping(value = "/company/hr/update-employee", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public EmployeeCreateMessage updateEmployee(
			@CookieValue(value = "signedUser", required = true) Cookie cookie,
			@RequestBody EmployeeUpdateData employeeData) {
		
		String loginEmail = cookie.getValue();
		
		try {
			Employee updatedEmployee = employeeService.updateEmployee(employeeData, loginEmail);
			
		} catch (DatabaseAccessException e) {
			logger.error("employee not update", e);
		}
		
		return new EmployeeCreateMessage(0);
	}
	
	@GetMapping(value = "/company/hr/get-generated-employees")
	@ResponseBody
	public GeneratedEmployeesMessage generateEmployees(
			@CookieValue(value = "signedUser") Cookie cookie,
			TimeZone timezone,
			Locale locale) {

		String login = cookie.getValue();
		
		List<Employee> generatedEmployees;
		try {
			generatedEmployees = employeeService.generateNewEmployees(login, timezone,  locale);
		} catch (DatabaseAccessException e) {
			return new GeneratedEmployeesMessage(Message.FAIL);
		}
		
		return new GeneratedEmployeesMessage(Message.SUCCESS, generatedEmployees);
	}
	
	@PostMapping("/company/hr/hire-generated-employees")
	@ResponseBody
	public HireEmployeesMessage hireGeneratedEmployees(@RequestBody long[] employeesId) {
		
		try {
			employeeService.hireGeneratedEmployees(employeesId);
		} catch (DatabaseAccessException e) {
			return new HireEmployeesMessage(Message.FAIL);
		}
		return new HireEmployeesMessage(Message.SUCCESS);
	}
}
