package services.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public abstract class LocalizationResources {
	protected final String EMPLOYEES_BUNDLE_PATH = "locale/employees/employees";
	protected final String CONTRACTS_BUNDLE_PATH = "locale/contracts/contracts";
	
	public abstract ResourceBundle getEmployeesBundle();
	public abstract ResourceBundle getContractsBundle();
	
	public static LocalizationResources getLocalizationClass(Locale locale) {
		if (locale == null)
			throw new IllegalArgumentException("Local cant be null");
		if (locale.equals(new Locale("ru", "RU")))
			return new RussianLocalization();
		else 
			return new EnglishLocalization();
	}
}
