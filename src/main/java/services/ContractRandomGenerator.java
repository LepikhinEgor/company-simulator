package services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import entities.Contract;

@Service
public class ContractRandomGenerator {

	private static final double POPULARITY_COEF= 0.8;
	private static final double RESPECT_COEF= 0.8;
	
	private static final int MAX_CONTRACTS = 5;
	private static final double MIN_CHANCE = 0.4;
	
	private static final int MIN_CONTRACT_SIZE = 1000;
	private static final int MAX_CONTRACT_SIZE = 1000000;
	
	private static final int AVERAGE_SALARY = 75;
	
	public List<Contract> generateNewContracts(double popularity, double respect, long companyId) {
		List<Contract> contracts = new ArrayList<Contract>();
		
		int contractsCount = generateContractsCount(popularity, respect, MAX_CONTRACTS, MIN_CHANCE);
		for (int i = 0; i < contractsCount; i++)
			contracts.add(generateContract(popularity, respect, companyId));
		
		return contracts;
	}
	
	private int generateContractsCount(double companyPopularity, double companyRespect, int max, double minChance) {
		int readyJunsCount = 0;
		
		for (int jun = 0; jun < max; jun++) {
			double foundChance = Math.random()*(1 - POPULARITY_COEF) +  companyPopularity*POPULARITY_COEF;
			double agreeChance =  Math.random()*(1-RESPECT_COEF) + companyRespect*RESPECT_COEF;
			if ((foundChance * agreeChance) > minChance)
				readyJunsCount++;
		}
		
		return readyJunsCount;
	}
	
	private Contract generateContract(double companyPopularity, double companyRespect, long companyId) {
		Contract contract = new Contract();
		
		int contractSize = generateContractSize(MIN_CONTRACT_SIZE, MAX_CONTRACT_SIZE);
		double urgencyChance = generateDeadlineUrgencyChance();
		Timestamp deadline = new Timestamp(generateDeadlineTimeInMinutes(contractSize, urgencyChance));
		double profitCoef = generateProfitCoef(companyPopularity, companyRespect);
		int fee = generateProfit(deadline.getTime(), profitCoef, urgencyChance);
		
		contract.setPerfomanceUnits(contractSize);
		contract.setDeadline(deadline);
		contract.setFee(fee);
		contract.setCompanyId(companyId);
		
		return contract;
	}
	
	private double generateProfitCoef(double companyPopularity, double companyRespect) {
		double profitCoef = 0;
		
		profitCoef = 0.75 + Math.random()/2 + companyPopularity/2 + companyRespect/2;
		
		return profitCoef;
	}
	
	private int generateContractSize(int minSize, int maxSize) {
		int contractSize = 0;
		
		contractSize = minSize + (int)(Math.random() * (maxSize - minSize));
		
		return contractSize;
	}
	
	private long generateDeadlineTimeInMinutes(int contractSize, double chance) {
		long time = 0;
		
		time = (int)((contractSize / 200) * chance);
		
		return time;
	}
	
	private int generateProfit(long contractDeadline, double profitCoef, double urgencyChance) {
		int profit = 0;
		
		int averagePrice = (int)contractDeadline * (AVERAGE_SALARY * 5);
		double urgencyCoef = calculateUrgencyCoef(urgencyChance);
		
		profit = (int)(averagePrice* urgencyCoef * profitCoef);
		
		return profit;
	}
	
	private double generateDeadlineUrgencyChance() {
		double chance = 0;
		
		chance =  0.75 + Math.random()*0.5;
		
		return chance;
	}
	
	private double calculateUrgencyCoef(double urgencyChance) {
		double coef = 0;
		
		coef = (1 + (1-urgencyChance));
		
		return coef;
	}
	
	public static void main(String[] args) {
		ContractRandomGenerator contractRandom = new ContractRandomGenerator();
		System.out.println(contractRandom.generateContract(0.5, 0.5, 1));
	}
}
