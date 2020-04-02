package data;

import java.util.HashSet;
import java.util.Set;

import game.city.City;

public class CityBuilder {
	private City city;
	private Set<City> neighbors;

	public CityBuilder() {
		neighbors = new HashSet<>();
	}

	public void buildFromCityData(CityData d) {
		city = new City(d, neighbors);
	}

	public City getCity() {
		return city;
	}

	public void addNeighbor(City city) {
		neighbors.add(city);
	}
}
