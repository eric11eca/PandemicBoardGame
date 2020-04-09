package test;

import java.util.HashSet;
import java.util.Set;

import data.CityData;
import game.GameColor;
import game.city.City;
import game.disease.CubeData;
import game.disease.CubeDataImpl;

public class MockCityBuilder {
	private String name;
	private GameColor color;
	private int population;
	private CubeData disease;
	private Set<City> neighbors;
	private boolean start;

	public MockCityBuilder() {
		name = "Test City";
		color = GameColor.BLACK;
		population = 12345;
		disease = new CubeDataImpl();
		neighbors = new HashSet<>();
		start = false;
	}

	public MockCityBuilder name(String name) {
		this.name = name;
		return this;
	}

	public MockCityBuilder color(GameColor color) {
		this.color = color;
		return this;
	}

	public MockCityBuilder population(int population) {
		this.population = population;
		return this;
	}

	public MockCityBuilder cubeData(CubeData disease) {
		this.disease = disease;
		return this;
	}

	public Set<City> neighborSet() {
		return neighbors;
	}

	public MockCityBuilder setStart() {
		start = true;
		return this;
	}

	public City build() {
		return new City(new CityData(name, color, population, start), disease, neighbors);
	}
}
