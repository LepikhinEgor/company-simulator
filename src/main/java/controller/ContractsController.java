package controller;

import javax.servlet.http.Cookie;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.input.CreateContractData;
import controller.messages.CreateContractMessage;

@Controller
public class ContractsController {
	
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
	public CreateContractMessage createContract(@RequestBody CreateContractData contractData) {
		CreateContractMessage message = new CreateContractMessage(0);
		
		return message;
	}
}
