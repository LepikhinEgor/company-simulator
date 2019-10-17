package controller.input;

import java.util.Arrays;

public class ChangeContractTeamData {
	private long[] hiredEmployees;
	private long[] freeEmployees;
	private long contractId;
	
	public long[] getHiredEmployees() {
		return hiredEmployees;
	}
	public void setHiredEmployees(long[] hiredEmployees) {
		this.hiredEmployees = hiredEmployees;
	}
	public long[] getFreeEmployees() {
		return freeEmployees;
	}
	public void setFreeEmployees(long[] freeEmployees) {
		this.freeEmployees = freeEmployees;
	}
	public long getContractId() {
		return contractId;
	}
	public void setContractId(long contractId) {
		this.contractId = contractId;
	}
	@Override
	public String toString() {
		return "ChangeContractTeamData [hiredEmployees=" + Arrays.toString(hiredEmployees) + ", freeEmployees="
				+ Arrays.toString(freeEmployees) + ", contractId=" + contractId + "]";
	}
}
