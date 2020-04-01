package helpers;

import data.CityData;
import data.CityOLD;
import data.GameColor;

public class TestCityFactory {
	public CityOLD makeFakeCity() {
		return makeFakeCity("Some Fake City");
	}

	public CityOLD makeFakeCity(String name) {
		return makeFakeCity(name, GameColor.BLACK);
	}

	public CityOLD makeFakeCity(String name, int population) {
		return makeFakeCity(name, GameColor.BLACK, population, 0, 0);
	}

	public CityOLD makeFakeCity(String name, GameColor color) {
		return makeFakeCity(name, color, 10, 0, 0);
	}

	public CityOLD makeFakeCity(String name, GameColor color, int population) {
		return makeFakeCity(name, color, population, 0, 0);
	}

	public CityOLD makeFakeCity(String name, GameColor color, int population, int x, int y) {
		return new CityOLD(new CityData(name, color, population), x, y);
	}
}
