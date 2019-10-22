package controller.messages;

import java.util.List;

import controller.messages.entities.ContractRestData;
import entities.Contract;

public class ContractsListMessage extends Message{
	
	List<ContractRestData> contracts;
	
	public ContractsListMessage(int status) {
		super(status);
	}
	
	public ContractsListMessage(int status, String message) {
		super(status, message);
	}
	
	public ContractsListMessage(int status, String message, List<ContractRestData> contracts) {
		super(status, message);
		this.contracts = contracts;
	}

	public List<ContractRestData> getContracts() {
		return contracts;
	}

	public void setContracts(List<ContractRestData> contracts) {
		this.contracts = contracts;
	} 
}
