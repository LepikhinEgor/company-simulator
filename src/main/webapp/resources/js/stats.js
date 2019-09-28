function fillCompanyInfo() {
	$.ajax({
        type: "GET",
        url: "/company-simulator/company/info/get-company-stats",
        contentType: 'application/json',
        success: function(data) {
			console.log(data);
		}
      });
}