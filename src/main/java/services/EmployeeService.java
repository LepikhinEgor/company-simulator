package services;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aspects.annotations.Loggable;
import dao.EmployeeDao;
import entities.Employee;
import exceptions.DatabaseAccessException;

@Service
public class EmployeeService {
	
	private static final int EMPLOYEES_PAGE_LIMIT = 10;
	
	@Autowired
	EmployeeDao employeeDao;
	
	@Loggable
	public Employee[] getEmployeesList(long companyId, int orderNum,int pageNum) throws DatabaseAccessException {
		try {
			return employeeDao.getEmployeesList(companyId, orderNum, pageNum, EMPLOYEES_PAGE_LIMIT);
		} catch (SQLException e) {
			throw new DatabaseAccessException("Error trying to get employees list");
		}
	}
}
