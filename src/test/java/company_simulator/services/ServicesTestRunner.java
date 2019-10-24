package company_simulator.services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import company_simulator.services.utils.EntitiesConventerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({EmployeeServiceTest.class,
	UserServiceTest.class,
	CompanyServiceTest.class,
	ContractsServiceTest.class,
	EntitiesConventerTest.class})
public class ServicesTestRunner {
	
}
