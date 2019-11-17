<table id="contracts_table">
	<tr>
	  <th>Name</th>
	  <th>Size</th>
	  <th>Fee</th>
	  <th>Progress</th>
	  <th>Perfomance/h</th>
	  <th>Expected</th>
	  <th>Deadline</th>
	  <th>Team</th>
	</tr>
</table>
<form id="contracts_control">
	<input type="button" id="get_generated_contracts" value="Get contract">
	<input type="button" id="create_new_contract" value="Create new contract">
</form>
<table id="completed_contracts_table">
	<tr>
	  <th>Name</th>
	  <th>Fee</th>
	  <th>Progress</th>
	  <th>Get</th>
	</tr>
</table>
<div id="contract_modal_window">
  <div id="contract_window">
    <div id="contract_window_content">
    	<form>
    		<p>
    			<span>Name:</span>
    			<input type="text" class="input_contract_name"></p>
    		<p>
    			<span>Size:</span>
    			<input type="text" class="input_contract_size">
    		</p>
    		<p>
    			<span>Fee:</span>
    			<input type="text" class="input_contract_fee">
    		</p>
    		<p>
    			<span>Deadline:</span>
    			<input type="text" class="input_contract_deadline">
    		</p>
			<p>
    			<input type="button" id="apply_contract_data" value="Apply">
    		</p>
    	</form>
    </div>
    <a href="#" class="close_modal_contract">Закрыть окно</a>
  </div>
</div>
<div id="contract_team_modal_window">
  <div id="contract_team_window">
    <div id="contract_team_window_content">
    	<p><h3>Hired employees</h3></p>
    	<div id="contract_employees_change_block">
	    	<table id="contract_hired_employees_table">
				<tr>
				  <th>Name</th>
				  <th>Perfomance</th>
				  <th>Remove</th>
				</tr>
				<tr class="table_placeholder">
				  <td></td>
				  <td></td>
				  <td></td>
				</tr>
			</table>
		</div>
		<p><h3>Free employees</h3></p>
    	<div id="contract_employees_change_block">
	    	<table id="contract_free_employees_table">
				<tr>
				  <th>Name</th>
				  <th>Perfomance</th>
				  <th>Hire</th>
				</tr>
				<tr class="table_placeholder">
				  <td></td>
				  <td></td>
				  <td></td>
				</tr>
			</table>
		</div>
    </div>
    <a href="#" class="close_modal_contract_team">Отмена</a>
    <a href="#" class="close_modal_contract_team_apply">Принять</a>
  </div>
</div>
<div id="generated_contracts_modal_window">
  <div id="generated_contracts_window">
    <div id="generated_contracts_window_content">
    	<p><h3>Hired employees</h3></p>
    	<div id="generated_contracts_change_block">
	    	<table id="generated_contracts_table">
				<tr>
				  <th>Name</th>
				  <th>Size</th>
				  <th>Fee</th>
				  <th>Deadline</th>
			  	  <th>Get</th>
				</tr>
				<tr class="table_placeholder">
				  <td></td>
				  <td></td>
				  <td></td>
  				  <td></td>
				</tr>
			</table>
		</div>
    </div>
    <a href="#" class="close_modal_generated_contracts">Отмена</a>
    <a class="close_modal_generated_contracts_apply">Принять</a>
  </div>
</div>