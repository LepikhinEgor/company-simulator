package controller;

import java.util.List;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.input.CreateContractData;
import controller.messages.ContractTeamMessage;
import controller.messages.ContractsListMessage;
import controller.messages.CreateContractMessage;
import controller.messages.Message;
import entities.Contract;
import entities.Employee;
import exceptions.DatabaseAccessException;
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
	public String getHrPage(@CookieValue(value = "signedUser", required = false) Cookie cookie) {
		
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
		
		List<Contract> contracts = null;
		
		try {
			contracts = contractService.getUserActiveContracts(sortOrder, pageNum, login);
		} catch (DatabaseAccessException e) {
			return new ContractsListMessage(Message.FAIL, e.getMessage());
		}
		
		return new ContractsListMessage(Message.SUCCESS,"Success return contracts list" , contracts);
	}
	
	@RequestMapping(value="/company/contracts/get-contract-team", method = RequestMethod.GET)
	@ResponseBody
	public ContractTeamMessage getContractTeamMessage(@RequestParam(value = "contractId") long contractId,
			@CookieValue(value="signedUser", required = false) Cookie cookie) {
		String login;
		if (cookie != null) 
			login = cookie.getValue();
		else 
			return new ContractTeamMessage(Message.FAIL);
		
		List<Employee> contractTeam;
		List<Employee> freeEmployees;
		
		contractTeam = employeeService.getContractTeam();
		freeEmployees = employeeService.getFreeEmployees();
		
		return new ContractTeamMessage(Message.SUCCESS);
		
	}
}
