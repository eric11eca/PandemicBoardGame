package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameProperty {
	private static final String PROPERTIES_FILE = "game.properties";
	private Properties properties;

	private GameProperty(InputStream stream) throws IOException {
		properties = new Properties();
		properties.load(stream);
	}

	public int getInt(String name) {
		return Integer.parseInt(get(name));
	}

	public String get(String name) {
		return properties.getProperty(name);
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
