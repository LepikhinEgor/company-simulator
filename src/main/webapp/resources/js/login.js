$(function() {
    $('#login').submit(function(e) {
      var $form = $(this);
      var userEmail = $form.find($('#email')).val();
      var userLogin = $form.find($('#user_login')).val();
      var userPassword = $form.find($('#password')).val();
      var userConfirm_password = $form.find($('#confirm_password')).val();

      
      var newUser = {
          email: userEmail || "",
          login: userLogin || "",
          password: userPassword || ""
        }
        console.log(newUser);
      $.ajax({
        type: $form.attr('method'),
        url: $form.attr('action'),
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