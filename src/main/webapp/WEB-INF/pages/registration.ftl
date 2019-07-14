<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>22 лучших формы входа и регистрации | Vladmaxi.net</title>
	<link rel="icon" href="http://vladmaxi.net/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="./resources/css/loginStyle.css"/>
    <script src="./resources/js/jquery-3.3.1.js" type="text/javascript"></script>
    <script src="./resources/js/registration.js" type="text/javascript"></script>
</head>
<body>

<!-- vladmaxi top bar -->
    <div class="vladmaxi-top">
        <a href="http://vladmaxi.net" target="_blank">Главная Vladmaxi.net</a>
        <span class="right">
        <a href="http://vladmaxi.net/web-developer/css/22-luchshix-formy-vxoda-i-registracii-na-sajte-v-html-css.html">
                <strong>Вернуться назад к статье</strong>
            </a>
        </span>
    <div class="clr"></div>
    </div>
<!--/ vladmaxi top bar -->

<form id="login" action="/receiveNewUser" method="post">
    <h1>Регистрация</h1>
    <fieldset id="inputs">
        <input id="email" type="text" placeholder="Потча" autofocus required>
        <input id="user_login" type="text" placeholder="Логин" required>   
        <input id="password" type="password" placeholder="Пароль" required>
        <input id="confirm_password" type="password" placeholder="Повторите пароль" required>
    </fieldset>
    <fieldset id="actions">
        <input type="submit" id="submit" value="ВОЙТИ">
        <a href="">Забыли пароль?</a><a href="registration">Регистрация</a>
    </fieldset>
</form>
</body>
</html>
