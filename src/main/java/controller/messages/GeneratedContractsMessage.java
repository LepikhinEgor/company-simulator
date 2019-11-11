package controller.messages;

import java.util.List;

import controller.messages.entities.ContractRestData;

public class GeneratedContractsMessage extends Message{
	
	private List<ContractRestData> contracts;
	
	public GeneratedContractsMessage() {
		super();
	}
	
	public GeneratedContractsMessage(int status) {
		super(status);
	}
	
	public GeneratedContractsMessage(int status, String message) {
		super(status, message);
	}
	
	public GeneratedContractsMessage(int status, List<ContractRestData> contracts) {
		super(status);
		this.contracts = contracts;
	}

	public List<ContractRestData> getContracts() {
		return contracts;
	}

	public void setContracts(List<ContractRestData> contracts) {
		this.contracts = contracts;
	}
}
