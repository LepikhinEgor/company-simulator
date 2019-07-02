$(function() {
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
        data: JSON.stringify(newUser)
      }).done(function() {
        console.log('success');
      }).fail(function() {
        console.log('fail');
      });
      //отмена действия по умолчанию для кнопки submit
      e.preventDefault(); 
    });
 });