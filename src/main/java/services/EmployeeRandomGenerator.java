package services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.GeneratedEmployeesDao;
import entities.Employee;
import exceptions.DatabaseAccessException;

@Service
public class EmployeeRandomGenerator {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeRandomGenerator.class);
	
	private final int JUNS_MAX_COUNT = 15;
	private final int MIDDLES_MAX_COUNT = 10;
	private final int SENIORS_MAX_COUNT = 5;
	
	private final double POPULARITY_COEF = 0.5;
	private final double RESPECT_COEF = 0.8;
	
	private final int JUNIOR = 0;
	private final int MIDDLE = 1;
	private final int SENIOR = 2;
	
	private final double JUNIOR_AGREE_MIN_CHANCE = 0.2;
	private final double MIDDLE_AGREE_MIN_CHANCE = 0.3;
	private final double SENIOR_AGREE_MIN_CHANCE = 0.35;
	
	private GeneratedEmployeesDao generatedEmployeesDao;
	
	@Autowired
	public void setGeneratedEmployeesDao(GeneratedEmployeesDao dao) {
		this.generatedEmployeesDao = dao;
	}
	
	private long getCurrentGenerateEmployeesTiming(TimeZone timeZone) {
		long timing = 0;
		
		Calendar calendar = new GregorianCalendar(timeZone);
		long timestamp = calendar.getTimeInMillis();

		timing = timestamp / (1000*60* 60 * 4); //4 hour
		
		return timing;
	}
	
	private long getEmployeesRecordTiming(long companyId) throws DatabaseAccessException {
		long oldTiming = 0;
		
		try {
			oldTiming = generatedEmployeesDao.getEmployeesRecordTiming(companyId);
		} catch (SQLException e) {
			logger.info(e.getMessage(), e);
			throw new DatabaseAccessException("Exception getting last employees record timing");
		}
		
		return oldTiming;
	}
	
	private List<Employee> getOldGeneratedEmployees(long companyId) throws DatabaseAccessException {
		List<Employee> employees = null;
		
		try {
			employees = generatedEmployeesDao.getOldGeneratedEmployees(companyId);
		} catch (SQLException e) {
			logger.info(e.getMessage(), e);
			throw new DatabaseAccessException("Error getting old generated employees");
		}
		
		return employees;
	}
	
	public List<Employee> getGeneratedEmployees(double companyPopularity, double companyRespect, long companyId, TimeZone timezone) throws DatabaseAccessException {
		List<Employee> employees = null;
		
		long curTiming = getCurrentGenerateEmployeesTiming(timezone);
		long oldTiming = getEmployeesRecordTiming(companyId);
		
		if (curTiming != oldTiming) {
			employees = generateEmployeesList(companyPopularity, companyRespect, companyId, curTiming);
		} else {
			employees = getOldGeneratedEmployees(companyId);
		}
		
		return employees;
	}

	public List<Employee> generateEmployeesList(double companyPopularity, double companyRespect, long companyId, long timing) throws DatabaseAccessException {
		List<Employee> employeesList = new ArrayList<Employee>();
		
		int agreeJunsCount = getAgreeEmployeesCount(companyPopularity, companyRespect, JUNS_MAX_COUNT, JUNIOR_AGREE_MIN_CHANCE);
		int agreeMiddlesCount = getAgreeEmployeesCount(companyPopularity, companyRespect, MIDDLES_MAX_COUNT, MIDDLE_AGREE_MIN_CHANCE);
		int agreeSeniorsCount = getAgreeEmployeesCount(companyPopularity, companyRespect, SENIORS_MAX_COUNT, SENIOR_AGREE_MIN_CHANCE);
		
		for (int i = 0; i < agreeJunsCount; i++) {
			employeesList.add(generateEmployee(JUNIOR));
		}
		for (int i = 0; i < agreeMiddlesCount; i++) {
			employeesList.add(generateEmployee(MIDDLE));
		}
		for (int i = 0; i < agreeSeniorsCount; i++) {
			employeesList.add(generateEmployee(SENIOR));
		}
		
		recordGeneratedEmployees(employeesList, companyId, timing);
		
		return employeesList;
	}
	
	private void recordGeneratedEmployees(List<Employee> employees, long companyId, long timing) throws DatabaseAccessException {
		try {
			generatedEmployeesDao.recordGeneratedEmployees(employees, companyId, timing);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DatabaseAccessException("Error recording new generated employees");
		}
	}
	
	private int getAgreeEmployeesCount(double companyPopularity, double companyRespect, int max, double minChance) {
		int readyJunsCount = 0;
		
		for (int jun = 0; jun < max; jun++) {
			double foundChance = Math.random()*(1 - POPULARITY_COEF) +  companyPopularity*POPULARITY_COEF;
			double agreeChance =  Math.random()*(1-RESPECT_COEF) + companyRespect*RESPECT_COEF;
			if ((foundChance * agreeChance) > minChance )
				readyJunsCount++;
		}
		
		return readyJunsCount;
	}
	
	public Employee generateEmployee(int qualification) {
		Employee employee = new Employee();
		
		int perfomance = generatePerfomance(qualification);
		int salary = generateSalary(qualification);
		int age = generateAge();
		String sex = generateSex();
		String name = generateName(sex);
		String description = generateDescription(sex);
		
		employee.setPerfomance(perfomance);
		employee.setSalary(salary);
		employee.setAge(age);
		employee.setSex(sex);
		employee.setName(name);
		employee.setDescription(description);
		
		return employee;
	}
	
	private int generateSalary(int qualification) {
		int salary = 0;
		
		Random random = new Random();
		
		int minSalary = 0;
		int randSalary = 0;
		
		switch (qualification) {
		case JUNIOR:
			minSalary = 25;
			randSalary = random.nextInt(25);
			salary = minSalary + randSalary;
			break;
		case MIDDLE:
			minSalary = 50;
			randSalary = random.nextInt(50);
			salary = minSalary + randSalary;
			break;
		case SENIOR:
			minSalary = 100;
			randSalary = random.nextInt(100);
			salary = minSalary + randSalary;
			break;
		}
		
		return salary;
	}
	
	private int generatePerfomance(int qualification) {
		int perfomance = 0;
		
		Random random = new Random();
		
		int minPerfomance = 0;
		int randPerfomance = 0;
		
		switch (qualification) {
		case JUNIOR:
			minPerfomance = 10;
			randPerfomance = random.nextInt(15);
			perfomance = minPerfomance + randPerfomance;
			break;
		case MIDDLE:
			minPerfomance = 25;
			randPerfomance = random.nextInt(35);
			perfomance = minPerfomance + randPerfomance;
			break;
		case SENIOR:
			minPerfomance = 60;
			randPerfomance = random.nextInt(40);
			perfomance = minPerfomance + randPerfomance;
			break;
		}
		
		return perfomance;
	}
	
	private int generateAge() {
		Random random = new Random();
		
		int minAge = 14;
		int randAge = random.nextInt(66);
		if (randAge < 6 || randAge > 31) //employee age [20;45] more chance
			randAge = random.nextInt(66);
		
		int age = minAge + randAge;
		
		return age;
	}
	
	private String generateName(String sex) {
		String fullname = "";
		
		Random random = new Random();
		
		String nameKey = "";
		String surnameKey = "";
		switch(sex) {
		case Employee.MALE: nameKey = "male.name_"; surnameKey = "male.surname_";break;
		case Employee.FEMALE: nameKey = "female.name_"; surnameKey = "female.surname_";break;
		}
		int nameNum = random.nextInt(50) + 1;
		nameKey += nameNum;
		int surnameNum = random.nextInt(50) + 1;
		surnameKey += surnameNum;
		
		ResourceBundle bundle = ResourceBundle.getBundle("locale/employees/employees",  new Locale("ru", "RU"));
		String name = bundle.getString(nameKey);
		String surname = bundle.getString(surnameKey);
		
		fullname = name + " " + surname;
		
		return fullname;
	}
	
	private String generateDescription(String sex) {
		String description = "";
		
		Random random = new Random();
		
		String descriptionKey = "";
		switch(sex) {
		case Employee.MALE: descriptionKey = "male.description_";break;
		case Employee.FEMALE: descriptionKey = "female.description_";break;
		}
		int descriptionNum = random.nextInt(10) + 1;
		descriptionKey += descriptionNum;
		
		ResourceBundle bundle = ResourceBundle.getBundle("locale/employees/employees",  new Locale("ru", "RU"));
		description = bundle.getString(descriptionKey);
		
		return description;
	}
	
	private String generateSex() {
		return Math.random() > 0.5? Employee.MALE : Employee.FEMALE;
	}
}
