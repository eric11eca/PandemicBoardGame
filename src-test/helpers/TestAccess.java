package helpers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import data.GameColor;
import game.Game;
import game.city.City;
import game.city.CubeData;

public class TestAccess {
	public CubeData getCityDisease(City c) {
		try {
			Field f = City.class.getDeclaredField("disease");
			f.setAccessible(true);
			return (CubeData) f.get(c);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void resetGame() {
		try {
			Field f = Game.class.getDeclaredField("instance");
			f.setAccessible(true);
			f.set(null, null);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public CubeData getGameCubeData() {
		try {
			Field f = Game.class.getDeclaredField("diseaseCubes");
			f.setAccessible(true);
			return (CubeData) f.get(Game.getInstance());
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public int getGameOutbreakMark() {
		try {
			Field f = Game.class.getDeclaredField("outbreakMark");
			f.setAccessible(true);
			return (int) f.get(Game.getInstance());
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setGameOutbreakMark(int mark) {
		try {
			Field f = Game.class.getDeclaredField("outbreakMark");
			f.setAccessible(true);
			f.set(Game.getInstance(), mark);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

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
}
