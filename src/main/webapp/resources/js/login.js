$(function() {
    $('#login').submit(function(e) {
      var $form = $(this);
      var userLogin = $form.find($('#login_email')).val();
      var userPassword = $form.find($('#password')).val();

      var userData = {
    	loginEmail: userLogin || "",
        password: userPassword || ""
        }
      
      $.ajax({
        type: "POST",
        url: "/company-simulator/sign-in",
        contentType: 'application/json',
        data: JSON.stringify(userData),
        success: function(data) {
			console.log(data);
			if (data.status == 0) {
				setCookie('signedUser', data.login, {'max-age': 3600, path: '/'});
				document.location.href = "company";
			}
		}
      });
      //отмена действия по умолчанию для кнопки submit
      e.preventDefault(); 
    });
    
    function setCookie(name, value, options = {}) {
//    	  if (options.expires.toUTCString) {
//    	    options.expires = options.expires.toUTCString();
//    	  }

    	  let updatedCookie = encodeURIComponent(name) + "=" + encodeURIComponent(value);

    	  for (let optionKey in options) {
    	    updatedCookie += "; " + optionKey;
    	    let optionValue = options[optionKey];
    	    if (optionValue !== true) {
    	      updatedCookie += "=" + optionValue;
    	    }
    	  }

    	  document.cookie = updatedCookie;
    	}
 });