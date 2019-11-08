package company_simulator.controllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	LoginControllerTest.class,
	CompanyControllerTest.class,
	EmployeesControllerTest.class,
	ContractsControllerTest.class
	})
public class ControllersTestRunner {

}
