<table id="employees_table">
	<tr>
	  <th>Name</th>
	  <th>Age</th>
	  <th>Perfomance</th>
	  <th>Salary</th>
	  <th>Sex</th>
	</tr>
</table>
<form id="employees_control">
	<center><input type="button" id="hire_employee" value="Hire employee"></center>
</form>
<div id="employee_modal_window">
  <div id="employee_window">
    <div id="employee_window_content">
    	<form>
    		<p>
    			<span>Name:</span>
    			<input type="text" class="input_employee_name"></p>
    		<p>
    			<span>Age:</span>
    			<input type="text" class="input_employee_age">
    		</p>
    		<p>
    			<span>Perfomance:</span>
    			<input type="text" class="input_employee_perf">
    		</p>
    		<p>
    			<span>Salary:</span>
    			<input type="text" class="input_employee_salary">
    		</p>
    		<p>
    			<span>Sex:</span>
    			<input type="radio" name="choose_employee_sex" value="male"><label>male</label>
    			<input type="radio" name="choose_employee_sex" value="female"><label>female</label>
			</p>
			<p>
    			<input type="button" id="apply_employee_data" value="Apply">
    		</p>
    	</form>
    </div>
    <a href="#" class="close_modal_employee">Закрыть окно</a>
  </div>
</div>
<div id="generated_employees_modal_window">
  <div id="generated_employees_window">
    <div id="generated_employees_window_content">
    	<p><h3>Employees wants job</h3></p>
    	<div id="generated_employees_change_block">
	    	<table id="generated_employees_table">
				<tr>
				  <th class='generated_employee_name'>Name</th>
				  <th>Age</th>
				  <th>Perfomance</th>
				  <th>Salary</th>
				  <th>Hire</th>
				</tr>
				<tr class="table_placeholder">
				  <td></td>
				  <td></td>
				  <td></td>
				  <td></td>
				  <td></td>
				</tr>
			</table>
		</div>
    </div>
    <a class="close_modal_generated_employees">Отмена</a>
    <a class="close_modal_generated_employees_apply">Принять</a>
  </div>
</div>