package services.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import entities.Employee;

@Service
public class EmployeeRandomGenerator {
	private final int JUNS_MAX_COUNT = 20;
//	private final int MIDDLES_MAX_C
	
	private final double POPULARITY_COEF = 0.5;
	private final double RESPECT_COEF = 0.8;

	public List<Employee> generateEmployeesList(double companyPopularity, double companyRespect) {
		List<Employee> employeesList = new ArrayList<Employee>();
		
		int agreeJunsCount = getAgreeJunsCount(companyPopularity, companyRespect);
		
		return employeesList;
		
	}
	
	private int getAgreeJunsCount(double companyPopularity, double companyRespect) {
		double popularityImportanceCoef = 0.5;
		
		int readyJunsCount = 0;
		for (int jun = 0; jun < JUNS_MAX_COUNT; jun++) {
			double foundChance = Math.random()*(1 - POPULARITY_COEF) +  companyPopularity*POPULARITY_COEF;
			double agreeChance =  Math.random()*(1-RESPECT_COEF) + companyRespect*RESPECT_COEF;
			if ((foundChance * agreeChance) > 0.2 )
				readyJunsCount++;
		}
		
		return readyJunsCount;
	}
}
