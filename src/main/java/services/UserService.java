package services;

import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import controller.messages.NewUserData;
import dao.UserDao;
import exceptions.EmailAlreadyExistException;
import exceptions.IncorrectRegistrationDataException;
import exceptions.LoginAlreadyExistException;
import exceptions.NotRecordToDBException;

@Service
public class UserService {
	
	private final static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;
	
	public void createNewUser(NewUserData userData)throws IncorrectRegistrationDataException, LoginAlreadyExistException, EmailAlreadyExistException, NotRecordToDBException {
		String[] userDataMistakes = getUserDataMistakes(userData);
		
		if (userDataMistakes.length != 0)
			throw new IncorrectRegistrationDataException(userDataMistakes);
		
		if (checkUserLoginAlreadyExist(userData.getLogin()))
			throw new LoginAlreadyExistException();
		
		if (checkUserEmailAlreadyExist(userData.getEmail())) 
			throw new EmailAlreadyExistException();
		
		recordNewUser(userData);
	}
	
	private void recordNewUser(NewUserData userData) throws NotRecordToDBException {
		try {
			userDao.recordUser(userData.getLogin(), userData.getEmail(), userData.getPassword());
		} catch (SQLException e) {
			logger.error("User not recorded", e);
			throw new NotRecordToDBException();
		}
	}
	
	private boolean checkUserLoginAlreadyExist(String userLogin) {
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
	
	private String[] getUserDataMistakes(NewUserData userData) {
		ArrayList<String> mistakesDescription = new ArrayList<String>();
		
		if (!isCorrectLogin(userData.getLogin())) {
			mistakesDescription.add("Incorrect login");
		}
		
		if (!isCorrectPassword(userData.getPassword())) {
			mistakesDescription.add("Incorrect password");
		}
		
		if (!isCorrectEmail(userData.getEmail())) {
			mistakesDescription.add("Incorrect email");
		}
		
		return mistakesDescription.toArray(new String[mistakesDescription.size()]);
	}
	
	private boolean isCorrectLogin(String login) {
		return login.matches("^(?!.*\\.\\.)(?!\\.)(?!.*\\.$)(?!\\d+$)[a-zA-Z0-9.]{5,50}$");
	}
	
	private boolean isCorrectPassword(String password) {
		return password.matches("^[0-9a-zA-Z!@#$%^&*]+");
	}
	
	private boolean isCorrectEmail(String email) {
		return email.matches(".+@.+");
	}
}
