package initialize;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "initialize.messages"; 
	
	public static ResourceBundle resourceBundle; 
	
	public Messages(Locale locale) {
		resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
	}

	public String getString(String key) {
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
