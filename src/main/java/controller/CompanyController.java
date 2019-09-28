package controller;	

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.messages.CompanyInfoMessage;

@Controller
public class CompanyController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
	
	@RequestMapping(value = "company/info", method = RequestMethod.GET)
	public String getCompanyInfoPage() {
		return "info";
	}
	
	@RequestMapping(value = "company/info/get-company-stats", method = RequestMethod.GET)
	@ResponseBody
	public CompanyInfoMessage getCompanyStats() {
		CompanyInfoMessage companyStats = new CompanyInfoMessage();
		
		companyStats.setId(1);
		companyStats.setCash(1488);
		companyStats.setContractsCompleted(2);
		companyStats.setContractsFailed(5);
		companyStats.setContractsExecuting(1);
		companyStats.setDefaultCash(100000);
		companyStats.setEmployeesCount(28);
		companyStats.setName("Roga&Kopyta");
		companyStats.setOwnerName("Ivanov");
		
		return companyStats;
	}
}
