$(document).ready(function() {
	// requestReplaysAjax();

	$('#apply_employee_data').on('click', sortReplays);
});

var changedEmployeeID = 0;

function applyEmployeeData() {
    var newEmployee = {
        name = $('#input_employee_name').val(),
        age = $('#input_employee_age').val(),
        perfomance =  $('#input_employee_perf').val(),
        salary = $('#input_employee_salary').val(),
        sex = $('#choose_employee_sex').val()
    }

    if (changedEmployeeID === 0)
        addNewEmployee(newEmployee);
    else
        changeEnployeeData();
}

function addNewEmployee(employeeData) {
    var str = "<tr>";
		str += "<td>" + employeeData.name + "</td>";
		str += "<td>" + employeeData.age + "</td>";
		str += "<td>" + employeeData.perfomance + "</td>";
		str += "<td>" + employeeData.salary + "</td>";
		str += "<td>" + employeeData.sex + "</td>";
	str += "</tr>";

	$('.table_tanks_body').append(str);
}

function changeEnployeeData() {

}
