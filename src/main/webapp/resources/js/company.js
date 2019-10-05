$(document).ready(function() {
	setEventHandlers();
	fillCompanyInfoPage();
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
		}
      });
}