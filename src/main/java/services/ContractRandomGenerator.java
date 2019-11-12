package services;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aspects.annotations.Loggable;
import dao.GeneratedContractsDao;
import entities.Contract;
import exceptions.DatabaseAccessException;

@Service
public class ContractRandomGenerator {
	
	private static final Logger logger = LoggerFactory.getLogger(ContractRandomGenerator.class);

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
	
	private GeneratedContractsDao generatedContractsDao;
	
	@Autowired
	public void setGeneratedContractsDao(GeneratedContractsDao generatedContractsDao) {
		this.generatedContractsDao = generatedContractsDao;
	}
	
	@Loggable
	public List<Contract> getGeneratedContracts(double popularity, double respect, long companyId, TimeZone timezone) throws DatabaseAccessException {
		List<Contract> generatedContracts = null;
		
		long curTiming = getCurrentContractsGenerationTiming(timezone);
		long oldTiming = getOldContractsGenerationTiming(companyId);
		
		if (oldTiming < curTiming)
			generatedContracts = generateNewContracts(popularity, respect, companyId, timezone);
		else
			generatedContracts = getOldGeneratedContracts(companyId);
		
		return generatedContracts;
	}
	
	@Loggable
	private List<Contract> generateNewContracts(double popularity, double respect, long companyId, TimeZone timezone) throws DatabaseAccessException {
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
		
		contracts = checkContractUnic(contracts);
		
		long generationTiming = getCurrentContractsGenerationTiming(timezone);
		contracts = recordGeneratedContracts(contracts, companyId, generationTiming);
		
		return contracts;
	}
	
	private List<Contract> getOldGeneratedContracts(long companyId) throws DatabaseAccessException {
		List<Contract> contracts = null;
		
		try {
			contracts = generatedContractsDao.getGeneratedContracts(companyId);
		} catch (SQLException e) {
			throw new DatabaseAccessException("Error getting old generated contracts");
		}
		
		return contracts;
	}
	
	private long getCurrentContractsGenerationTiming(TimeZone timeZone) {
		long timing = 0;
		
		Calendar calendar = new GregorianCalendar(timeZone);
		long timestamp = calendar.getTimeInMillis();

		timing = timestamp / (1000*60* 60 * 4); //4 hour
		
		return timing;
	}
	
	private long getOldContractsGenerationTiming(long companyId) throws DatabaseAccessException {
		long oldTiming = 0;
		
		try {
			oldTiming = generatedContractsDao.getContractGenerationTiming(companyId);
		} catch (SQLException e) {
			throw new DatabaseAccessException("Error getting contracts generation timing");
		}
		
		return oldTiming;
	}
	
	private List<Contract> recordGeneratedContracts(List<Contract> contracts, long companyId, long generationTiming) throws DatabaseAccessException {
		List<Contract> recordedContracts;
		try {
			recordedContracts = generatedContractsDao.recordGeneratedContracts(contracts, companyId, generationTiming);
		} catch (SQLException e) {
			throw new DatabaseAccessException("Error recording generatedContracts to database");
		}
	
		
		return recordedContracts;
	}
	
	private List<Contract> checkContractUnic(List<Contract> contracts) {
		Set<Contract> unicContracts = new HashSet<Contract>();
		Set<Contract> filteredContracts = new HashSet<Contract>();
		
		for (Contract contractI : contracts) {
			for (Contract contractJ : contracts) {
				if (contractI.equals(contractJ))
					continue;
				for (Contract filteredContract : filteredContracts)
					if (contractI.getName().equals(filteredContract.getName()))
						continue;
					
				if (contractI.getName().equals(contractJ.getName())) {
					if (contractI.getDeadline().getTime() > contractJ.getDeadline().getTime())
						filteredContracts.add(contractI);
					else
						filteredContracts.add(contractJ);
				} 
			}
		}
		
		for (Contract contract : contracts) {
			boolean defaultUnic = true;
			for (Contract filterContract: filteredContracts) {
				if (contract.getName().equals(filterContract.getName()))
					defaultUnic = false;
			}
			if (defaultUnic)
				unicContracts.add(contract);
		}
		
		unicContracts.addAll(filteredContracts);
		
		return new ArrayList<Contract>(unicContracts);
		
		
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
		contract.setStatus("1");
		
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
}
