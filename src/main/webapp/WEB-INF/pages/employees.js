$(document).ready(function() {
	// requestReplaysAjax();

    $('#create_new_employee').on('click', openEmployeeModalWindow);
    $('#apply_employee_data').on('click', applyEmployeeData);
});

var changedEmployeeID = 0;

function openEmployeeModalWindow() {;
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

    if (changedEmployeeID === 0)
        addNewEmployee(newEmployee);
    else
        changeEnployeeData();
    
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

}
