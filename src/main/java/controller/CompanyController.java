package controller;	

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.messages.CompanyInfoMessage;
import entities.Company;
import entities.User;
import exceptions.DatabaseAccessException;
import services.CompanyService;
import services.UserService;

@Controller
public class CompanyController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
	
	private CompanyService companyService;
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String getCompanyPage(@CookieValue(value = "signedUser", required = false) Cookie cookie) {
		return "redirect:company";
	}
	
	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public String getHomePage(@CookieValue(value = "signedUser", required = false) Cookie cookie,
			Model model) {
		
		if (cookie == null) 
			return "login";
		String loginEmail = cookie.getValue();
		
		String companyName = "company";
		String userLogin = "username";
		try {
			Company company = companyService.getUserCompany(loginEmail);
			companyName = company.getName();
			
			User user = userService.getUserDataByLoginEmail(loginEmail);
			userLogin = user.getLogin();
		} catch (DatabaseAccessException e) {
			logger.info("User company not founded", e);
		}
		
		model.addAttribute("company", companyName);
		model.addAttribute("login", userLogin);
		
		return "company";			
		
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
