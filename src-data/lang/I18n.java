package lang;

import java.util.IllegalFormatException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class I18n {
	private static I18n instance;
	private static final String BUNDLE_NAME = "lang.lang";
	private static Locale locale = Locale.getDefault();

	private ResourceBundle resourceBundle;

	private I18n(String bundle) {
		resourceBundle = ResourceBundle.getBundle(bundle);
	}

	/**
	 * Localize a message
	 * 
	 * @param key
	 * @param args
	 * @return
	 */
	private String formatString(String key, Object... args) {
		try {
			String format = resourceBundle.getString(key);
			return String.format(format, args);
		} catch (MissingResourceException e) {
			return "MISSING!" + key;
		} catch (IllegalFormatException e) {
			return "FORMAT?" + key;
		}
	}

	public static String format(String key, Object... args) {
		if (instance == null) {
			instance = new I18n(BUNDLE_NAME);
		}
		return instance.formatString(key, args);
	}

}
