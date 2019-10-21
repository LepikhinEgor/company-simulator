package entities;

import java.util.ArrayList;

public class Company {
	
	private final long DEFAULT_CASH = 1000000; 
	private final String DEFAULT_NAME = "My company";
	
	private long id;

	private String name;
	private long ownerId;
	private CompanyTeam team;
	private long cash;
	private ArrayList<Contract> contracts;
	
	public Company() {
		this.name = DEFAULT_NAME;
		this.cash = DEFAULT_CASH;
		this.team = new CompanyTeam();
		this.contracts = new ArrayList<Contract>();
	}
	
	public Company(long userId) {
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long userOwnerId) {
		this.ownerId = userOwnerId;
	}

	public CompanyTeam getTeam() {
		return team;
	}
	
	public void setTeam(CompanyTeam team) {
		this.team = team;
	}
	
	public ArrayList<Contract> getContracts() {
		return contracts;
	}
	
	public void setContracts(ArrayList<Contract> contracts) {
		this.contracts = contracts;
	}
	
	public void setCash(long cash) {
		this.cash = cash;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public long getCash() {
		return this.cash;
	}
	
	public long getDefaultCash() {
		return DEFAULT_CASH;
	}
	
	public void hireEmployee(Employee employee) {
		team.addEmployee(employee);
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", ownerId=" + ownerId + ", cash=" + cash + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (cash ^ (cash >>> 32));
		result = prime * result + ((contracts == null) ? 0 : contracts.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (ownerId ^ (ownerId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (cash != other.cash)
			return false;
		if (contracts == null) {
			if (other.contracts != null)
				return false;
		} else if (!contracts.equals(other.contracts))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ownerId != other.ownerId)
			return false;
		return true;
	}
	
}
