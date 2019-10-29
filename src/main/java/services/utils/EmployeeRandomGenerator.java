package services.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import entities.Employee;

@Service
public class EmployeeRandomGenerator {
	private final int JUNS_MAX_COUNT = 20;
//	private final int MIDDLES_MAX_C
	
	private final double POPULARITY_COEF = 0.5;
	private final double RESPECT_COEF = 0.8;
	
	private final int JUNIOR = 0;
	private final int MIDDLE = 1;
	private final int SENIOR = 2;

	public List<Employee> generateEmployeesList(double companyPopularity, double companyRespect) {
		List<Employee> employeesList = new ArrayList<Employee>();
		
		int agreeJunsCount = getAgreeJunsCount(companyPopularity, companyRespect);
		
		return employeesList;
		
	}
	
	private int getAgreeJunsCount(double companyPopularity, double companyRespect) {
		int readyJunsCount = 0;
		
		for (int jun = 0; jun < JUNS_MAX_COUNT; jun++) {
			double foundChance = Math.random()*(1 - POPULARITY_COEF) +  companyPopularity*POPULARITY_COEF;
			double agreeChance =  Math.random()*(1-RESPECT_COEF) + companyRespect*RESPECT_COEF;
			if ((foundChance * agreeChance) > 0.2 )
				readyJunsCount++;
		}
		
		return readyJunsCount;
	}
	
	private Employee generateEmployee(int qualification) {
		Employee employee = new Employee();
		
		Random random = new Random();
		
		int perfomance = 0;
		int salary = 0;
		
		int minPerfomance = 0;
		int randPerfomance = 0;
		
		int minSalary = 0;
		int randSalary = 0;
		
		switch (qualification) {
		case JUNIOR:
			minPerfomance = 10;
			randPerfomance = random.nextInt(15);
			perfomance = minPerfomance + randPerfomance;
			minSalary = 25;
			randSalary = random.nextInt(25);
			salary = minSalary + randSalary;
			break;
		case MIDDLE:
			minPerfomance = 25;
			randPerfomance = random.nextInt(35);
			perfomance = minPerfomance + randPerfomance;
			minSalary = 50;
			randSalary = random.nextInt(50);
			salary = minSalary + randSalary;
			break;
		case SENIOR:
			minPerfomance = 60;
			randPerfomance = random.nextInt(40);
			perfomance = minPerfomance + randPerfomance;
			minSalary = 100;
			randSalary = random.nextInt(100);
			salary = minSalary + randSalary;
			break;
		}
		employee.setPerfomance(perfomance);
		employee.setSalary(salary);
		
		int minAge = 14;
		int age = minAge + random.nextInt(66);
		employee.setAge(age);
		
		String sex = Math.random() > 0.5? Employee.MALE : Employee.FEMALE;
		employee.setSex(sex);
		
		int nameNum = random.nextInt(50);
		
		return employee;
		
	}
}
