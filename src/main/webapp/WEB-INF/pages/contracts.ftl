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