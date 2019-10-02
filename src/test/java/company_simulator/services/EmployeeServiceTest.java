package company_simulator.services;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.input.EmployeeUpdateData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.sql.SQLException;

import dao.EmployeeDao;
import entities.Company;
import entities.Employee;
import entities.User;
import exceptions.DatabaseAccessException;
import services.CompanyService;
import services.EmployeeService;
import services.UserService;

public class EmployeeServiceTest {

	private EmployeeService employeeService;
	private CompanyService companyServiceMock;
	private UserService userServiceMock;
	
	private EmployeeDao employeeDaoMock;
	
	//methods arguments
	//updateEmployee()
	EmployeeUpdateData employeeData;
	String loginEmail;
	
	//mockEntities
	private User goodUser;
	private Company goodCompany;
	
	@Before
	public void testsInit() {
		employeeService = new EmployeeService();
		
		userServiceMock = mock(UserService.class);
		companyServiceMock = mock(CompanyService.class);
		employeeDaoMock = mock(EmployeeDao.class);
		
		employeeData = new EmployeeUpdateData();
		employeeData.setAge(21);
		employeeData.setId(1);
		employeeData.setName("Ivan");
		employeeData.setPerfomance(44);
		employeeData.setSalary(23000);
		employeeData.setSex("male");
		loginEmail = "admin";
		
		goodUser = new User();
		goodUser.setId(1);
		goodUser.setEmail("admin@mail.ru");
		goodUser.setLogin("admin");
		goodUser.setPassword("qwerty123");
		
		goodCompany = new Company();
		goodCompany.setId(1);
	}
	
	@Test
	public void successUpdateEmployee() throws DatabaseAccessException, SQLException {
		
		Employee expectedEmployee = new Employee(employeeData);
		expectedEmployee.setId(1);
		
		when(userServiceMock.getUserDataByLoginEmail(loginEmail)).thenReturn(goodUser);
		when(companyServiceMock.getUserCompany(goodUser.getId())).thenReturn(goodCompany);
		when(employeeDaoMock.updateEmployee(expectedEmployee, goodCompany.getId())).thenReturn(expectedEmployee.getId());
		
		employeeService.setUserService(userServiceMock);
		employeeService.setEmployeeDao(employeeDaoMock);
		employeeService.setCompanyService(companyServiceMock);
		
		Employee actualEmployee = employeeService.updateEmployee(employeeData, loginEmail);
		
		assertTrue(expectedEmployee.equals(actualEmployee));	
		
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void updateEmployeeThrowDBExceptionFromCompanyService() throws DatabaseAccessException, SQLException {		
		
		Employee expectedEmployee = new Employee(employeeData);
		expectedEmployee.setId(1);
		
		when(userServiceMock.getUserDataByLoginEmail(loginEmail)).thenReturn(goodUser);
		when(companyServiceMock.getUserCompany(goodUser.getId())).thenThrow(new DatabaseAccessException());
		when(employeeDaoMock.updateEmployee(expectedEmployee, goodCompany.getId())).thenReturn(expectedEmployee.getId());
		
		employeeService.setUserService(userServiceMock);
		employeeService.setEmployeeDao(employeeDaoMock);
		employeeService.setCompanyService(companyServiceMock);
		
		Employee actualEmployee = employeeService.updateEmployee(employeeData, loginEmail);
		
		assertTrue(expectedEmployee.equals(actualEmployee));	
		
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void updateEmployeeThrowDBExceptionFromUserService() throws DatabaseAccessException, SQLException {
		
		Employee expectedEmployee = new Employee(employeeData);
		expectedEmployee.setId(1);
		
		when(userServiceMock.getUserDataByLoginEmail(loginEmail)).thenThrow(new DatabaseAccessException());
		when(companyServiceMock.getUserCompany(goodUser.getId())).thenReturn(goodCompany);
		when(employeeDaoMock.updateEmployee(expectedEmployee, goodCompany.getId())).thenReturn(expectedEmployee.getId());
		
		employeeService.setUserService(userServiceMock);
		employeeService.setEmployeeDao(employeeDaoMock);
		employeeService.setCompanyService(companyServiceMock);
		
		Employee actualEmployee = employeeService.updateEmployee(employeeData, loginEmail);
		
		assertTrue(expectedEmployee.equals(actualEmployee));	
		
	}
}
