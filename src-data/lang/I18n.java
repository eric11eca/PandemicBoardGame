package lang;

import java.util.IllegalFormatException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class I18n {
	private static final String BUNDLE_NAME = "lang.lang";

	public static ResourceBundle resourceBundle;

	public I18n(Locale locale) {
		resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
	}

	public String format(String key, Object... args) {
		try {
			String format = resourceBundle.getString(key);
			return String.format(format, args);
		} catch (MissingResourceException e) {
			e.printStackTrace();
			return '!' + key + '!';
		} catch (IllegalFormatException e) {
			e.printStackTrace();
			return '?' + key + '?';
		}
	}

}
