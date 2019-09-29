package controller.messages;

public class CompanyInfoMessage {
	
	public static final int SUCCESS = 0;
	public static final int FAIL = 1;
	
	private int status;
	private String message;
	
	private long id;
	private String name;
	private long cash;
	private long defaultCash;
	private String ownerName;
	private int employeesCount;
	private int contractsExecuting;
	private int contractsCompleted;
	private int contractsFailed;
	
	public CompanyInfoMessage() {
		
	}
	
	public CompanyInfoMessage(int status) {
		this.status = status;
		
		switch(status) {
		case SUCCESS: message = "Success return company info";break;
		case FAIL: message = "Fail getting company info";break;
		}
	}
	
	public CompanyInfoMessage(int status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCash() {
		return cash;
	}
	public void setCash(long cash) {
		this.cash = cash;
	}
	public long getDefaultCash() {
		return defaultCash;
	}
	public void setDefaultCash(long defaultCash) {
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
