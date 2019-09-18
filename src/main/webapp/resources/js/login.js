$(function() {
    $('#login').submit(function(e) {
      var $form = $(this);
      var userLogin = $form.find($('#login_email')).val();
      var userPassword = $form.find($('#password')).val();

      var userData = {
    	loginEmail: userLogin || "",
        password: userPassword || ""
        }
        console.log(userData);
      $.ajax({
        type: "POST",
        url: "/company-simulator/sign-in",
        contentType: 'application/json',
        data: JSON.stringify(userData),
        success: function(data) {
			console.log(data);
			if (data.status == 0)
				document.location.href = "company?loginEmail="+userLogin;
		}
      });
      //отмена действия по умолчанию для кнопки submit
      e.preventDefault(); 
    });
 });