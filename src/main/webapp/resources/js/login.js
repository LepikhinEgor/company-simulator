$(function() {
	$("#login").focus(function() {
		console.log("on focus");
		checkLoginUpdate();
		});
	$("#login").blur(function() {
		console.log("on blur");
		clearInterval(loginUpdateTimer);
		});
	loginUpdateTimer
	
	var loginUpdateTimer;
	
    $('#login').submit(function(e) {
      var $form = $(this);
      var userEmail = $form.find($('#email')).val();
      var userLogin = $form.find($('#user_login')).val();
      var userPassword = $form.find($('#password')).val();
      var userConfirm_password = $form.find($('#confirm_password')).val();

      
      var newUser = {
          login: userLogin || "",
          password: userPassword || "",
          email: userEmail || ""
        }
        console.log(newUser);
      $.ajax({
        type: "POST",
        url: "/company-simulator/receiveNewUser",
        contentType: 'application/json',
        data: JSON.stringify(newUser),
        success: function(data) {
			console.log(data);
		}
      });
      //отмена действия по умолчанию для кнопки submit
      e.preventDefault(); 
    });
    
    function checkLoginUpdate() {
    	var newUserLogin = $form.find($('#user_login')).val();
    	var oldUserLogin = newUserLogin;
    	loginUpdateTimer = setInterval(function() {
    		console.log("update");
    		newUserLogin = $form.find($('#user_login')).val();
    		if (newUserLogin != oldUserLogin) {
    			checkLoginExist(newUserLogin);
    		}
    		}, 1000);
    }
    
    function checkLoginExist(loginVal) {
    	var requestStr = "/company-simulator/checkLoginExist/login=" + loginVal;
    	$.ajax({
            type: "POST",
            url: requestStr,
            contentType: 'application/json',
            data: JSON.stringify(newUser),
            success: function(data) {
    			console.log(data);
    		}
          });
    }
 });