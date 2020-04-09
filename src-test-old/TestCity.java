
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import game.GameColor;
import game.city.City;
import test.util.TestCityBuilder;

public class TestCity {

	@Test
	public void testNeighbors() {
		TestCityBuilder centerCityBuilder = new TestCityBuilder();
		Set<City> centerNeighbor = centerCityBuilder.neighborSet();
		City centerCity = centerCityBuilder.build();

		TestCityBuilder city1Builder = new TestCityBuilder();
		Set<City> city1Neighbor = city1Builder.neighborSet();
		City city1 = city1Builder.build();

		TestCityBuilder city2Builder = new TestCityBuilder();
		Set<City> city2Neighbor = city2Builder.neighborSet();
		City city2 = city2Builder.build();

		TestCityBuilder city3Builder = new TestCityBuilder();
		Set<City> city3Neighbor = city3Builder.neighborSet();
		City city3 = city3Builder.build();

		centerNeighbor.add(city1);
		centerNeighbor.add(city2);
		centerNeighbor.add(city3);
		city1Neighbor.add(centerCity);
		city2Neighbor.add(centerCity);
		city3Neighbor.add(centerCity);

		assertTrue(centerCity.isNeighboring(city1));
		assertTrue(centerCity.isNeighboring(city2));
		assertTrue(centerCity.isNeighboring(city3));
		assertTrue(city1.isNeighboring(centerCity));
		assertTrue(city2.isNeighboring(centerCity));
		assertTrue(city3.isNeighboring(centerCity));
	}

	@Test
	public void testResearchStations() {
		City city = new TestCityBuilder().build();
		city.buildResearchStation();
		assertTrue(city.hasResearchStation());
		city.removeResearchStation();
		assertFalse(city.hasResearchStation());
	}

	@Test
	public void testColor() {
		City city = new TestCityBuilder().color(GameColor.RED).build();
		assertEquals(GameColor.RED, city.getColor());
	}
}
