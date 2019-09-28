package controller.messages;

public class CompanyInfoMessage {
	
	private int id;
	private String name;
	private int cash;
	private int defaultCash;
	private String ownerName;
	private int employeesCount;
	private int contractsExecuting;
	private int contractsCompleted;
	private int contractsFailed;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCash() {
		return cash;
	}
	public void setCash(int cash) {
		this.cash = cash;
	}
	public int getDefaultCash() {
		return defaultCash;
	}
	public void setDefaultCash(int defaultCash) {
		this.defaultCash = defaultCash;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public int getEmployeesCount() {
		return employeesCount;
	}
	public void setEmployeesCount(int employeesCount) {
		this.employeesCount = employeesCount;
	}
	public int getContractsCompleted() {
		return contractsCompleted;
	}
	public void setContractsCompleted(int contractsCompleted) {
		this.contractsCompleted = contractsCompleted;
	}
	public int getContractsFailed() {
		return contractsFailed;
	}
	public void setContractsFailed(int contractsFailed) {
		this.contractsFailed = contractsFailed;
	}
	public int getContractsExecuting() {
		return contractsExecuting;
	}
	public void setContractsExecuting(int contractsExecuting) {
		this.contractsExecuting = contractsExecuting;
	}
	
	
}
