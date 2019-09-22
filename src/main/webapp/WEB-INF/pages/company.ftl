<html>
<head>
	<meta charset="utf-8">
	<link rel="stylesheet" href="./resources/css/companyStyle.css"/>
	<script src="./resources/js/jquery-3.3.1.js" type="text/javascript"></script>
    <script src="./resources/js/employees.js" type="text/javascript"></script>
    <title>Горизонтальное выпадающее меню</title>
</head>
<body>
	<div id="container">
	    <div id="header">
	    	<table id="header_table">
				  <tr>
				    <td id="company_name"><h2>Company</h2></td>
				    <td id="user_indicator"><h3>Nickname</h3></td>
				  </tr>
			</table>
		</div>      
		<div id="app_body">
	    	<table id="app_table">
				  <tr>
				    <td id="company_menu">
				    	<ol class="rectangle">
						  <li><a href="#">Main descrption</a></li>
						  <li><a href="#">Employees</a></li>
						  <li><a href="#">Contracts</a></li>
						</ol>
				    </td>
				    <td id="game_window">
				    	<div id="tab_content">
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
								<input type="button" id="hire_employee" value="Hire employee">
								<input type="button" id="create_new_employee" value="Create new employee">
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
				    	</div>
				    </td>
				  </tr>
			</table>
		</div>    
	</body>
</html>