package services.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

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
	
	public static void main(String[] args) {
		EmployeeRandomGenerator employeeGenerator = new EmployeeRandomGenerator();
		System.out.println(employeeGenerator.generateEmployee(0));
	}
}
