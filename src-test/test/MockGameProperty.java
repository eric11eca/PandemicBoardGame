package test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import data.GameProperty;

public class MockGameProperty {
	Map<String, String> map;

	public MockGameProperty() {
		map = new HashMap<>();
	}

	public MockGameProperty put(String key, String value) {
		map.put(key, value);
		return this;
	}

	public void inject() {
		String propertyString = getPropertyString(map);
		InputStream propertyInputStream = getInputStreamFromString(propertyString);
		GameProperty instance = createInstance(propertyInputStream);
		injectProperty(instance);
	}

	public void injectFile(File file) throws FileNotFoundException {
		GameProperty instance = createInstance(new FileInputStream(file));
		injectProperty(instance);
	}

	public void resetAndEject() {
		map.clear();
		injectProperty(null);
	}

	private String getPropertyString(Map<String, String> properties) {
		StringBuilder builder = new StringBuilder();
		properties
				.forEach((key, value) -> builder.append(key).append("=").append(value).append(System.lineSeparator()));
		return builder.toString();
	}

	private InputStream getInputStreamFromString(String string) {
		return new ByteArrayInputStream(string.getBytes());
	}

	private GameProperty createInstance(InputStream propertyInputStream) {
		try {
			Constructor<GameProperty> constructor = GameProperty.class.getDeclaredConstructor(InputStream.class);
			constructor.setAccessible(true);
			return constructor.newInstance(propertyInputStream);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private void injectProperty(GameProperty property) {
		try {
			Field f = GameProperty.class.getDeclaredField("instance");
			f.setAccessible(true);
			f.set(null, property);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
