package services.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class EnglishLocalization extends LocalizationResources{

	@Override
	public ResourceBundle getEmployeesBundle() {
		return ResourceBundle.getBundle(EMPLOYEES_BUNDLE_PATH, Locale.ENGLISH);
	}

	@Override
	public ResourceBundle getContractsBundle() {
		return null;
	}

}
