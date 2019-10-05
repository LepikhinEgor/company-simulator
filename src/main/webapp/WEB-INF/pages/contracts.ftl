<table id="contracts_table">
	<tr>
	  <th>Name</th>
	  <th>Size</th>
	  <th>Fee</th>
	  <th>Progress</th>
	  <th>Perfomance/h</th>
	  <th>Expected</th>
	  <th>Deadline</th>
	</tr>
</table>
<form id="contracts_control">
	<input type="button" id="contract_employees" value="Eemployees">
	<input type="button" id="create_new_contract" value="Create new contract">
</form>
<div id="contract_modal_window">
  <div id="contract_window">
    <div id="contract_window_content">
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
    			<input type="button" id="apply_contract_data" value="Apply">
    		</p>
    	</form>
    </div>
    <a href="#" class="close_modal_contract">Закрыть окно</a>
  </div>
</div>