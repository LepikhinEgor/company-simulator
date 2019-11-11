package controller.messages;

import java.util.List;

import entities.Contract;

public class GeneratedContractsMessage extends Message{
	
	private List<Contract> contracts;
	
	public GeneratedContractsMessage() {
		super();
	}
	
	public GeneratedContractsMessage(int status) {
		super(status);
	}
	
	public GeneratedContractsMessage(int status, String message) {
		super(status, message);
	}
	
	public GeneratedContractsMessage(int status, String message, List<Contract> contract) {
		super(status, message);
	}
}
