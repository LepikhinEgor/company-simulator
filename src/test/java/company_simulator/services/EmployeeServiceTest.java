package company_simulator.services;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.input.EmployeeCreateData;
import controller.input.EmployeeUpdateData;
import controller.input.EmployeesListQuerryData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.EmployeeDao;
import entities.Company;
import entities.Employee;
import entities.User;
import exceptions.DatabaseAccessException;
import exceptions.employees.EmployeesListException;
import exceptions.employees.IncorrectOrderNumException;
import exceptions.employees.IncorrectPageNumException;
import services.CompanyService;
import services.EmployeeService;
import services.UserService;
import services.utils.EntitiesConventer;

public class EmployeeServiceTest {

	private EmployeeService employeeService;
	private CompanyService companyServiceMock;
	private UserService userServiceMock;
	
	private EmployeeDao employeeDaoMock;
	
	private EntitiesConventer entitiesConventerMock;
	
	//methods arguments
	//updateEmployee()
	String loginEmail;
	
	private void injectDependensies() {
		employeeService.setUserService(userServiceMock);
		employeeService.setEmployeeDao(employeeDaoMock);
		employeeService.setCompanyService(companyServiceMock);
		employeeService.setEntitiesConventer(entitiesConventerMock);
	}
	
	
	@Before
	public void testsInit() {
		employeeService = new EmployeeService();
		
		userServiceMock = mock(UserService.class);
		companyServiceMock = mock(CompanyService.class);
		employeeDaoMock = mock(EmployeeDao.class);
		
		entitiesConventerMock = mock(EntitiesConventer.class);
		
		loginEmail = "admin";
		
	}
	
	private EmployeeUpdateData getEmployeeUpdateData() {
		EmployeeUpdateData employeeData = new EmployeeUpdateData();
		employeeData.setAge(21);
		employeeData.setId(1);
		employeeData.setName("Ivan");
		employeeData.setPerfomance(44);
		employeeData.setSalary(23000);
		employeeData.setSex("male");
		
		return employeeData;
	}
	
	private EmployeeCreateData getEmployeeCreateData() {
		EmployeeCreateData createData = new EmployeeCreateData();
		createData.setAge(21);
		createData.setName("Ivan");
		createData.setPerfomance(44);
		createData.setSalary(23000);
		createData.setSex("male");
		
		return createData;
	}
	
	private ArrayList<Employee> getEmployeesList() {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		employees.add(new Employee());
		employees.add(new Employee());
		employees.add(new Employee());
		employees.add(new Employee());
		
		return employees;
	}
	
	private EmployeesListQuerryData getEmployeesListQuerryData() {
		EmployeesListQuerryData querryData = new EmployeesListQuerryData();
		querryData.setOrderNum(0);
		querryData.setPageNum(0);
		
		return querryData;
	}
	
	private Company getGoodCompany() {
		Company goodCompany = new Company();
		goodCompany.setId(1);
		
		return goodCompany;
	}
	
	private Employee getValidEmployee() {
		Employee employee = new Employee();
		
		employee.setId(1);
		employee.setName("Ivan");
		employee.setAge(21);
		employee.setSalary(23000);
		employee.setSex("male");
		employee.setPerfomance(44);
		
		return employee;
	}
	
	@Test
	public void successUpdateEmployee() throws DatabaseAccessException, SQLException {
		
		EmployeeUpdateData employeeData = getEmployeeUpdateData();
		Company goodCompany = getGoodCompany();
		
		Employee expectedEmployee = getValidEmployee();
		expectedEmployee.setId(1);
		
		when(companyServiceMock.getUserCompany(loginEmail)).thenReturn(goodCompany);
		when(employeeDaoMock.updateEmployee(expectedEmployee, goodCompany.getId())).thenReturn(expectedEmployee.getId());
		when(entitiesConventerMock.transformToEmployee(employeeData)).thenReturn(getValidEmployee());
		
		injectDependensies();
		
		Employee actualEmployee = employeeService.updateEmployee(employeeData, loginEmail);
		
		assertTrue(expectedEmployee.equals(actualEmployee));	
		
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void updateEmployeeThrowDBExceptionFromCompanyService() throws DatabaseAccessException, SQLException {		
		
		EmployeeUpdateData employeeData = getEmployeeUpdateData();
		Company goodCompany = getGoodCompany();
		
		Employee expectedEmployee = getValidEmployee();
		expectedEmployee.setId(1);
		
		when(companyServiceMock.getUserCompany(loginEmail)).thenThrow(new DatabaseAccessException(""));
		when(employeeDaoMock.updateEmployee(expectedEmployee, goodCompany.getId())).thenReturn(expectedEmployee.getId());
		when(entitiesConventerMock.transformToEmployee(employeeData)).thenReturn(getValidEmployee());
		
		injectDependensies();
		
		Employee actualEmployee = employeeService.updateEmployee(employeeData, loginEmail);
		
		assertTrue(expectedEmployee.equals(actualEmployee));	
		
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void updateEmployeeThrowSQLExceptionFromEmployeeDao() throws DatabaseAccessException, SQLException {		
		
		EmployeeUpdateData employeeData = getEmployeeUpdateData();
		Company goodCompany = getGoodCompany();
		
		Employee expectedEmployee = getValidEmployee();
		expectedEmployee.setId(1);
		
		when(companyServiceMock.getUserCompany(loginEmail)).thenReturn(goodCompany);
		when(employeeDaoMock.updateEmployee(expectedEmployee, goodCompany.getId())).thenThrow(new SQLException());
		when(entitiesConventerMock.transformToEmployee(employeeData)).thenReturn(getValidEmployee());
		
		injectDependensies();
		
		Employee actualEmployee = employeeService.updateEmployee(employeeData, loginEmail);
		
		assertTrue(expectedEmployee.equals(actualEmployee));	
		
	}
	
	
	@Test
	public void successReturnCompanyEmployeesList() throws DatabaseAccessException, SQLException, EmployeesListException, IncorrectOrderNumException, IncorrectPageNumException {
		final int PAGE_LIMIT = 10;
		EmployeesListQuerryData querryData = getEmployeesListQuerryData();
		ArrayList<Employee> employees = getEmployeesList();
		Company goodCompany = getGoodCompany();
		
		when(companyServiceMock.getUserCompany(loginEmail)).thenReturn(goodCompany);
		when(employeeDaoMock.getEmployeesList(goodCompany.getId(), querryData.getOrderNum(), querryData.getPageNum(), PAGE_LIMIT)).
		thenReturn(employees);
		
		injectDependensies();
		
		List<Employee> actualEmployees = employeeService.getEmployeesList(querryData, loginEmail);
		
		assertTrue(employees.equals(actualEmployees));
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void getEmployeesListReturnDatabaseExceptionFromCompanyService() throws DatabaseAccessException, SQLException, EmployeesListException, IncorrectOrderNumException, IncorrectPageNumException {

		final int PAGE_LIMIT = 10;
		EmployeesListQuerryData querryData = getEmployeesListQuerryData();
		ArrayList<Employee> employees = getEmployeesList();
		Company goodCompany = getGoodCompany();
		
		when(companyServiceMock.getUserCompany(loginEmail)).thenThrow(new DatabaseAccessException(""));
		when(employeeDaoMock.getEmployeesList(goodCompany.getId(), querryData.getOrderNum(), querryData.getPageNum(), PAGE_LIMIT)).
		thenReturn(employees);
		
		injectDependensies();
		
		List<Employee> actualEmployees = employeeService.getEmployeesList(querryData, loginEmail);
		
		assertTrue(employees.equals(actualEmployees));
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void getEmployeesListThrowSQLExceptionFromDao() throws DatabaseAccessException, SQLException, EmployeesListException, IncorrectOrderNumException, IncorrectPageNumException {

		final int PAGE_LIMIT = 10;
		EmployeesListQuerryData querryData = getEmployeesListQuerryData();
		ArrayList<Employee> employees = getEmployeesList();
		Company goodCompany = getGoodCompany();
		
		when(companyServiceMock.getUserCompany(loginEmail)).thenReturn(getGoodCompany());
		when(employeeDaoMock.getEmployeesList(goodCompany.getId(), querryData.getOrderNum(), querryData.getPageNum(), PAGE_LIMIT)).
		thenThrow(new SQLException());
		
		injectDependensies();
		
		List<Employee> actualEmployees = employeeService.getEmployeesList(querryData, loginEmail);
		
		assertTrue(employees.equals(actualEmployees));
	}
	
	@Test(expected = IncorrectPageNumException.class)
	public void getEmployeesListThrowIncorrectPageNumException() throws DatabaseAccessException, SQLException, EmployeesListException, IncorrectOrderNumException, IncorrectPageNumException {
		final int PAGE_LIMIT = 10;
		ArrayList<Employee> employees = getEmployeesList();
		Company goodCompany = getGoodCompany();
		
		EmployeesListQuerryData querryData = new EmployeesListQuerryData();
		querryData.setPageNum(-1);
		querryData.setOrderNum(0);
		
		when(companyServiceMock.getUserCompany(loginEmail)).thenThrow(new DatabaseAccessException(""));
		when(employeeDaoMock.getEmployeesList(goodCompany.getId(), querryData.getOrderNum(), querryData.getPageNum(), PAGE_LIMIT)).
		thenThrow(new SQLException());
		
		injectDependensies();
		
		List<Employee> actualEmployees = employeeService.getEmployeesList(querryData, loginEmail);
		
		assertTrue(employees.equals(actualEmployees));
	}
	
	@Test(expected = IncorrectOrderNumException.class)
	public void getEmployeesListThrowIncorrectOrderNumException() throws DatabaseAccessException, SQLException, EmployeesListException, IncorrectOrderNumException, IncorrectPageNumException {
		final int PAGE_LIMIT = 10;
		ArrayList<Employee> employees = getEmployeesList();
		Company goodCompany = getGoodCompany();
		
		EmployeesListQuerryData querryData = new EmployeesListQuerryData();
		querryData.setPageNum(0);
		querryData.setOrderNum(12);
		
		when(companyServiceMock.getUserCompany(loginEmail)).thenThrow(new DatabaseAccessException(""));
		when(employeeDaoMock.getEmployeesList(goodCompany.getId(), querryData.getOrderNum(), querryData.getPageNum(), PAGE_LIMIT)).
		thenThrow(new SQLException());
		
		injectDependensies();
		
		List<Employee> actualEmployees = employeeService.getEmployeesList(querryData, loginEmail);
		
		assertTrue(employees.equals(actualEmployees));
	}
	
	@Test
	public void successCreateEmployee() throws DatabaseAccessException, SQLException {
		Company goodCompany = getGoodCompany();
		EmployeeCreateData createData = getEmployeeCreateData();
		
		Employee expectedEmployee = getValidEmployee();
		
		when(companyServiceMock.getUserCompany(loginEmail)).thenReturn(goodCompany);
		when(employeeDaoMock.createEmployee(expectedEmployee, goodCompany.getId())).thenReturn(1L);
		when(entitiesConventerMock.transformToEmployee(createData)).thenReturn(getValidEmployee());
		
		injectDependensies();
		
		Employee actualEmployee = employeeService.createEmployee(createData, loginEmail);
		expectedEmployee.setId(1);
		
		assertTrue(expectedEmployee.equals(actualEmployee));
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void createEmployeeThrowDBAccessExceptionFromCompanyService() throws DatabaseAccessException, SQLException {
		
		Company goodCompany = getGoodCompany();
		EmployeeCreateData createData = getEmployeeCreateData();
		
		Employee expectedEmployee = getValidEmployee();
		
		when(companyServiceMock.getUserCompany(loginEmail)).thenThrow(new DatabaseAccessException(""));
		when(employeeDaoMock.createEmployee(expectedEmployee, goodCompany.getId())).thenReturn(1L);
		when(entitiesConventerMock.transformToEmployee(createData)).thenReturn(getValidEmployee());
		
		injectDependensies();
		
		Employee actualEmployee = employeeService.createEmployee(createData, loginEmail);
		expectedEmployee.setId(1);
		
		assertTrue(expectedEmployee.equals(actualEmployee));
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void createEmployeeThrowSQLExceptionFromEmployeeDao() throws DatabaseAccessException, SQLException {
		
		Company goodCompany = getGoodCompany();
		EmployeeCreateData createData = getEmployeeCreateData();
		
		Employee expectedEmployee = getValidEmployee();
		
		when(companyServiceMock.getUserCompany(loginEmail)).thenReturn(goodCompany);
		when(employeeDaoMock.createEmployee(expectedEmployee, goodCompany.getId())).thenThrow(new SQLException());
		when(entitiesConventerMock.transformToEmployee(createData)).thenReturn(getValidEmployee());
		
		injectDependensies();
		
		Employee actualEmployee = employeeService.createEmployee(createData, loginEmail);
		expectedEmployee.setId(1);
		
		assertTrue(expectedEmployee.equals(actualEmployee));
	}
	
	@Test
	public void getContractTeamSuccess() throws SQLException, DatabaseAccessException {
		int contractId = 1;
		List<Employee> expectedTeam = Arrays.asList(new Employee(), new Employee(), new Employee(), new Employee());
		
		when(employeeDaoMock.getContractEmployees(contractId)).thenReturn(expectedTeam);
		
		injectDependensies();
		
		List<Employee> actualTeam = employeeService.getContractTeam(loginEmail, contractId);
		
		assertTrue(expectedTeam.equals(actualTeam));
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void getContractTeamThrowDBExceptionFromEmployeeDao() throws SQLException, DatabaseAccessException {
		int contractId = 1;
		List<Employee> expectedTeam = Arrays.asList(new Employee(), new Employee(), new Employee(), new Employee());
		
		when(employeeDaoMock.getContractEmployees(contractId)).thenThrow(new SQLException());
		
		injectDependensies();
		List<Employee> actualTeam = employeeService.getContractTeam(loginEmail, contractId);
	
		assertTrue(expectedTeam.equals(actualTeam));
	}
	
	@Test
	public void getFreeEmployeesSuccess() throws DatabaseAccessException, SQLException {
		Company company = getGoodCompany();
		List<Employee> expectedEmployees = Arrays.asList(new Employee(), new Employee(), new Employee(), new Employee());
		
		when(companyServiceMock.getUserCompany(loginEmail)).thenReturn(company);
		when(employeeDaoMock.getFreeEmployees(company.getId())).thenReturn(expectedEmployees);
		
		injectDependensies();
		
		List<Employee> actualEmployees = employeeService.getFreeEmployees(loginEmail);
		
		assertTrue(expectedEmployees.equals(actualEmployees));
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void getFreeEmployeesThrowDBExceptionFromCompanyService() throws DatabaseAccessException, SQLException {
		Company company = getGoodCompany();
		List<Employee> expectedEmployees = Arrays.asList(new Employee(), new Employee(), new Employee(), new Employee());
		
		when(companyServiceMock.getUserCompany(loginEmail)).thenThrow(new DatabaseAccessException(""));
		when(employeeDaoMock.getFreeEmployees(company.getId())).thenReturn(expectedEmployees);
		
		injectDependensies();
		
		List<Employee> actualEmployees = employeeService.getFreeEmployees(loginEmail);
		
		assertTrue(expectedEmployees.equals(actualEmployees));
	}
	
	@Test(expected = DatabaseAccessException.class)
	public void getFreeEmployeesThrowSQLExceptionFromEmployeeDao() throws DatabaseAccessException, SQLException {
		Company company = getGoodCompany();
		List<Employee> expectedEmployees = Arrays.asList(new Employee(), new Employee(), new Employee(), new Employee());
		
		when(companyServiceMock.getUserCompany(loginEmail)).thenReturn(company);
		when(employeeDaoMock.getFreeEmployees(company.getId())).thenThrow(new SQLException());
		
		injectDependensies();
		
		List<Employee> actualEmployees = employeeService.getFreeEmployees(loginEmail);
		
		assertTrue(expectedEmployees.equals(actualEmployees));
	}
}
