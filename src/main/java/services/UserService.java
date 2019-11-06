package services;

import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aspects.annotations.Loggable;
import controller.input.NewUserData;
import controller.input.SignInData;
import dao.UserDao;
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

@Service
public class UserService {
	
	private final static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private UserDao userDao;
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	/**
	 * 
	 * @param signUpData
	 * @return true if user with specified login and password exist
	 * @throws InvalidSignInPasswordException password must match a-z A-Z 0-9 !-*
	 * @throws InvalidSignInLoginEmail
	 * @throws DatabaseAccessException 
	 */
	@Loggable
	public User userSignIn(SignInData signUpData) throws InvalidSignInPasswordException, InvalidSignInLoginEmail, DatabaseAccessException {
		
		boolean isEmail = isCorrectEmail(signUpData.getLoginEmail());
		boolean isLogin = isCorrectLogin(signUpData.getLoginEmail());
		boolean correctPassword = isCorrectPassword(signUpData.getPassword());
		
		if (!isEmail && !isLogin)
			throw new InvalidSignInLoginEmail();
		
		if (!correctPassword)
			throw new InvalidSignInPasswordException();
		
		User signedUser;
		try {
			signedUser = userDao.signIn(signUpData.getLoginEmail(), signUpData.getPassword());
		} catch (SQLException e) {
			throw new DatabaseAccessException(e.getMessage());
		}
		
		return signedUser;
	}
	
	@Loggable
	public long createNewUser(NewUserData userData)throws InvalidLoginRegistrationException, LoginAlreadyExistException, EmailAlreadyExistException, InvalidEmailRegistrationException, InvalidPasswordRegistrationException, DatabaseAccessException {
		
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
	
	private long recordNewUser(NewUserData userData) throws DatabaseAccessException   {
		try {
			long id = userDao.recordUser(userData.getLogin(), userData.getEmail(), userData.getPassword());
			return id;
		} catch (SQLException e) {
			logger.error("User not recorded", e);
			throw new DatabaseAccessException("");
		}
	}
	
	@Loggable
	public boolean checkUserLoginAlreadyExist(String userLogin) throws DatabaseAccessException {
		boolean loginExist = false;
		
		try {
			loginExist = userDao.checkLoginAlreadyExist(userLogin);
		} catch(SQLException sqlex) {
			throw new DatabaseAccessException("Error when checking user login exist");
		}
		
		return loginExist;
	}
	
	@Loggable
	public User getUserDataByLoginEmail(String loginEmail) throws DatabaseAccessException {
		User user = null;
		try {
			user = userDao.getUserDataByLoginEmail(loginEmail);
		} catch (SQLException e) {
			throw new DatabaseAccessException("Error, when try get user data by login/email");
		}
		
		return user;
	}
	
	private boolean checkUserEmailAlreadyExist(String userEmail) throws DatabaseAccessException {
		boolean loginExist = false;
		
		try {
			loginExist = userDao.checkEmailAlreadyExist(userEmail);
		} catch(SQLException sqlex) {
			throw new DatabaseAccessException("");
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
