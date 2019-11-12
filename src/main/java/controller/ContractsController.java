package controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.input.ChangeContractTeamData;
import controller.input.CreateContractData;
import controller.messages.ContractTeamMessage;
import controller.messages.ContractsListMessage;
import controller.messages.CreateContractMessage;
import controller.messages.GeneratedContractsMessage;
import controller.messages.Message;
import controller.messages.entities.ContractRestData;
import entities.Contract;
import entities.Employee;
import exceptions.DatabaseAccessException;
import exceptions.employees.DoubleEmployeeIdException;
import services.ContractService;
import services.EmployeeService;

@Controller
public class ContractsController {
	
	private static final Logger logger = LoggerFactory.getLogger(ContractsController.class);
	
	private ContractService contractService;
	
	private EmployeeService employeeService;
	
	@Autowired
	public void serContractService(ContractService contractService) {
		this.contractService = contractService;
	}
	
	@Autowired
	public void serContractService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@RequestMapping(value = "/company/contracts", method = RequestMethod.GET)
	public String getContractsPage(@CookieValue(value = "signedUser", required = false) Cookie cookie) {
		
		if (cookie == null) {
			return "login";
		} else {
			String loginEmail = cookie.getValue();
			return "contracts";			
		}
		
	}
	
	@RequestMapping(value="/company/contracts/create-contract", method = RequestMethod.POST)
	@ResponseBody
	public CreateContractMessage createContract(@RequestBody CreateContractData contractData,
			@CookieValue(value = "signedUser", required = false) Cookie cookie) {
		CreateContractMessage message;
		
		String userLogin = cookie.getValue();
		if (userLogin == null)
			return new CreateContractMessage(Message.FAIL, "User login from cookie not found");
		
		try {
			contractService.createContract(contractData, userLogin);
		} catch (DatabaseAccessException e) {
			logger.error(e.getMessage(), e);
			return new CreateContractMessage(Message.FAIL, e.getMessage());
		}
		
		return new CreateContractMessage(0);
	}
	
	@RequestMapping(value="/company/contracts/get-active-contracts", method = RequestMethod.GET)
	@ResponseBody
	public ContractsListMessage getContractsList(@RequestParam(value = "sortOrder") int sortOrder,
			@RequestParam(value = "pageNum") int pageNum,
			@CookieValue(value="signedUser", required = false) Cookie cookie) {
		String login;
		if (cookie != null) 
			login = cookie.getValue();
		else 
			return new ContractsListMessage(Message.FAIL);
		
		List<ContractRestData> contracts = null;
		
		try {
			contracts = contractService.getUserActiveContracts(sortOrder, pageNum, login);
		} catch (DatabaseAccessException e) {
			return new ContractsListMessage(Message.FAIL, e.getMessage());
		}
		
		return new ContractsListMessage(Message.SUCCESS,"Success return contracts list" , contracts);
	}
	
	@RequestMapping(value="/company/contracts/get-contract-team", method = RequestMethod.GET)
	@ResponseBody
	public ContractTeamMessage getContractTeam(@RequestParam(value = "contractId") long contractId,
			@CookieValue(value="signedUser", required = false) Cookie cookie) {
		String login;
		if (cookie != null) 
			login = cookie.getValue();
		else 
			return new ContractTeamMessage(Message.FAIL);
		
		List<Employee> contractTeam;
		List<Employee> freeEmployees;
		
		try {
			contractTeam = employeeService.getContractTeam(login, contractId);
			freeEmployees = employeeService.getFreeEmployees(login);
		} catch (DatabaseAccessException e) {
			logger.error(e.getMessage(), e);
			return new ContractTeamMessage(Message.FAIL, e.getMessage());
		}
		
		return new ContractTeamMessage(Message.SUCCESS, "Success return contract employees data", contractTeam, freeEmployees);
		
	}
	
	@RequestMapping(value="/company/contracts/change-contract-team", method = RequestMethod.POST)
	@ResponseBody
	public Message changeContractTeamMessage(@RequestBody ChangeContractTeamData newTeamData) {
		try {
			contractService.reassignEmployees(newTeamData.getHiredEmployees(), newTeamData.getFreeEmployees(), newTeamData.getContractId());
		} catch (DoubleEmployeeIdException e) {
			logger.error(e.getMessage(),e);
			return new Message(Message.FAIL, e.getMessage());
		} catch (DatabaseAccessException e) {
			logger.error(e.getMessage(),e);
			return new Message(Message.FAIL, e.getMessage());
		}
		
		return new Message(Message.SUCCESS);
	}
	
	@RequestMapping(value="/company/contracts/resolve-contract", method = RequestMethod.GET)
	@ResponseBody
	public Message resolveContract(@RequestParam(value = "contractId") long contractId) {
		
		try {
			contractService.resolveContract(contractId);
		} catch (DatabaseAccessException e) {
			return new Message(Message.FAIL, e.getMessage());
		}
		
		return new Message(Message.SUCCESS);
	}
	
	@GetMapping("/company/contracts/get-generated-contracts")
	@ResponseBody
	public GeneratedContractsMessage getGeneratedContracts(
			@CookieValue(value="signedUser", required = true) Cookie cookie,
			Locale locale) {
		try {
			logger.info("1");
			List<ContractRestData> generatedContracts = contractService.getGeneratedContracts(cookie.getValue(), locale);
			logger.info("2");
			return new GeneratedContractsMessage(Message.SUCCESS, generatedContracts);
		} catch (DatabaseAccessException e) {
			return new GeneratedContractsMessage(Message.FAIL);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new GeneratedContractsMessage(Message.FAIL);
	}
}
