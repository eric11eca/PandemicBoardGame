package helpers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import data.GameColor;
import game.Game;
import game.city.City;
import game.city.CubeData;
import render.RenderCity;

public class TestAccess {
	public CubeData getCityDisease(City c) {
		return (CubeData) accessField(City.class, c, "disease", CubeData.class);
	}

	public void resetGame() {
		Game.reset();
	}

	public CubeData getGameCubeData() {
		return (CubeData) accessField(Game.class, Game.getInstance(), "diseaseCubes", CubeData.class);
	}

	public int getGameOutbreakMark() {
		return Game.getInstance().getOutbreakMark();
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

	@SuppressWarnings("unchecked")
	public Set<City> getCityNeighborSet(City c) {
		return (Set<City>) accessField(City.class, c, "neighbors", Set.class);
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
}
