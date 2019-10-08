package controller;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.input.CreateContractData;
import controller.messages.CreateContractMessage;
import controller.messages.Message;
import exceptions.DatabaseAccessException;
import services.ContractService;

@Controller
public class ContractsController {
	
	private static final Logger logger = LoggerFactory.getLogger(ContractsController.class);
	
	private ContractService contractService;
	
	@Autowired
	public void serContractService(ContractService contractService) {
		this.contractService = contractService;
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
}
