package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class GameProperty {
	private static final String PROPERTIES_FILE = "game.properties";
	private Properties properties;

	private GameProperty(InputStream stream) throws IOException {
		properties = new Properties();
		properties.load(stream);
	}

	/**
	 * Get the game property as an int. See {@code get(String)}
	 * 
	 * @param name
	 * @return
	 */
	public int getInt(String name) {
		return Integer.parseInt(get(name));
	}

	/**
	 * Get the game property as an int array. See {@code get(String)}
	 * 
	 * @param name
	 * @return
	 */
	public int[] getIntArray(String name) {
		String[] rawRates = get(name).split(",");
		int[] rates = new int[rawRates.length];
		for (int i = 0; i < rates.length; i++) {
			rates[i] = Integer.parseInt(rawRates[i]);
		}
		return rates;
	}

	/**
	 * Get the game property with the specified name.
	 * 
	 * @param name name of the property
	 * @return value of the property as string
	 * @throws NullPointerException if the property doesn't exist
	 */
	public String get(String name) {
		return Objects.requireNonNull(properties.getProperty(name));
	}

	private static GameProperty instance;

	public static GameProperty getInstance() {
		if (instance == null) {
			try {
				File file = new File(PROPERTIES_FILE);
				instance = new GameProperty(new FileInputStream(file));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return instance;
	}
}
