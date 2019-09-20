package controller.messages;

public class EmployeesListQuerryData {
	private String loginEmail;
	private int orderNum;
	private int pageNum;
	
	public String getLoginEmail() {
		return loginEmail;
	}
	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}
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
		return "EmployeesListQuerryData [loginEmail=" + loginEmail + ", orderNum=" + orderNum + ", pageNum=" + pageNum
				+ "]";
	}
	
}
