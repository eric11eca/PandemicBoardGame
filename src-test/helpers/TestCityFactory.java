package helpers;

import data.CityData;
import data.GameColor;
import game.City;

public class TestCityFactory {
	public City makeFakeCity() {
		return makeFakeCity("Some Fake City");
	}

	public City makeFakeCity(String name) {
		return makeFakeCity(name, GameColor.BLACK);
	}

	public City makeFakeCity(String name, int population) {
		return makeFakeCity(name, GameColor.BLACK, population, 0, 0);
	}

	public City makeFakeCity(String name, GameColor color) {
		return makeFakeCity(name, color, 10, 0, 0);
	}

	public City makeFakeCity(String name, GameColor color, int population) {
		return makeFakeCity(name, color, population, 0, 0);
	}

	public City makeFakeCity(String name, GameColor color, int population, int x, int y) {
		return new City(new CityData(name, color, population), x, y);
	}
}
