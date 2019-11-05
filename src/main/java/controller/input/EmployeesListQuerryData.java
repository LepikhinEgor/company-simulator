package controller.input;

public class EmployeesListQuerryData {
	private int orderNum;
	private int pageNum;
	
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	@Override
	public String toString() {
		return "EmployeesListQuerryData [" + "orderNum=" + orderNum + ", pageNum=" + pageNum
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + orderNum;
		result = prime * result + pageNum;
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
		EmployeesListQuerryData other = (EmployeesListQuerryData) obj;
		if (orderNum != other.orderNum)
			return false;
		if (pageNum != other.pageNum)
			return false;
		return true;
	}
	
}
