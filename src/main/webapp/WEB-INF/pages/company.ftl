<html>
<head>
	<meta charset="utf-8">
	<link rel="stylesheet" href="./resources/css/companyStyle.css"/>
	<script src="./resources/js/jquery-3.3.1.js" type="text/javascript"></script>
	<script src="./resources/js/company.js" type="text/javascript"></script>
	<script src="./resources/js/stats.js" type="text/javascript"></script>
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
						  <li><a id="get_company_info">Information</a></li>
						  <li><a id="get_hr">Employees</a></li>
						  <li><a id="get_contracts">Contracts</a></li>
						</ol>
				    </td>
				    <td id="game_window">
				    	<div id="tab_content">
				    	</div>
				    </td>
				  </tr>
			</table>
		</div>    
	</body>
</html>