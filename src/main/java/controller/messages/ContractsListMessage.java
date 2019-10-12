package controller.messages;

import java.util.List;

import entities.Contract;

public class ContractsListMessage extends Message{
	
	List<Contract> contracts;
	
	public ContractsListMessage(int status) {
		super(status);
	}
	
	public ContractsListMessage(int status, String message) {
		super(status, message);
	}
	
	public ContractsListMessage(int status, String message, List<Contract> contracts) {
		super(status, message);
		this.contracts = contracts;
	}

	public List<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	} 
}
