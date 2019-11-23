package entities;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Company {
	
	private final long DEFAULT_CASH = 1000000; 
	private final String DEFAULT_NAME = "My company";
	
	private long id;

	private String name;
	private long ownerId;
	private CompanyTeam team;
	private long cash;
	private Timestamp cashUpdatedTiming;
	
	public Company() {
		this.name = DEFAULT_NAME;
		this.cash = DEFAULT_CASH;
		this.team = new CompanyTeam();
	}
	
	public Timestamp getCashUpdatedTiming() {
		return cashUpdatedTiming;
	}

	public void setCashUpdatedTiming(Timestamp cashUpdatedTiming) {
		this.cashUpdatedTiming = cashUpdatedTiming;
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
		return "Company [DEFAULT_CASH=" + DEFAULT_CASH + ", DEFAULT_NAME=" + DEFAULT_NAME + ", id=" + id + ", name="
				+ name + ", ownerId=" + ownerId + ", team=" + team + ", cash=" + cash + ", cashUpdatedTiming="
				+ cashUpdatedTiming + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (DEFAULT_CASH ^ (DEFAULT_CASH >>> 32));
		result = prime * result + ((DEFAULT_NAME == null) ? 0 : DEFAULT_NAME.hashCode());
		result = prime * result + (int) (cash ^ (cash >>> 32));
		result = prime * result + ((cashUpdatedTiming == null) ? 0 : cashUpdatedTiming.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (ownerId ^ (ownerId >>> 32));
		result = prime * result + ((team == null) ? 0 : team.hashCode());
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
		if (DEFAULT_CASH != other.DEFAULT_CASH)
			return false;
		if (DEFAULT_NAME == null) {
			if (other.DEFAULT_NAME != null)
				return false;
		} else if (!DEFAULT_NAME.equals(other.DEFAULT_NAME))
			return false;
		if (cash != other.cash)
			return false;
		if (cashUpdatedTiming == null) {
			if (other.cashUpdatedTiming != null)
				return false;
		} else if (!cashUpdatedTiming.equals(other.cashUpdatedTiming))
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
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}
	
}
