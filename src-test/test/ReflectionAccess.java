package test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import game.GameColor;
import game.city.City;

public class ReflectionAccess {
	public void outbreak(City city, GameColor color) {
		outbreak(city, color, new HashSet<>());
	}

	public void outbreak(City city, GameColor color, Set<City> inOutBreak) {
		try {
			Method m = City.class.getDeclaredMethod("outbreak", GameColor.class, Set.class);
			m.setAccessible(true);
			m.invoke(city, color, inOutBreak);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public <T, R> R accessField(Class<T> clazz, T instance, String field, Class<R> returnType) {
		try {
			Field f = clazz.getDeclaredField(field);
			f.setAccessible(true);
			return returnType.cast(f.get(instance));
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public <T> void setField(Class<T> clazz, T instance, String field, Object value) {
		try {
			Field f = clazz.getDeclaredField(field);
			f.setAccessible(true);
			f.set(instance, value);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
