package services.utils;

import org.springframework.stereotype.Service;

import controller.input.CreateContractData;
import controller.input.EmployeeCreateData;
import controller.input.EmployeeUpdateData;
import entities.Contract;
import entities.Employee;

@Service
public class EntitiesConventer {

	public Contract trasformToContract(CreateContractData contractData) {
		Contract contract = new Contract();
		
		contract.setName(contractData.getName());
		contract.setPerfomanceUnits(contractData.getSize());
		contract.setFee(contractData.getFee());
		contract.setDeadline(contractData.getDeadline());
		contract.setDescription("No description");
		contract.setProgress(0);
		
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
