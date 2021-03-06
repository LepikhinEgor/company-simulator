function employeesPageSetup() {

	refreshEmpEventHandlers();
	requestEmployeesList();
}


var changedEmployeeID = 0;
var tempId = 4;
var generatedEmployeesId = [];
var hiredGeneratedEmployeesId = [];

function refreshEmpEventHandlers() {
    $('#create_new_employee').off('click');
    $('#hire_employee').off('click');
    $('#apply_employee_data').off('click');
    $('.employee_name').off('click');
    $('.close_modal_generated_employees').off('click');
    $('.hire_generated_employee').off('click');
    $('.close_modal_generated_employees_apply').off('click');

    $('#create_new_employee').on('click', newEmployeeOpenModal);
    $('#hire_employee').on('click', requestGeneratedEmployees);
    $('#apply_employee_data').on('click', applyEmployeeData);
    $('.employee_name').on('click', changeEmployeeOpenModal);
    $('.close_modal_generated_employees').on('click', clearGeneratedEmployeesTable);
    $('.hire_generated_employee').on('click', hireGeneratedEmployee);
    $('.close_modal_generated_employees_apply').on('click', requestHireGeneratedEmployees);
}

function requestGeneratedEmployees() {
	$.ajax({
        type: "GET",
        url: "/company-simulator/company/hr/get-generated-employees",
        success: function(data) {
			console.log(data);
			generatedEmployeesOpenModal(data.employees);
		}
     });
}

function generatedEmployeesOpenModal(employees) {
	for (var employee in employees) {
		generatedEmployeesId.push(employees[employee].id);
		var newTdStr = "<tr id=\"gen_emp_" + employees[employee].id + "\">" + 
		 	"<td class='generated_employee_name'>" + employees[employee].name + "</td>" + 
		 	"<td>" + employees[employee].age + "</td>" +
		 	"<td>" + employees[employee].perfomance + "</td>" + 
		 	"<td>" + employees[employee].salary + "</td>" +
		 	"<td class='hire_gen_employee_td'><input  type=\"button\" class = \"hire_generated_employee\" value=\"Hire\"></td>" +
		"</tr>";
		var placeholder = $('#generated_employees_table').find(".table_placeholder");
		placeholder.before(newTdStr);
		
		refreshEmpEventHandlers();
		
		document.location.href = "#generated_employees_modal_window";
	}
}

function requestEmployeesList() {
	var requestData = {
		orderNum: 0,
		pageNum: 0
    }

	$.ajax({
        type: "POST",
        url: "/company-simulator/company/hr/get-employees",
        contentType: 'application/json',
        data: JSON.stringify(requestData),
        success: function(data) {
			console.log(data);
			
			for (var key in data) {
				if (key === "employees") {
					var employees = data[key];
					for(var employee in employees) {
						console.log(employees[employee]);
						addEmployeeToTable(employees[employee]);
					}
				}
			}
		}
      });
}

function requestCreateEmployee(employeeData) {
	var status = 0;
	$.ajax({
        type: "POST",
        url: "/company-simulator/company/hr/create-employee",
        contentType: 'application/json',
        data: JSON.stringify(employeeData),
        success: function(data) {
			console.log(data);
		}
      });
	return status;
}

function requestUpdateEmployee(employeeData) {
	var status = 0;
	employeeData.id = changedEmployeeID.substring(4);
	$.ajax({
        type: "POST",
        url: "/company-simulator/company/hr/update-employee",
        contentType: 'application/json',
        data: JSON.stringify(employeeData),
        success: function(data) {
			console.log(data);
		}
      });
	return status;
}

function changeEmployeeOpenModal() {
    var parent = $(this).closest("tr");
    changedEmployeeID = parent.attr("id");

    var newEmployee = {
        name: parent.find(".employee_name").text() || "",
        age : parent.find(".employee_age").text() || "",
        perfomance : parent.find(".employee_perf").text() || "0",
        salary : parent.find(".employee_salary").text() || "0",
        sex : parent.find(".employee_sex").text() ||""
    }
    openEmployeeModalWindow(newEmployee);
}

function newEmployeeOpenModal() {
    changedEmployeeID = "newUser";

    var newEmployee = {
        name: "",
        age : "",
        perfomance : "",
        salary :  "",
        sex : ""
    }

    openEmployeeModalWindow(newEmployee);
}

function openEmployeeModalWindow(employeeData) {
    var sexVal = $("input[name=choose_employee_sex]").filter(":checked").val();

    $('.input_employee_name').val(employeeData.name || "");
    $('.input_employee_age').val(employeeData.age || "");
    $('.input_employee_perf').val(employeeData.perfomance || "");
    $('.input_employee_salary').val(employeeData.salary || "");

    document.location.href = "#employee_modal_window";
}

function applyEmployeeData() {
    var sexVal = $("input[name=choose_employee_sex]").filter(":checked").val();
    var newEmployee = {
        name: $('.input_employee_name').val() || "",
        age : $('.input_employee_age').val() || "",
        perfomance : $('.input_employee_perf').val() || "0",
        salary : $('.input_employee_salary').val() || "0",
        sex : sexVal || "undef"
    }

    if (changedEmployeeID === "newUser")
        addNewEmployee(newEmployee);
    else
        changeEnployeeData(newEmployee);
    
    document.location.href = "#";
}

function addNewEmployee(employeeData) {
	var status = requestCreateEmployee(employeeData);
	
	addEmployeeToTable(employeeData);
}

function addEmployeeToTable(employeeData) {
	var str = "<tr id='emp_" + employeeData.id + "'>";
	str += "<td><a class=\"employee_name\">" + employeeData.name + "</a></td>";
	str += "<td class=\"employee_age\">" + employeeData.age + "</td>";
	str += "<td class=\"employee_perf\">" + employeeData.perfomance + "</td>";
	str += "<td class=\"employee_salary\">" + employeeData.salary + "</td>";
	str += "<td class=\"employee_sex\">" + employeeData.sex + "</td>";
	str += "</tr>";
	
	$('#employees_table').append(str);
	tempId++;
	
	refreshEmpEventHandlers();
}

function changeEnployeeData(employeeData) {
	requestUpdateEmployee(employeeData);
	
    var empSelector = "#" + changedEmployeeID;
    $(empSelector).find(".employee_name").text(employeeData.name);
    $(empSelector).find(".employee_age").text(employeeData.age);
    $(empSelector).find(".employee_perf").text(employeeData.perfomance);
    $(empSelector).find(".employee_salary").text(employeeData.salary);
    $(empSelector).find(".employee_sex").text(employeeData.sex);
}

function clearGeneratedEmployeesTable() {
	$('#generated_employees_table tr[id]').remove();
	generatedEmployeesId = [];
	hiredGeneratedEmployeesId = [];
	
	document.location.href = "#";
}

function requestHireGeneratedEmployees() {
	$.ajax({
        type: "POST",
        url: "/company-simulator/company/hr/hire-generated-employees",
        contentType: 'application/json',
        data: JSON.stringify(hiredGeneratedEmployeesId),
        success: function(data) {
			console.log(data);
			
			if (data.status == 0) {
				document.location.href = "#";
				clearGeneratedEmployeesTable(); 
			}
		}
      });
}

function hireGeneratedEmployee() {
	console.log('hire');
	var parent = $(this).closest("tr");
    changedEmployeeID = parent.attr("id");
    var digitId = changedEmployeeID.substring(8);
    
    hiredGeneratedEmployeesId.push(digitId);
    parent.find('.hire_gen_employee_td').html('Hired');
}

function getCookie(name) {
	  let matches = document.cookie.match(new RegExp(
	    "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
	  ));
	  return matches ? decodeURIComponent(matches[1]) : undefined;
}

