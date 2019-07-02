package services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import controller.messages.NewUserData;
import exceptions.IncorrectRegistrationDataException;

@Service
public class UserService {
	public void createNewUser(NewUserData userData) throws IncorrectRegistrationDataException {
		String[] userDataMistakes = getUserDataMistakes(userData);
		if (userDataMistakes.length != 0)
			throw new IncorrectRegistrationDataException(userDataMistakes);
	//check from database...
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
