package services.utils;

import org.springframework.stereotype.Service;

import controller.input.CreateContractData;
import controller.input.EmployeeCreateData;
import controller.input.EmployeeUpdateData;
import controller.messages.entities.ContractRestData;
import entities.Contract;
import entities.Employee;

@Service
public class EntitiesConventer {

	public ContractRestData transformToContractRestData(Contract contract) {
		ContractRestData contractRestData = new ContractRestData();
		
		contractRestData.setId(contract.getId());
		contractRestData.setDeadline(contract.getDeadline());
		contractRestData.setDescription(contract.getDescription());
		contractRestData.setExpectedCompletionTime(contract.getTimeBeforeCompletion());
		contractRestData.setFee(contract.getFee());
		contractRestData.setName(contract.getName());
		contractRestData.setSize(contract.getPerfomanceUnits());
		contractRestData.setWorkSpeed(contract.getWorkSpeed());
		contractRestData.setProgress(contract.calculateProgress());
		
		return contractRestData;
	}
	
	public Contract trasformToContract(CreateContractData contractData) {
		Contract contract = new Contract();
		
		contract.setName(contractData.getName());
		contract.setPerfomanceUnits(contractData.getSize());
		contract.setFee(contractData.getFee());
		contract.setDeadline(contractData.getDeadline());
		contract.setDescription("No description");
		
		return contract;
	}
	
	public Employee transormToEmployee(EmployeeUpdateData updateData) {
		Employee employee = new Employee();
		
		employee.setId(updateData.getId());
		employee.setName(updateData.getName());
		employee.setAge(updateData.getAge());
		employee.setSalary(updateData.getSalary());
		employee.setSex(updateData.getSex());
		employee.setPerfomance(updateData.getPerfomance());
		
		return employee;
	}
	
	public Employee transformToEmployee(EmployeeCreateData createData) {
		Employee employee = new Employee();
		
		employee.setName(createData.getName());
		employee.setAge(createData.getAge());
		employee.setSalary(createData.getSalary());
		employee.setSex(createData.getSex());
		employee.setPerfomance(createData.getPerfomance());
		
		return employee;
	}
}
