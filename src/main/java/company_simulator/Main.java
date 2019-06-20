package company_simulator;

import java.sql.SQLException;

import dao.CompanyDao;
import dao.ContractDao;
import dao.EmployeeDao;
import dao.UserDao;
import dao.WorkPositionDao;
import domain.Contract;
import domain.Employee;
import domain.User;
import domain.UserCompany;

public class Main {
	public static void main(String[] args) {
		User user = new User("EgorL", "root");
		UserDao userDao = new UserDao();
		long newUserId = 0;
		try {
			newUserId = userDao.recordUser("EgorL", "root");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		CompanyDao companyDao = new CompanyDao();
		long newUserCompanyId = 0;
		try {
			newUserCompanyId = companyDao.recordCompany(newUserId, "apple");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		EmployeeDao employeeDao = new EmployeeDao();
		Employee employee1 = new Employee("Ivan Petrov", 25, "male", 55, 10000, "Ivan Petrov Syka");
		long newEmployeId = 0;
		try {
			newEmployeId = employeeDao.createEmployee(employee1, newUserCompanyId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ContractDao contractDao = new ContractDao();
		Contract contract = new Contract("Ardublock", 50000, 1000, 10, "dev ardublock");
		long newContractId = 0;
		try {
			newContractId = contractDao.recordContract(contract, newUserCompanyId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		WorkPositionDao workPositionDao = new WorkPositionDao();
		try {
			workPositionDao.recordWorkPosition(newEmployeId, newContractId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(newUserCompanyId);
	}
}
