package controller;	

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.messages.CompanyInfoMessage;
import exceptions.DatabaseAccessException;
import services.CompanyService;

@Controller
public class CompanyController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
	
	private CompanyService companyService;
	
	@Autowired
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String getCompanyPage(@CookieValue(value = "signedUser", required = false) Cookie cookie) {
		return "redirect:company";
	}
	
	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public String getHomePage(@CookieValue(value = "signedUser", required = false) Cookie cookie) {
		
		if (cookie == null) {
			return "login";
		} else {
			String loginEmail = cookie.getValue();
			return "company";			
		}
		
	}
	
	@RequestMapping(value = "company/info", method = RequestMethod.GET)
	public String getCompanyInfoPage() {
		return "info";
	}
	
	@RequestMapping(value = "company/info/get-company-stats", method = RequestMethod.GET)
	@ResponseBody
	public CompanyInfoMessage getCompanyStats(@CookieValue(value = "signedUser", required = false) Cookie cookie) {
		CompanyInfoMessage companyInfo = null;
		
		try {
			companyInfo = companyService.getCompanyInfo(cookie.getValue());
		} catch (DatabaseAccessException e) {
			return new CompanyInfoMessage(CompanyInfoMessage.FAIL, e.getMessage());
		}
		
		companyInfo.setStatus(CompanyInfoMessage.SUCCESS);
		
		return companyInfo;
	}
}
