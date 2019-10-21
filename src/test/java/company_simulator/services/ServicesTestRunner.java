package company_simulator.services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@Suite.SuiteClasses({EmployeeServiceTest.class, UserServiceTest.class, CompanyServiceTest.class, ContractsServiceTest.class})
public class ServicesTestRunner {
	
}
