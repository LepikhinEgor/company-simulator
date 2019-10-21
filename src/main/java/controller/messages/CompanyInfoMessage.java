package controller.messages;

public class CompanyInfoMessage extends Message {
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
		super();
	}
	
	public CompanyInfoMessage(int status) {
		this.status = status;
		
		switch(status) {
		case SUCCESS: message = "Success return company info";break;
		case FAIL: message = "Fail getting company info";break;
		}
	}
	
	public CompanyInfoMessage(int status, String message) {
		super(status, message);
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

	@Override
	public String toString() {
		return "CompanyInfoMessage [id=" + id + ", name=" + name + ", cash=" + cash + ", defaultCash=" + defaultCash
				+ ", ownerName=" + ownerName + ", employeesCount=" + employeesCount + ", contractsExecuting="
				+ contractsExecuting + ", contractsCompleted=" + contractsCompleted + ", contractsFailed="
				+ contractsFailed + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (cash ^ (cash >>> 32));
		result = prime * result + contractsCompleted;
		result = prime * result + contractsExecuting;
		result = prime * result + contractsFailed;
		result = prime * result + (int) (defaultCash ^ (defaultCash >>> 32));
		result = prime * result + employeesCount;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((ownerName == null) ? 0 : ownerName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanyInfoMessage other = (CompanyInfoMessage) obj;
		if (cash != other.cash)
			return false;
		if (contractsCompleted != other.contractsCompleted)
			return false;
		if (contractsExecuting != other.contractsExecuting)
			return false;
		if (contractsFailed != other.contractsFailed)
			return false;
		if (defaultCash != other.defaultCash)
			return false;
		if (employeesCount != other.employeesCount)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ownerName == null) {
			if (other.ownerName != null)
				return false;
		} else if (!ownerName.equals(other.ownerName))
			return false;
		return true;
	}
	
	
}
