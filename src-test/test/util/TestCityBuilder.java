package test.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import data.CityData;
import data.GameColor;
import game.city.City;
import game.disease.CubeData;
import game.disease.CubeDataImpl;

public class TestCityBuilder {
	private String name;
	private GameColor color;
	private int population;
	private CubeData disease;
	private Set<City> neighbors;
	private boolean start;

	public TestCityBuilder() {
		name = "Test City";
		color = GameColor.BLACK;
		population = 12345;
		disease = new CubeDataImpl();
		neighbors = new HashSet<>();
		start = false;
	}

	public TestCityBuilder name(String name) {
		this.name = name;
		return this;
	}

	public TestCityBuilder color(GameColor color) {
		this.color = color;
		return this;
	}

	public TestCityBuilder population(int population) {
		this.population = population;
		return this;
	}

	public TestCityBuilder cubeData(CubeData disease) {
		this.disease = disease;
		return this;
	}

	public Set<City> neighborSet() {
		return neighbors;
	}

	public TestCityBuilder setStart() {
		start = true;
		return this;
	}

	public City build() {
		return new City(new CityData(name, color, population, start), disease, neighbors);
	}
}
