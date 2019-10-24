package company_simulator.services.utils;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import controller.input.CreateContractData;
import controller.input.EmployeeCreateData;
import controller.input.EmployeeUpdateData;
import controller.messages.entities.ContractRestData;
import entities.Contract;
import entities.Employee;
import services.utils.EntitiesConventer;

public class EntitiesConventerTest {
	private EntitiesConventer entitiesConventer;
	
	private static Timestamp contractDeadline;
	private static Timestamp contractTeamChangedTime;
	
	@BeforeClass
	public static void initClass() {
		contractDeadline = new Timestamp(System.currentTimeMillis() + 1000000);
		contractTeamChangedTime = new Timestamp(System.currentTimeMillis() - 1000000);
	}
	
	@Before
	public void init() {
		entitiesConventer = new EntitiesConventer();
	}
	
	private Contract getValidContract() {
		Contract validContract = new Contract();
		validContract.setDeadline(contractDeadline);
		validContract.setDescription("");
		validContract.setFee(100);
		validContract.setId(1);
		validContract.setLastProgress(100);
		validContract.setName("Name");
		validContract.setPerfomanceUnits(10000);
		validContract.setTeamChangedDate(contractTeamChangedTime);
		validContract.setWorkSpeed(100);
		validContract.setStatus(Contract.PERFORMED);
		
		return validContract;
	}
	
	private ContractRestData getValidContractRestData() {
		ContractRestData contractRestData = new ContractRestData();
		
		contractRestData.setId(1);
		contractRestData.setDeadline(contractDeadline.getTime());
		contractRestData.setDescription("");
		contractRestData.setFee(100);
		contractRestData.setName("Name");
		contractRestData.setSize(10000);
		contractRestData.setWorkSpeed(100);
		contractRestData.setStatus(Contract.PERFORMED);
		
		return contractRestData;
	}
	
	private CreateContractData getValidCreateContractData() {
		CreateContractData validCreateContractData = new CreateContractData();
		
		validCreateContractData.setDeadline(1000000);
		validCreateContractData.setFee(100);
		validCreateContractData.setName("Name");
		validCreateContractData.setSize(10000);
		
		return validCreateContractData;
	}
	
	private Contract getExpectedConventeredContract() {
		Contract contract = new Contract();
		
		contract.setDeadline(new Timestamp(System.currentTimeMillis() + 1000000));
		contract.setFee(100);
		contract.setName("Name");
		contract.setPerfomanceUnits(10000);
		contract.setDescription("No description");
		
		return contract;
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
	public void transformToContractRestDataSuccessContractNotChangeStatus() {
		Contract validContract = getValidContract();
		
		ContractRestData actualContractRestData = entitiesConventer.transformToContractRestData(validContract);
		
		ContractRestData expectedContractRestData = getValidContractRestData();
		expectedContractRestData.setExpectedCompletionTime(validContract.getTimeBeforeCompletion());
		expectedContractRestData.setProgress(validContract.calculateProgress());
		
		assertTrue(expectedContractRestData.equals(actualContractRestData));
	}
	
	@Test
	public void transformToContractRestDataSuccessContractChangeStatusToCompleted() {
		Contract validContract = getValidContract();
		Timestamp finishedDeadline = new Timestamp(System.currentTimeMillis() - 10000);
		validContract.setDeadline(finishedDeadline);
		validContract.setWorkSpeed(10000000);
		
		ContractRestData actualContractRestData = entitiesConventer.transformToContractRestData(validContract);
		
		ContractRestData expectedContractRestData = getValidContractRestData();
		expectedContractRestData.setExpectedCompletionTime(validContract.getTimeBeforeCompletion());
		expectedContractRestData.setProgress(validContract.calculateProgress());
		expectedContractRestData.setDeadline(finishedDeadline.getTime());
		expectedContractRestData.setWorkSpeed(10000000);
		expectedContractRestData.setStatus(Contract.COMPLETED);
		
		assertTrue(expectedContractRestData.equals(actualContractRestData));
	}
	
	
	@Test
	public void transformToContractRestData_ContractChangeStatusFromPerformedToCompleted() {
		Contract validContract = getValidContract();
		validContract.setWorkSpeed(1000000); // contract will be completed
		
		ContractRestData actualContractRestData = entitiesConventer.transformToContractRestData(validContract);
		
		ContractRestData expectedContractRestData = getValidContractRestData();
		expectedContractRestData.setExpectedCompletionTime(validContract.getTimeBeforeCompletion());
		expectedContractRestData.setProgress(validContract.calculateProgress());
		expectedContractRestData.setWorkSpeed(1000000);
		expectedContractRestData.setStatus(Contract.COMPLETED);
		
//		System.out.println(expectedContractRestData);
//		System.out.println(actualContractRestData);
		
		assertTrue(expectedContractRestData.equals(actualContractRestData));
	}
	
	@Test
	public void transformToContractRestData_ContractChangeStatusFromPerformedToFailed() {
		Contract validContract = getValidContract();
		Timestamp fuckUpDeadline = new Timestamp(System.currentTimeMillis() - 10000000);
		validContract.setDeadline(fuckUpDeadline);
		ContractRestData actualContractRestData = entitiesConventer.transformToContractRestData(validContract);
		
		ContractRestData expectedContractRestData = getValidContractRestData();
		expectedContractRestData.setExpectedCompletionTime(validContract.getTimeBeforeCompletion());
		expectedContractRestData.setProgress(validContract.calculateProgress());
		expectedContractRestData.setDeadline(fuckUpDeadline.getTime());
		expectedContractRestData.setStatus(Contract.FAILED);
		
		assertTrue(expectedContractRestData.equals(actualContractRestData));
	}
	
	@Test
	public void trasformToContractSuccess() {
		CreateContractData createContractData = getValidCreateContractData();
		Contract expectedContract = getExpectedConventeredContract();
		
		Contract actualContract = entitiesConventer.trasformToContract(createContractData);
		
		expectedContract.setDeadline(actualContract.getDeadline()); // deadline not testing because its depend by run time
		
		assertTrue(expectedContract.equals(actualContract));
	}
	
	@Test
	public void transformToEmployeeFromEmployeeCreateDataSuccess() {
		EmployeeCreateData createData = getEmployeeCreateData();
		
		Employee actualEmployee = entitiesConventer.transformToEmployee(createData);
		
		Employee expectedEmployee = getValidEmployee();
		expectedEmployee.setId(0);
		
		assertTrue(expectedEmployee.equals(actualEmployee));
	}
	
	@Test
	public void transformToEmployeeFromEmployeeUpdateDataSuccess() {
		EmployeeUpdateData updateData = getEmployeeUpdateData();
		
		Employee actualEmployee = entitiesConventer.transformToEmployee(updateData);
		
		Employee expectedEmployee = getValidEmployee();
		
		assertTrue(expectedEmployee.equals(actualEmployee));
	}
}
