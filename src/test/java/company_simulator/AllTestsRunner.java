package company_simulator;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import company_simulator.controllers.ControllersTestRunner;
import company_simulator.controllers.LoginControllerTest;
import company_simulator.services.ServicesTestRunner;

@RunWith(Suite.class)
@Suite.SuiteClasses({ControllersTestRunner.class, ServicesTestRunner.class})
public class AllTestsRunner {

}
