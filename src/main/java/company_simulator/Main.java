package company_simulator;

import java.sql.SQLException;

import dao.CompanyDao;
import dao.EmployeeDao;
import dao.UserDao;
import domain.Employee;
import domain.User;
import domain.UserCompany;

public class Main {
	public static void main(String[] args) {
		User user = new User("EgorL", "root");
		UserDao userDao = new UserDao();
		long newUserId = 0;
		try {
			newUserId = userDao.createNewUser("EgorL", "root");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		CompanyDao companyDao = new CompanyDao();
		long newUserCompanyId = 0;
		try {
			newUserCompanyId = companyDao.createCompany(newUserId, "apple");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		EmployeeDao employeeDao = new EmployeeDao();
		Employee employee1 = new Employee("Ivan Petrov", 25, 55, 10000, "Ivan Petrov Syka");
		try {
			employeeDao.createEmployee(employee1, newUserCompanyId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(newUserCompanyId);
	}
}
