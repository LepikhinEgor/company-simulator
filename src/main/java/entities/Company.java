package entities;

import java.util.ArrayList;

public class Company {
	
	private final long DEFAULT_CASH = 1000000; 
	
	private long id;

	private String name;
	private User userOwner;
	private CompanyTeam team;
	private long cash;
	private ArrayList<Contract> contracts;
	
	public Company(String name) {
		this.name = name;
		this.team = new CompanyTeam();
		this.cash = DEFAULT_CASH;
		this.contracts = new ArrayList<Contract>();
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public User getUserOwner() {
		return userOwner;
	}
	
	public void setUserOwner(User userOwner) {
		this.userOwner = userOwner;
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
	
	public long getDEFAULT_CASH() {
		return DEFAULT_CASH;
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
}
