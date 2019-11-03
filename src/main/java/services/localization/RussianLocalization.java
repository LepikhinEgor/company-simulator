package services.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class RussianLocalization extends LocalizationResources{

	@Override
	public ResourceBundle getEmployeesBundle() {
		return ResourceBundle.getBundle(EMPLOYEES_BUNDLE_PATH, new Locale("ru", "RU"));
	}

	@Override
	public ResourceBundle getContractsBundle() {
		return null;
	}
	
}
