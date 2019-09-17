$(document).ready(function() {
	// requestReplaysAjax();

    $('#create_new_employee').on('click', newEmployeeOpenModal);
    $('#apply_employee_data').on('click', applyEmployeeData);
    $('.employee_name').on('click', changeEmployeeOpenModal);
});

var changedEmployeeID = 0;
var tempId = 4;

function refreshEventHandlers() {
    $('#create_new_employee').off('click');
    $('#apply_employee_data').off('click');
    $('.employee_name').off('click');

    $('#create_new_employee').on('click', newEmployeeOpenModal);
    $('#apply_employee_data').on('click', applyEmployeeData);
    $('.employee_name').on('click', changeEmployeeOpenModal);
}

function changeEmployeeOpenModal() {
    var parent = $(this).closest("tr");
    changedEmployeeID = parent.attr("id");

    var newEmployee = {
        name: parent.find(".employee_name").text() || "",
        age : parent.find(".employee_age").text() || "",
        perfomance : parent.find(".employee_perf").text() || "0",
        salary : parent.find(".employee_salary").text() || "0",
        sex : parent.find(".employee_sex").text()
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
    var str = "<tr id='emp_" + tempId + "'>";
		str += "<td><a class=\"employee_name\">" + employeeData.name + "</a></td>";
		str += "<td class=\"employee_age\">" + employeeData.age + "</td>";
		str += "<td class=\"employee_perf\">" + employeeData.perfomance + "</td>";
		str += "<td class=\"employee_salary\">" + employeeData.salary + "</td>";
		str += "<td class=\"employee_sex\">" + employeeData.sex + "</td>";
    str += "</tr>";

    $('#employees_table').append(str);
    tempId++;

    refreshEventHandlers();
}

function changeEnployeeData() {
    console.log("changeExistEmployee");
}
