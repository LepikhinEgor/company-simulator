package services;

import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import controller.messages.NewUserData;
import controller.messages.SignInData;
import dao.UserDao;
import exceptions.EmailAlreadyExistException;
import exceptions.InvalidEmailRegistrationException;
import exceptions.InvalidLoginRegistrationException;
import exceptions.InvalidPasswordRegistrationException;
import exceptions.InvalidSignInLoginEmail;
import exceptions.InvalidSignInPasswordException;
import exceptions.LoginAlreadyExistException;
import exceptions.NotRecordToDBException;

@Service
public class UserService {
	
	private final static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private UserDao userDao;
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public boolean userSignIn(SignInData signUpData) throws InvalidSignInPasswordException, InvalidSignInLoginEmail, SQLException {
		
		boolean isEmail = isCorrectEmail(signUpData.getLoginEmail());
		boolean isLogin = isCorrectLogin(signUpData.getLoginEmail());
		boolean correctPassword = isCorrectPassword(signUpData.getPassword());
		
		if (!isEmail && !isLogin)
			throw new InvalidSignInLoginEmail();
		
		if (!correctPassword)
			throw new InvalidSignInPasswordException();
		
		boolean userExist = userDao.signIn(signUpData.getLoginEmail(), signUpData.getPassword());
		
		return userExist;
	}
	
	public long createNewUser(NewUserData userData)throws InvalidLoginRegistrationException, LoginAlreadyExistException, EmailAlreadyExistException, NotRecordToDBException, InvalidEmailRegistrationException, InvalidPasswordRegistrationException {
		
		if (!isCorrectEmail(userData.getEmail())) {
			throw new InvalidEmailRegistrationException();
		}
		if (!isCorrectLogin(userData.getLogin())) {
			throw new InvalidLoginRegistrationException();
		}
		if (!isCorrectPassword(userData.getPassword())) {
			throw new InvalidPasswordRegistrationException();
		}
		
		if (checkUserLoginAlreadyExist(userData.getLogin()))
			throw new LoginAlreadyExistException();
		
		if (checkUserEmailAlreadyExist(userData.getEmail())) 
			throw new EmailAlreadyExistException();
		
		long newUserId = recordNewUser(userData);
		
		return newUserId;
	}
	
	private long recordNewUser(NewUserData userData) throws NotRecordToDBException {
		try {
			long id = userDao.recordUser(userData.getLogin(), userData.getEmail(), userData.getPassword());
			return id;
		} catch (SQLException e) {
			logger.error("User not recorded", e);
			throw new NotRecordToDBException();
		}
	}
	
	public boolean checkUserLoginAlreadyExist(String userLogin) {
		boolean loginExist = false;
		
		try {
			loginExist = userDao.checkLoginAlreadyExist(userLogin);
		} catch(SQLException sqlex) {
			logger.error("Error when checking user login exist", sqlex);
		}
		
		return loginExist;
	}
	
	private boolean checkUserEmailAlreadyExist(String userEmail) {
		boolean loginExist = false;
		
		try {
			loginExist = userDao.checkEmailAlreadyExist(userEmail);
		} catch(SQLException sqlex) {
			logger.error("Error when checking user email exist", sqlex);
		}
		
		return loginExist;
	}
	
	private boolean isCorrectLogin(String login) {
		return login.matches("^(?!.*\\.\\.)(?!\\.)(?!.*\\.$)(?!\\d+$)[a-zA-Z0-9.]{5,50}$");
	}
	
	private boolean isCorrectPassword(String password) {
		return password.matches("^[0-9a-zA-Z!@#$%^&*]+");
	}
	
	private boolean isCorrectEmail(String email) {
		return email.matches(".+@.+\\..+");
	}
}
