package controller.input;

import java.util.Arrays;

public class ChangeContractTeamData {
	private int[] hiredEmployees;
	private int[] freeEmployees;
	
	public int[] getHiredEmployees() {
		return hiredEmployees;
	}
	public void setHiredEmployees(int[] hiredEmployees) {
		this.hiredEmployees = hiredEmployees;
	}
	public int[] getFreeEmployees() {
		return freeEmployees;
	}
	public void setFreeEmployees(int[] freeEmployees) {
		this.freeEmployees = freeEmployees;
	}
	@Override
	public String toString() {
		return "ChangeContractTeamData [hiredEmployees=" + Arrays.toString(hiredEmployees) + ", freeEmployees="
				+ Arrays.toString(freeEmployees) + "]";
	}
}
