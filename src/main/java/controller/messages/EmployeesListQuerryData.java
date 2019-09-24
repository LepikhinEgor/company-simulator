package controller.messages;

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
	
}
