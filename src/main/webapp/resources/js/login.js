$(function() {
	var loginUpdateTimer;
	$("#user_login").focus(function() {
		checkLoginUpdate();
		});
	$("#user_login").blur(function() {
		clearInterval(loginUpdateTimer);
		});
	
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
			if (data.status == 1)
				document.location.href = "login";
		}
      });
      //отмена действия по умолчанию для кнопки submit
      e.preventDefault(); 
    });
    
    function checkLoginUpdate() {
    	var $form = $(this);
    	var newUserLogin = $('#user_login').val();
    	var oldUserLogin = newUserLogin;
    	loginUpdateTimer = setInterval(function() {
    		newUserLogin = $('#user_login').val();
    		if (newUserLogin != oldUserLogin) {
    			checkLoginExist(newUserLogin);
    			oldUserLogin = newUserLogin;
    		}
    		}, 1000);
    }
    
    function checkLoginExist(loginVal) {
    	var requestStr = "/company-simulator/checkLoginExist?login=" + loginVal;
    	$.ajax({
            type: "GET",
            url: requestStr,
            success: function(data) {
    			console.log(data);

    		}
          });
    }
 });