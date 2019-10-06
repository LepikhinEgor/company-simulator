function contractsPageSetup() {
	refreshContractsEventHandlers();
//	requestEmployeesList();
}

function refreshContractsEventHandlers() {
    $('#create_new_contract').off('click');
    $('#apply_contract_data').off('click');
//    $('.employee_name').off('click');

    $('#create_new_contract').on('click', newContractOpenModal);
    $('#apply_contract_data').on('click', applyContractData);
//    $('.employee_name').on('click', changeEmployeeOpenModal);
}

function newContractOpenModal() {
    changedEmployeeID = "newUser";

    var newContract = {
        name: "",
        size : "",
        fee : "",
        deadline :  "",
    }

    openContractModalWindow(newContract);
}

function openContractModalWindow(employeeData) {
    var sexVal = $("input[name=choose_employee_sex]").filter(":checked").val();

    $('.input_contract_name').val(employeeData.name || "");
    $('.input_contract_size').val(employeeData.size || "");
    $('.input_contract_fee').val(employeeData.fee || "");
    $('.input_contract_deadline').val(employeeData.deadline || "");

    document.location.href = "#contract_modal_window";
}

function applyContractData() {
    var newContract = {
        name: $('.input_contract_name').val() || "",
        size : $('.input_contract_size').val() || "",
        fee : $('.input_contract_fee').val() || "0",
        deadline : $('.input_contract_deadline').val() || "0",
    }

    if (changedEmployeeID === "newUser")
        addNewContract(newContract);
    else
        changeEnployeeData(newEmployee);
    
    document.location.href = "#";
}

function addNewContract(newContract) {
	newContract.progress = 0;
	newContract.perfomance = 0;
	newContract.expected = 0;
	addContractToTable(newContract);
}

function addContractToTable(contractData) {
	var str = "<tr id='contract_" + contractData.id + "'>";
	str += "<td><a class=\"contract_name\">" + contractData.name + "</a></td>";
	str += "<td class=\"contract_size\">" + contractData.size + "</td>";
	str += "<td class=\"contract_fee\">" + contractData.fee + "</td>";
	str += "<td class=\"contract_progress\">" + contractData.progress + "</td>";
	str += "<td class=\"contract_perfomance\">" + contractData.perfomance + "</td>";
	str += "<td class=\"contract_expected\">" + contractData.expected + "</td>";
	str += "<td class=\contract_deadline\">" + contractData.deadline + "</td>";
	str += "</tr>";
	
	$('#contracts_table').append(str);
	tempId++;
	
	refreshEmpEventHandlers();
}

