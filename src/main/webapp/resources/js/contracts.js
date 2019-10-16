function contractsPageSetup() {
	refreshContractsEventHandlers();
	requestContractsList();
}

var changedContractId;

var oldFreeEmployeesId = [];
var oldHiredEmployeesId = [];

function refreshContractsEventHandlers() {
    $('#create_new_contract').off('click');
    $('#apply_contract_data').off('click');
    $('.contract_name').off('click');
    $('.change_contract_team').off('click');
    $(".close_modal_contract_team_apply").off('click');

    $('#create_new_contract').on('click', newContractOpenModal);
    $('#apply_contract_data').on('click', applyContractData);
    $('.contract_name').on('click', changeContractOpenModal);
    $('.change_contract_team').on('click', changeContractTeamOpenModal);
    $(".close_modal_contract_team_apply").on('click', applyContractTeamChanges)
}

function refreshContractTeamEventHandlers() {
	$('.hire_employee_to_contract').off('click');
	$('.make_employee_free').off('click');
	
	$('.hire_employee_to_contract').on('click', hireEmployeeToContract);
	$('.make_employee_free').on('click', makeEmployeeFree);
}

function applyContractTeamChanges() {
	var newHiredEmployeesId = [];
	var newFreeEmployeesId = [];
	
	$('#contract_hired_employees_table tr[id]').each(function () {
		var fullId =  $(this).attr('id');
		var id = fullId.substring(6);
		
		newHiredEmployeesId.push(id);
	});
	
	$('#contract_free_employees_table tr[id]').each(function () {
		var fullId =  $(this).attr('id');
		var id = fullId.substring(6);
		
		newFreeEmployeesId.push(id);
	});
	
	var hiredEmployeesId = [];
	for (var emp_id in newHiredEmployeesId) {
		if (!contains(oldHiredEmployeesId, newHiredEmployeesId[emp_id])) {
			hiredEmployeesId.push((newHiredEmployeesId[emp_id]));
		}
	}
	
	var freeEmployeesId = [];
	for (var f_emp_id in newFreeEmployeesId) {
		if (!contains(oldFreeEmployeesId, newFreeEmployeesId[f_emp_id])) {
			freeEmployeesId.push(newFreeEmployeesId[f_emp_id]);
		}
	}
	
	console.log(hiredEmployeesId);
	console.log(freeEmployeesId); 
	
	requestChangeContractTeam(hiredEmployeesId, freeEmployeesId);
}

function contains(arr, elem) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] == elem) {
            return true;
        }
    }
    return false;
}

function changeContractOpenModal() {
    var parent = $(this).closest("tr");
    changedContractId = parent.attr("id");

    var newContract = {
        name: parent.find(".contract_name").text() || "",
        size : parent.find(".contract_size").text() || "",
        fee : parent.find(".contract_fee").text() || "0",
        deadline : parent.find(".contract_deadline").text() || "0",
    }
    openContractModalWindow(newContract);
}

function requestChangeContractTeam(hiredEmployeesId, freeEmployeesId) {
	var employeesId = {
		hiredEmployees: hiredEmployeesId,
		freeEmployees: freeEmployeesId
	}
	$.ajax({
        type: "POST",
        url: "/company-simulator/company/contracts/change-contract-team",
        contentType: 'application/json',
        data: JSON.stringify(employeesId),
        success: function(data) {
			console.log(data);
			status = data.status;
		}
      });
}

function newContractOpenModal() {
    changedContractId = "newContract";

    var newContract = {
        name: "",
        size : "",
        fee : "",
        deadline :  "",
    }

    openContractModalWindow(newContract);
}

function changeContractTeamOpenModal() {
	var parent = $(this).closest("tr");
	changedContractId = parent.attr("id");
	
	var idNum = changedContractId.substring(9);
	
	requestContractTeamAndFreeEmployees(idNum);
	openContractTeamModalWindow(0);
}

function openContractTeamModalWindow(employeesSet) {
	document.location.href = "#contract_team_modal_window";
}

function openContractModalWindow(contractData) {
    $('.input_contract_name').val(contractData.name || "");
    $('.input_contract_size').val(contractData.size || "");
    $('.input_contract_fee').val(contractData.fee || "");
    $('.input_contract_deadline').val(contractData.deadline || "");

    document.location.href = "#contract_modal_window";
}

function applyContractData() {
    var newContract = {
        name: $('.input_contract_name').val() || "",
        size : $('.input_contract_size').val() || "",
        fee : $('.input_contract_fee').val() || "0",
        deadline : $('.input_contract_deadline').val() || "0",
    }

    if (changedContractId === "newContract")
        addNewContract(newContract);
    else
    	changeContractData(newContract);
    
    document.location.href = "#";
}

function changeContractData(contractData) {
	
    var selector = "#" + changedContractId;
    $(selector).find(".contract_name").text(contractData.name);
    $(selector).find(".contract_size").text(contractData.size);
    $(selector).find(".contract_fee").text(contractData.fee);
    $(selector).find(".contract_deadline").text(contractData.deadline);
}

function addNewContract(newContract) {
	var status = requestCreateContract(newContract);
	if (status == 0) {
		newContract.progress = 0;
		newContract.perfomance = 0;
		newContract.expected = 0;
		
		addContractToTable(newContract);
	}
}

function requestCreateContract(newContract) {
	var status = 0;
	$.ajax({
        type: "POST",
        url: "/company-simulator/company/contracts/create-contract",
        contentType: 'application/json',
        data: JSON.stringify(newContract),
        success: function(data) {
			console.log(data);
			status = data.status;
		}
      });
	return status;
}

function requestContractsList() {
	var requestData = {
		sortOrder: 0,
		pageNum: 0
    }

	$.ajax({
        type: "GET",
        url: "/company-simulator/company/contracts/get-active-contracts?sortOrder=0&pageNum=0",
        contentType: 'application/json',
        success: function(data) {
			console.log(data);
			
			for (var key in data) {
				if (key === "contracts") {
					var contracts = data[key];
					for(var contract in contracts) {
						console.log(contracts[contract]);
						var contractData = {
							id: contracts[contract].id,
							name: contracts[contract].name,
							size: contracts[contract].perfomanceUnits,
							fee: contracts[contract].fee,
							deadline: contracts[contract].deadline,
							progress: 0,
							perfomance: 0,
							expected: 0
						}
						
			    		addContractToTable(contractData);
					}
				}
			}
		}
      });
}

function requestContractTeamAndFreeEmployees(contractId) {
	$.ajax({
        type: "GET",
        url: "/company-simulator/company/contracts/get-contract-team?contractId=" + contractId,
        success: function(data) {
			console.log(data);
			
			var hiredEmployees = [];
			
			for (var key in data) {
				if (key === "hiredEmployees") {
					var employees = data[key];
					for(var employee in employees) {
						console.log(employees[employee]);
						var employeeData = {
							id: employees[employee].id,
							name: employees[employee].name,
							perfomance: employees[employee].perfomance,
						}
						hiredEmployees.push(employeeData);
						oldHiredEmployeesId.push(employees[employee].id);
					}
				}
			}
			
			var freeEmployees = [];
			
			for (var key in data) {
				if (key === "freeEmployees") {
					var employees = data[key];
					for(var employee in employees) {
						console.log(employees[employee]);
						var employeeData = {
							id: employees[employee].id,
							name: employees[employee].name,
							perfomance: employees[employee].perfomance,
						}
						freeEmployees.push(employeeData);
						oldFreeEmployeesId.push(employees[employee].id);
					}
				}
			}
			
			fillHiredEmployeesTable(hiredEmployees);
			fillFreeEmployeesTable(freeEmployees);
		}
      });
}

function fillHiredEmployeesTable(hiredEmployees) {
	for (var hiredEmployee in hiredEmployees) {
		appendEmployeeToHiredTable(hiredEmployees[hiredEmployee]);
	}
	refreshContractTeamEventHandlers();
}	

function fillFreeEmployeesTable(freeEmployees) {
	for (var freeEmployee in freeEmployees) {
		appendEmployeeToFreeTable(freeEmployees[freeEmployee]);
	}
	refreshContractTeamEventHandlers();
}

function hireEmployeeToContract() {
	var parent = $(this).closest("tr");
	var contractId = parent.attr("id");
	
	var selector = "#" + contractId;
	
	var employeeData = {
			id: contractId.substring(6),
			name: $(selector).find(".f_emp_name").text(),
			perfomance: $(selector).find(".f_emp_perfomance").text()
	}
	parent.remove();
	appendEmployeeToHiredTable(employeeData);
	
	refreshContractTeamEventHandlers();
}

function makeEmployeeFree() {
	var parent = $(this).closest("tr");
	var contractId = parent.attr("id");
	
	var selector = "#" + contractId;
	
	var employeeData = {
			id: contractId.substring(6),
			name: $(selector).find(".h_emp_name").text(),
			perfomance: $(selector).find(".h_emp_perfomance").text()
	}
	parent.remove();
	appendEmployeeToFreeTable(employeeData);
	refreshContractTeamEventHandlers();
}

function appendEmployeeToHiredTable(employeeData) {
	var str = "<tr id='h_emp_" + employeeData.id + "' class = \"contract_emp_tr\">";
	str += "<td><a class=\"h_emp_name\">" + employeeData.name + "</a></td>";
	str += "<td class=\"h_emp_perfomance\">" + employeeData.perfomance + "</td>";
	str += "<td class=\"h_emp_button\"><input  type=\"button\" class = \"make_employee_free\" value=\"Remove\"></td>";

	var placeholder = $('#contract_hired_employees_table').find(".table_placeholder");
	placeholder.before(str);
}

function appendEmployeeToFreeTable(employeeData) {
	var str = "<tr id='f_emp_" + employeeData.id + "' class=\"contract_emp_tr\">";
	str += "<td><a class=\"f_emp_name\">" + employeeData.name + "</a></td>";
	str += "<td class=\"f_emp_perfomance\">" + employeeData.perfomance + "</td>";
	str += "<td class=\"f_emp_button\"><input  type=\"button\" class = \"hire_employee_to_contract\" value=\"Hire\"></td>";

	var placeholder = $('#contract_free_employees_table').find(".table_placeholder");
	placeholder.before(str);
}

function addContractToTable(contractData) {
	var str = "<tr id='contract_" + contractData.id + "'>";
	str += "<td><a class=\"contract_name\">" + contractData.name + "</a></td>";
	str += "<td class=\"contract_size\">" + contractData.size + "</td>";
	str += "<td class=\"contract_fee\">" + contractData.fee + "</td>";
	str += "<td class=\"contract_progress\">" + contractData.progress + "</td>";
	str += "<td class=\"contract_perfomance\">" + contractData.perfomance + "</td>";
	str += "<td class=\"contract_expected\">" + contractData.expected + "</td>";
	str += "<td class=\"contract_deadline\">" + contractData.deadline + "</td>";
	str += "<td class=\"contract_team\"><input  type=\"button\" class = \"change_contract_team\" value=\"Change team\"></td>";
	str += "</tr>";
	
	$('#contracts_table').append(str);
	tempId++;
	
	refreshContractsEventHandlers();
}

