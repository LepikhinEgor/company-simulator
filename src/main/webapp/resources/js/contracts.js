function contractsPageSetup() {
	refreshContractsEventHandlers();
//	requestEmployeesList();
}

var changedContractId;

function refreshContractsEventHandlers() {
    $('#create_new_contract').off('click');
    $('#apply_contract_data').off('click');
    $('.contract_name').off('click');

    $('#create_new_contract').on('click', newContractOpenModal);
    $('#apply_contract_data').on('click', applyContractData);
    $('.contract_name').on('click', changeContractOpenModal);
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
//	requestUpdateEmployee(employeeData);
	
    var selector = "#" + changedContractId;
    $(selector).find(".contract_name").text(contractData.name);
    $(selector).find(".contract_size").text(contractData.size);
    $(selector).find(".contract_fee").text(contractData.fee);
    $(selector).find(".contract_deadline").text(contractData.deadline);
}

function addNewContract(newContract) {
	var status = requestCreateContract(newContract);
	if (status == 1) {
		newContract.progress = 0;
		newContract.perfomance = 0;
		newContract.expected = 0;
		
		addContractToTable(newContract);
	}
}

function requestCreateContract(newContract) {
	var contractData =  {
			name : "f5f5",
			size : 45,
			fee : 53,
			deadline : 34
			
	};
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

function addContractToTable(contractData) {
	var str = "<tr id='contract_" + contractData.id + "'>";
	str += "<td><a class=\"contract_name\">" + contractData.name + "</a></td>";
	str += "<td class=\"contract_size\">" + contractData.size + "</td>";
	str += "<td class=\"contract_fee\">" + contractData.fee + "</td>";
	str += "<td class=\"contract_progress\">" + contractData.progress + "</td>";
	str += "<td class=\"contract_perfomance\">" + contractData.perfomance + "</td>";
	str += "<td class=\"contract_expected\">" + contractData.expected + "</td>";
	str += "<td class=\"contract_deadline\">" + contractData.deadline + "</td>";
	str += "</tr>";
	
	$('#contracts_table').append(str);
	tempId++;
	
	refreshContractsEventHandlers();
}

