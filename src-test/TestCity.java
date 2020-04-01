import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import data.CityData;
import data.GameColor;
import game.City;

public class TestCity {
	@Test
	public void testCoordinates() {
		City city = new City(new CityData("Some City", GameColor.RED, 10), 12, 50);
		assertEquals(city.x, 12);
		assertEquals(city.y, 50);
	}

	@Test
	public void testNeighbors() {
		City city = new City(new CityData("Some City", GameColor.RED, 10), 0, 0);

		City c1 = new City(new CityData("Atalanta", GameColor.RED, 10), 0, 0);
		City c2 = new City(new CityData("NewYork", GameColor.RED, 10), 0, 0);
		City c3 = new City(new CityData("Boston", GameColor.RED, 10), 0, 0);

		city.neighbors.add(c1);
		city.neighbors.add(c2);
		city.neighbors.add(c3);

		Set<String> neighborNames = new HashSet<>();
		city.neighbors.stream().map(c -> c.getName()).forEach(neighborNames::add);
		assertTrue(neighborNames.contains("Atalanta"));
		assertTrue(neighborNames.contains("NewYork"));
		assertTrue(neighborNames.contains("Boston"));
	}

	@Test
	public void testResearchStations() {
		City city = new City(new CityData("Atalanta", GameColor.RED, 10), 0, 0);
		city.researchStation = true;
		assertTrue(city.researchStation);
	}

	@Test
	public void testDiseaseCubes() {
		City city = new City(new CityData("Atalanta", GameColor.RED, 10), 0, 0);
		city.diseaseCubes.put("Yellow", 1);
		city.diseaseCubes.put("Red", 2);
		assertTrue(city.diseaseCubes.containsKey("Yellow"));
		assertTrue(1 == city.diseaseCubes.get("Yellow"));
		assertTrue(city.diseaseCubes.containsKey("Red"));
		assertTrue(2 == city.diseaseCubes.get("Red"));
	}

	@Test
	public void testColor() {
		City city = new City(new CityData("Atalanta", GameColor.RED, 10), 0, 0);
		assertEquals("RED", city.getColor().compatibility_ColorString);
	}
}
