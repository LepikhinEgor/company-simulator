$(document).ready(function() {
	// requestReplaysAjax();

    $('#create_new_employee').on('click', newEmployeeOpenModal);
    $('#apply_employee_data').on('click', applyEmployeeData);
    $('.employee_name').on('click', changeEmployeeOpenModal);
});

var changedEmployeeID = 0;

function changeEmployeeOpenModal(e) {
    changedEmployeeID = $(this).attr("id");
    openEmployeeModalWindow();
}

function newEmployeeOpenModal(e) {
    changedEmployeeID = "newUser";
    openEmployeeModalWindow();
}

function openEmployeeModalWindow() {
    var sexVal = $("input[name=choose_employee_sex]").filter(":checked").val();
    $('.input_employee_name').val(""),
    $('.input_employee_age').val(""),
    $('.input_employee_perf').val(""),
    $('.input_employee_salary').val(""),

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
    console.log("employeeData");
    var str = "<tr>";
		str += "<td>" + employeeData.name + "</td>";
		str += "<td>" + employeeData.age + "</td>";
		str += "<td>" + employeeData.perfomance + "</td>";
		str += "<td>" + employeeData.salary + "</td>";
		str += "<td>" + employeeData.sex + "</td>";
	str += "</tr>";

	$('#employees_table').append(str);
}

function changeEnployeeData() {
    console.log("changeExistEmployee");
}
