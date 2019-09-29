function requestCompanyInfo() {
	$.ajax({
        type: "GET",
        url: "/company-simulator/company/info/get-company-stats",
        contentType: 'application/json',
        success: function(data) {
			console.log(data);
			fillCompanyInfo(data);
		}
      });
}

function fillCompanyInfo(data) {
	$('#company_name_info').text(data.name);
	$('#owner_name_info').text(data.ownerName);
	$('#company_cash_info').text(data.cash);
	$('#company_def_cash_info').text(data.defaultCash);
	$('#employees_count_info').text(data.employeesCount);
	$('#contracts_executing_info').text(data.contractsExecuting);
	$('#contracts_completed_info').text(data.contractsCompleted);
	$('#contracts_failed_info').text(data.contractsFailed);
}