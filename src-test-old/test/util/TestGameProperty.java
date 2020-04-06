package test.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import data.GameProperty;

public class TestGameProperty {
	Map<String, String> map;

	public TestGameProperty() {
		map = new HashMap<>();
	}

	public void put(String key, String value) {
		map.put(key, value);
	}

	public void inject() {
		String propertyString = getPropertyString(map);
		InputStream propertyInputStream = getInputStreamFromString(propertyString);
		GameProperty instance = createInstance(propertyInputStream);
		injectProperty(instance);
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
