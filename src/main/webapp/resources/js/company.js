$(document).ready(function() {
	setEventHandlers();
	
	var lastTab = localStorage.getItem("content_tab");

	switch (lastTab) {
	  case "info":
		  fillCompanyInfoPage();
		  break;
	  case "hr":
		  fillEmployeesPage();
		  break;
	  case "contracts":
		  requestContractsPage();
		  break;
	  default:
		  fillCompanyInfoPage();
	}
});

function setEventHandlers() {
	$('#get_company_info').on('click', fillCompanyInfoPage);
    $('#get_hr').on('click', fillEmployeesPage);
    $('#get_contracts').on('click', requestContractsPage);
}

function fillCompanyInfoPage() {
	$.ajax({
        type: "GET",
        url: "/company-simulator/company/info",
        contentType: 'application/json',
        success: function(data) {
			$('#tab_content').html(data);
		}
      });
	localStorage.setItem("content_tab", "info");
	requestCompanyInfo();
}

function fillEmployeesPage() {
	$.ajax({
        type: "GET",
        url: "/company-simulator/company/hr",
        contentType: 'application/json',
        success: function(data) {
			$('#tab_content').html(data);
			employeesPageSetup();
			localStorage.setItem("content_tab", "hr");
		}
      });
}

function requestContractsPage() {
	$.ajax({
        type: "GET",
        url: "/company-simulator/company/contracts",
        contentType: 'application/json',
        success: function(data) {
			$('#tab_content').html(data);
			contractsPageSetup();
			localStorage.setItem("content_tab", "contracts");
		}
      });
}