package controller;

import java.sql.SQLException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.input.NewUserData;
import controller.messages.RegistrationMessage;
import controller.input.SignInData;
import controller.messages.SignInMessage;
import entities.User;
import exceptions.DatabaseAccessException;
import exceptions.EmailAlreadyExistException;
import exceptions.InvalidEmailRegistrationException;
import exceptions.InvalidLoginRegistrationException;
import exceptions.InvalidPasswordRegistrationException;
import exceptions.InvalidSignInLoginEmail;
import exceptions.InvalidSignInPasswordException;
import exceptions.LoginAlreadyExistException;
import exceptions.NotRecordToDBException;
import services.UserService;
/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private UserService userService;
	

	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	@ResponseBody
	public String home() {
		return "login";
	}
	
	@RequestMapping(value = "/sign-in", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public SignInMessage userSignIn(@RequestBody SignInData signInData) {
		
		User signedUser = null;
		boolean signInSuccess = false;
	
		try {
			signedUser = userService.userSignIn(signInData);
			
			if (signedUser != null) {
				signInSuccess = true;
			}
		} catch (InvalidSignInPasswordException e) {
			logger.error("Invalid password", e);
			return new SignInMessage(SignInMessage.INVALID_PASSWORD);
		} catch (InvalidSignInLoginEmail e) {
			logger.error("Invalid login or email", e);
			return new SignInMessage(SignInMessage.INVALID_LOGIN);
		} catch (DatabaseAccessException e) {
			logger.error("Sign in error", e);
			return new SignInMessage(SignInMessage.FAIL);
		}
		
		if (signInSuccess) {
			logger.info("User sign in successful");
			return new SignInMessage(SignInMessage.SUCCESS, signedUser.getLogin());
		} else {
			logger.info("Invalid password");
			return new SignInMessage(SignInMessage.INCORRECT_LOGIN_OR_PASSWORD);
		}
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration() {
		return "registration";
	}
	
	@RequestMapping(value = "/receiveNewUser", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public RegistrationMessage receiveNewUser(@RequestBody NewUserData newUserData) {
		logger.info(newUserData.toString());
		
		try {
			userService.createNewUser(newUserData);
			return new RegistrationMessage(RegistrationMessage.SUCCESS);
		} catch (InvalidLoginRegistrationException e) {
			return new RegistrationMessage(RegistrationMessage.INCORRECT_LOGIN);
		} catch (LoginAlreadyExistException e) {
			return new RegistrationMessage(RegistrationMessage.LOGIN_ALREADY_EXIST);
		} catch (EmailAlreadyExistException e) {
			return new RegistrationMessage(RegistrationMessage.EMAIL_ALREADY_EXIST);
		} catch (DatabaseAccessException e) {
			return new RegistrationMessage(RegistrationMessage.LOGIN_ALREADY_EXIST);
		} catch (InvalidEmailRegistrationException e) {
			return new RegistrationMessage(RegistrationMessage.INCORRECT_EMAIL);
		} catch (InvalidPasswordRegistrationException e) {
			return new RegistrationMessage(RegistrationMessage.INCORRECT_PASSWORD);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return new RegistrationMessage(RegistrationMessage.FAIL);
		}
		
	}
	
	@RequestMapping(value="/checkLoginExist", method = RequestMethod.GET)
	@ResponseBody
	public RegistrationMessage checkLoginExist(@RequestParam String login) {
		
		boolean loginExist = false;
		try {
			loginExist = userService.checkUserLoginAlreadyExist(login);
		} catch (DatabaseAccessException e) {
			logger.error(e.getMessage(), e);
		}
		
		if (loginExist) {
			return new RegistrationMessage(RegistrationMessage.LOGIN_ALREADY_EXIST);
		} 
		
		return new RegistrationMessage(RegistrationMessage.LOGIN_IS_FREE);
		
	}
}
