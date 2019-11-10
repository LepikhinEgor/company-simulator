package services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import aspects.annotations.Loggable;
import entities.Contract;

@Service
public class ContractRandomGenerator {

	private static final double POPULARITY_COEF= 0.5;
	private static final double RESPECT_COEF= 0.5;
	
	private static final int MAX_SMALL_CONTRACTS = 5;
	private static final int MAX_MEDIUM_CONTRACTS = 2;
	private static final int MAX_LARGE_CONTRACTS = 1;
	private static final double MIN_CHANCE = 0.09;
	
	private static final int SMALL_CONTRACT = 0;
	private static final int MEDIUM_CONTRACT = 1;
	private static final int LARGE_CONTRACT = 3;
	private static final int MIN_SMALL_CONTRACT_TIME = 10;
	private static final int MAX_SMALL_CONTRACT_SIZE = 2*60;
	private static final int MIN_MEDIUM_CONTRACT_TIME = 2*60;
	private static final int MAX_MEDIUM_CONTRACT_SIZE = 24*60;
	private static final int MIN_LARGE_CONTRACT_TIME = 24*60;
	private static final int MAX_LARGE_CONTRACT_SIZE = 3*24*60;
	
	private static final double SMALL_CONTRACT_PROFIT_COEF = 1.2;
	private static final double MEDIUM_CONTRACT_PROFIT_COEF = 1;
	private static final double LARGE_CONTRACT_PROFIT_COEF = 0.8;
	
	private static final int AVERAGE_TEAM_PERFOMANCE = 150;
	private static final int AVERAGE_SALARY = 60;
	
	@Loggable
	public List<Contract> generateNewContracts(double popularity, double respect, long companyId) {
		List<Contract> contracts = new ArrayList<Contract>();
		
		int smallContractsCount = generateContractsCount(popularity, respect, MAX_SMALL_CONTRACTS);
		for (int i = 0; i < MAX_SMALL_CONTRACTS; i++)
			contracts.add(generateContract(popularity, respect, companyId, SMALL_CONTRACT));
		
		int mediumContractsCount = generateContractsCount(popularity, respect, MAX_MEDIUM_CONTRACTS);
		for (int i = 0; i < MAX_MEDIUM_CONTRACTS; i++)
			contracts.add(generateContract(popularity, respect, companyId, MEDIUM_CONTRACT));
		
		int largeContractsCount = generateContractsCount(popularity, respect, MAX_LARGE_CONTRACTS);
		for (int i = 0; i < MAX_LARGE_CONTRACTS; i++)
			contracts.add(generateContract(popularity, respect, companyId, LARGE_CONTRACT));
		
		return contracts;
	}
	
	private int generateContractsCount(double companyPopularity, double companyRespect, int max) {
		int readyJunsCount = 0;
		
		for (int jun = 0; jun < max; jun++) {
			double foundChance = Math.random()*(1 - POPULARITY_COEF) +  companyPopularity*POPULARITY_COEF;
			double agreeChance =  Math.random()*(1 - RESPECT_COEF) + companyRespect*RESPECT_COEF;
			if ((foundChance * agreeChance) > MIN_CHANCE)
				readyJunsCount++;
		}
		
		return readyJunsCount;
	}
	
	private Contract generateContract(double companyPopularity, double companyRespect, long companyId, int contractSizeNum) {
		Contract contract = new Contract();
		
		Random random = new Random();
		int nameNum = random.nextInt(13) + 1;
		String name = "name_" + nameNum;
		String description = "description_" + nameNum;
		
		int contractSize = generateContractSize(contractSizeNum);
		double urgencyChance = generateDeadlineUrgencyChance();
		Timestamp deadline = new Timestamp(generateDeadline(contractSize, urgencyChance));
		double profitCoef = generateProfitCoef(companyPopularity, companyRespect);
		int fee = generateProfit(deadline.getTime(), profitCoef, urgencyChance, contractSizeNum);
		
		contract.setPerfomanceUnits(contractSize);
		contract.setDeadline(deadline);
		contract.setFee(fee);
		contract.setCompanyId(companyId);
		contract.setName(name);
		contract.setDescription(description);
		
		return contract;
	}
	
	private double generateProfitCoef(double companyPopularity, double companyRespect) {
		double profitCoef = 0;
		
		profitCoef = 0.75 + Math.random()/2 + companyPopularity/2 + companyRespect/2;
		
		return profitCoef;
	}
	
	private int generateContractSize(int contractSizeNum) {
		int contractSize = 0;
		
		int minSize = 0;
		int maxSize = 0;
		
		switch (contractSizeNum) {
		case SMALL_CONTRACT:
			minSize = MIN_SMALL_CONTRACT_TIME * AVERAGE_TEAM_PERFOMANCE;
			maxSize = MAX_SMALL_CONTRACT_SIZE * AVERAGE_TEAM_PERFOMANCE;
			break;
		case MEDIUM_CONTRACT:
			minSize = MIN_MEDIUM_CONTRACT_TIME * AVERAGE_TEAM_PERFOMANCE;
			maxSize = MAX_MEDIUM_CONTRACT_SIZE * AVERAGE_TEAM_PERFOMANCE;
			break;
		case LARGE_CONTRACT:
			minSize = MIN_LARGE_CONTRACT_TIME * AVERAGE_TEAM_PERFOMANCE;
			maxSize = MAX_LARGE_CONTRACT_SIZE * AVERAGE_TEAM_PERFOMANCE;
			break;
		}
		
		contractSize = minSize + (int)(Math.random() * (maxSize - minSize));
		
		return contractSize;
	}
	
	private long generateDeadline(int contractSize, double chance) {
		long time = 0;
		
		time = (int)((contractSize / 200) * chance) * (60 * 1000);
		
		return time;
	}
	
	private int generateProfit(long contractDeadline, double profitCoef, double urgencyChance, int contractSize) {
		int profit = 0;
		
		double sizeProfitCoef = 0;
		switch (contractSize) {
		case SMALL_CONTRACT:
			sizeProfitCoef = SMALL_CONTRACT_PROFIT_COEF;
			break;
		case MEDIUM_CONTRACT:
			sizeProfitCoef = MEDIUM_CONTRACT_PROFIT_COEF;
			break;
		case LARGE_CONTRACT:
			sizeProfitCoef = LARGE_CONTRACT_PROFIT_COEF;
			break;
		}
		
		int deadlineInMinutes = (int)contractDeadline / (60 * 1000);
		int averagePrice = (int)deadlineInMinutes * (AVERAGE_SALARY * 5);
		double urgencyCoef = calculateUrgencyCoef(urgencyChance);
		
		profit = (int)(averagePrice* urgencyCoef * profitCoef * sizeProfitCoef);
		
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
		for (Contract contrract :contractRandom.generateNewContracts(0.2, 0.2, 1)) {
			System.out.println(contrract);
		};
	}
}
