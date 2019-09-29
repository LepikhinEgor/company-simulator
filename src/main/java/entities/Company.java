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
	
}
