package domain;

import java.util.ArrayList;

public class UserCompany {
	
	private final long DEFAULT_CASH = 1000000; 
	
	private String name;
	private User userOwner;
	private CompanyTeam team;
	private long cash;
	private ArrayList<Contract> contracts;
	
	public UserCompany(String name) {
		this.name = name;
		//this.userOwner = owner;
		this.team = new CompanyTeam();
		this.cash = DEFAULT_CASH;
		this.contracts = new ArrayList<Contract>();
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
}
