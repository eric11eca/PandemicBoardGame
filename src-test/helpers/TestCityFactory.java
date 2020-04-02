package helpers;

import java.util.HashSet;

import data.CityData;
import data.GameColor;
import game.city.City;

public class TestCityFactory {
	public City makeFakeCity() {
		return makeFakeCity("Some Fake City");
	}

	public City makeFakeCity(String name) {
		return makeFakeCity(name, GameColor.BLACK);
	}

	public City makeFakeCity(String name, int population) {
		return makeFakeCity(name, GameColor.BLACK, population);
	}

	public City makeFakeCity(String name, GameColor color) {
		return makeFakeCity(name, color, 10);
	}

	public City makeFakeCity(String name, GameColor color, int population) {
		return new City(new CityData(name, color, population, false), new HashSet<>());
	}
}
