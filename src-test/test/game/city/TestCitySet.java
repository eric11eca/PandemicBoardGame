package test.game.city;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import game.city.City;
import game.city.CitySet;
import mock.MockCityBuilder;

public class TestCitySet {

	@Test
	public void testSatisfy() {
		City city = new MockCityBuilder().build();
		Set<City> set = new HashSet<>();
		set.add(city);
		set.add(new MockCityBuilder().name("other").build());
		CitySet citySet = new CitySet(set);

		assertEquals(1, citySet.getCitiesSatisfying(c -> !c.equals(city)).size());
	}

	@Test
	public void testGetAll() {
		Set<City> set = new HashSet<>();
		set.add(new MockCityBuilder().build());
		set.add(new MockCityBuilder().build());
		set.add(new MockCityBuilder().build());
		CitySet citySet = new CitySet(set);
		assertEquals(set.size(), citySet.getAllCities().size());
	}

}
