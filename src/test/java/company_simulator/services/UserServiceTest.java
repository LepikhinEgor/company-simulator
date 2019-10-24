package company_simulator.services;

import java.sql.SQLException;

import javax.xml.crypto.Data;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.BDDMockito.Then;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
import services.UserService;


public class UserServiceTest {
	
	private UserService userService;
	private UserDao userDaoMock;
	
	private User validUser;
	
	@Before
	public void initBefore() {
		userService = new UserService();
		userDaoMock = mock(UserDao.class);
		
		validUser = new User();
	}
	
	private User getValidUser() {
		User validUser = new User();
		
		validUser.setEmail("admin@mail.ru");
		validUser.setId(1);
		validUser.setLogin("admin");
		validUser.setPassword("qwerty");
		
		return validUser;
	}
	
	@Test(expected = InvalidSignInLoginEmail.class)
	public void invalidLoginSignIn() throws InvalidSignInPasswordException, InvalidSignInLoginEmail, DatabaseAccessException {
		SignInData signInData = new SignInData();
		signInData.setLoginEmail("user22!!&&");
		signInData.setPassword("qwerty");
		
		userService.userSignIn(signInData);
	}
	
	@Test(expected = InvalidSignInLoginEmail.class)
	public void invalidEmailSignIn() throws InvalidSignInPasswordException, InvalidSignInLoginEmail, DatabaseAccessException {
		SignInData signInData = new SignInData();
		signInData.setLoginEmail("user@user");
		signInData.setPassword("qwerty");
		
		userService.userSignIn(signInData);
	}
	
	@Test(expected = InvalidSignInPasswordException.class)
	public void invalidPasswordSignIn() throws InvalidSignInPasswordException, InvalidSignInLoginEmail, DatabaseAccessException {
		SignInData signInData = new SignInData();
		signInData.setLoginEmail("user123");
		signInData.setPassword("qwerty &&11!");
		
		userService.userSignIn(signInData);
	}
	
	@Test
	public void correctUserSignIn() throws InvalidSignInPasswordException, InvalidSignInLoginEmail, DatabaseAccessException, SQLException {
		String login = "admin";
		String password = "password";
		
		SignInData signInData = new SignInData();
		signInData.setLoginEmail(login);
		signInData.setPassword(password);
		
		when(userDaoMock.signIn(login, password)).thenReturn(validUser);
		
		userService.setUserDao(userDaoMock);
		
		boolean userExist = userService.userSignIn(signInData) != null;
		assertTrue(userExist);
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void dbExceptionSignIn() throws InvalidSignInPasswordException, InvalidSignInLoginEmail, DatabaseAccessException, SQLException {
		String login = "user123";
		String password = "password";
		
		SignInData signInData = new SignInData();
		signInData.setLoginEmail(login);
		signInData.setPassword(password);
		
		when(userDaoMock.signIn(login, password)).thenThrow(new SQLException());
		
		userService.setUserDao(userDaoMock);
		
		boolean userExist = userService.userSignIn(signInData) != null;
		assertTrue(userExist);
	}
	
	@Test(expected = InvalidEmailRegistrationException.class)
	public void invalidEmailCreateUser() throws InvalidLoginRegistrationException, LoginAlreadyExistException, EmailAlreadyExistException, InvalidEmailRegistrationException, InvalidPasswordRegistrationException, DatabaseAccessException {
		NewUserData newUserData = new NewUserData();
		newUserData.setEmail("newUser@mail");
		newUserData.setLogin("user123");
		newUserData.setPassword("password");
		
		userService.createNewUser(newUserData);
	}
	
	@Test(expected = InvalidLoginRegistrationException.class)
	public void invalidLoginCreateUser() throws InvalidLoginRegistrationException, LoginAlreadyExistException, EmailAlreadyExistException, InvalidEmailRegistrationException, InvalidPasswordRegistrationException, DatabaseAccessException {
		NewUserData newUserData = new NewUserData();
		newUserData.setEmail("newUser@mail.sru");
		newUserData.setLogin("user$%123");
		newUserData.setPassword("password");
		
		userService.createNewUser(newUserData);
	}
	
	@Test(expected = InvalidPasswordRegistrationException.class)
	public void invalidPasswordCreateUser() throws InvalidLoginRegistrationException, LoginAlreadyExistException, EmailAlreadyExistException, InvalidEmailRegistrationException, InvalidPasswordRegistrationException, DatabaseAccessException {
		NewUserData newUserData = new NewUserData();
		newUserData.setEmail("newUser@mail.sru");
		newUserData.setLogin("user123");
		newUserData.setPassword("passw ord");
		
		userService.createNewUser(newUserData);
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = EmailAlreadyExistException.class)
	public void emailExistCreateUser() throws SQLException, InvalidLoginRegistrationException, LoginAlreadyExistException, EmailAlreadyExistException, NotRecordToDBException, InvalidEmailRegistrationException, InvalidPasswordRegistrationException, DatabaseAccessException {
		String email = "newUser@mail.ru";
		String login = "user123";
		String password = "password";
		
		NewUserData newUserData = new NewUserData();
		newUserData.setEmail(email);
		newUserData.setLogin(login);
		newUserData.setPassword(password);
		
		when(userDaoMock.recordUser(login, email, password)).thenThrow(EmailAlreadyExistException.class);
		
		userService.setUserDao(userDaoMock);
		
		userService.createNewUser(newUserData);
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = LoginAlreadyExistException.class)
	public void loginExistCreateUser() throws SQLException, InvalidLoginRegistrationException, LoginAlreadyExistException, EmailAlreadyExistException, InvalidEmailRegistrationException, InvalidPasswordRegistrationException, DatabaseAccessException {
		String email = "newUser@mail.ru";
		String login = "user1234";
		String password = "password";
		
		NewUserData newUserData = new NewUserData();
		newUserData.setEmail(email);
		newUserData.setLogin(login);
		newUserData.setPassword(password);
		
		when(userDaoMock.recordUser(login, email, password)).thenThrow(LoginAlreadyExistException.class);
		
		userService.setUserDao(userDaoMock);
		
		userService.createNewUser(newUserData);
	}
	
	@Test
	public void correctCreateUser() throws SQLException, InvalidLoginRegistrationException, LoginAlreadyExistException, EmailAlreadyExistException, InvalidEmailRegistrationException, InvalidPasswordRegistrationException, DatabaseAccessException {
		String email = "newUser@mail.ru";
		String login = "user1234";
		String password = "password";
		
		NewUserData newUserData = new NewUserData();
		newUserData.setEmail(email);
		newUserData.setLogin(login);
		newUserData.setPassword(password);
		
		when(userDaoMock.recordUser(login, email, password)).thenReturn(12L);
		
		userService.setUserDao(userDaoMock);
		
		long id = userService.createNewUser(newUserData);
		
		assertNotEquals(0, id);
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void createNewUserThrowSQLExceptionFromDao() throws SQLException, InvalidLoginRegistrationException, LoginAlreadyExistException, EmailAlreadyExistException, InvalidEmailRegistrationException, InvalidPasswordRegistrationException, DatabaseAccessException {
		String email = "newUser@mail.ru";
		String login = "user1234";
		String password = "password";
		
		NewUserData newUserData = new NewUserData();
		newUserData.setEmail(email);
		newUserData.setLogin(login);
		newUserData.setPassword(password);
		
		when(userDaoMock.recordUser(login, email, password)).thenThrow(new SQLException());
		
		userService.setUserDao(userDaoMock);
		
		long id = userService.createNewUser(newUserData);
		
		assertNotEquals(0, id);
	}
	
	@Test(expected = LoginAlreadyExistException.class)
	public void createNewUserLoginExist() throws SQLException, InvalidLoginRegistrationException, LoginAlreadyExistException, EmailAlreadyExistException, InvalidEmailRegistrationException, InvalidPasswordRegistrationException, DatabaseAccessException {
		String email = "newUser@mail.ru";
		String login = "user1234";
		String password = "password";
		
		NewUserData newUserData = new NewUserData();
		newUserData.setEmail(email);
		newUserData.setLogin(login);
		newUserData.setPassword(password);
		
		when(userDaoMock.checkLoginAlreadyExist(login)).thenReturn(true);
		when(userDaoMock.recordUser(login, email, password)).thenReturn(1L);
		
		userService.setUserDao(userDaoMock);
		
		long id = userService.createNewUser(newUserData);
		
		assertNotEquals(0, id);
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void createNewUserThrowDBExceptionCheckingLoginExist() throws SQLException, InvalidLoginRegistrationException, LoginAlreadyExistException, EmailAlreadyExistException, InvalidEmailRegistrationException, InvalidPasswordRegistrationException, DatabaseAccessException {
		String email = "newUser@mail.ru";
		String login = "user1234";
		String password = "password";
		
		NewUserData newUserData = new NewUserData();
		newUserData.setEmail(email);
		newUserData.setLogin(login);
		newUserData.setPassword(password);
		
		when(userDaoMock.checkLoginAlreadyExist(login)).thenThrow(new SQLException());
		when(userDaoMock.recordUser(login, email, password)).thenReturn(1L);
		
		userService.setUserDao(userDaoMock);
		
		long id = userService.createNewUser(newUserData);
		
		assertNotEquals(0, id);
	}
	
	@Test(expected = EmailAlreadyExistException.class)
	public void createNewUserEmailExist() throws SQLException, InvalidLoginRegistrationException, LoginAlreadyExistException, EmailAlreadyExistException, InvalidEmailRegistrationException, InvalidPasswordRegistrationException, DatabaseAccessException {
		String email = "newUser@mail.ru";
		String login = "user1234";
		String password = "password";
		
		NewUserData newUserData = new NewUserData();
		newUserData.setEmail(email);
		newUserData.setLogin(login);
		newUserData.setPassword(password);
		
		when(userDaoMock.checkEmailAlreadyExist(email)).thenReturn(true);
		when(userDaoMock.recordUser(login, email, password)).thenReturn(1L);
		
		userService.setUserDao(userDaoMock);
		
		long id = userService.createNewUser(newUserData);
		
		assertNotEquals(0, id);
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void createNewUserThrowDBExceptionCheckingEmailExist() throws SQLException, InvalidLoginRegistrationException, LoginAlreadyExistException, EmailAlreadyExistException, InvalidEmailRegistrationException, InvalidPasswordRegistrationException, DatabaseAccessException {
		String email = "newUser@mail.ru";
		String login = "user1234";
		String password = "password";
		
		NewUserData newUserData = new NewUserData();
		newUserData.setEmail(email);
		newUserData.setLogin(login);
		newUserData.setPassword(password);
		
		when(userDaoMock.checkEmailAlreadyExist(email)).thenThrow(new SQLException());
		when(userDaoMock.recordUser(login, email, password)).thenReturn(1L);
		
		userService.setUserDao(userDaoMock);
		
		long id = userService.createNewUser(newUserData);
		
		assertNotEquals(0, id);
	}
	
	@Test
	public void getUserDataByLoginEmailSuccess() throws SQLException, DatabaseAccessException {
		User validUser = getValidUser();
		
		when(userDaoMock.getUserDataByLoginEmail(validUser.getLogin())).thenReturn(validUser);
		
		userService.setUserDao(userDaoMock);
		
		User actualUser = userService.getUserDataByLoginEmail(validUser.getLogin());
		
		assertTrue(validUser.equals(actualUser));
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void getUserDataByLoginEmailThrowSQLExceptionFromDao() throws SQLException, DatabaseAccessException {
		User validUser = getValidUser();
		
		when(userDaoMock.getUserDataByLoginEmail(validUser.getLogin())).thenThrow(new SQLException());
		
		userService.setUserDao(userDaoMock);
		
		User actualUser = userService.getUserDataByLoginEmail(validUser.getLogin());
		
		assertTrue(validUser.equals(actualUser));
	}
	
}
