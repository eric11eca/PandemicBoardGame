
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import game.GameColor;
import game.city.City;
import test.MockCityBuilder;

public class TestCity {

	@Test
	public void testNeighbors() {
		MockCityBuilder centerCityBuilder = new MockCityBuilder();
		Set<City> centerNeighbor = centerCityBuilder.neighborSet();
		City centerCity = centerCityBuilder.build();

		MockCityBuilder city1Builder = new MockCityBuilder();
		Set<City> city1Neighbor = city1Builder.neighborSet();
		City city1 = city1Builder.build();

		MockCityBuilder city2Builder = new MockCityBuilder();
		Set<City> city2Neighbor = city2Builder.neighborSet();
		City city2 = city2Builder.build();

		MockCityBuilder city3Builder = new MockCityBuilder();
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
		City city = new MockCityBuilder().build();
		city.buildResearchStation();
		assertTrue(city.hasResearchStation());
		city.removeResearchStation();
		assertFalse(city.hasResearchStation());
	}

	@Test
	public void testColor() {
		City city = new MockCityBuilder().color(GameColor.RED).build();
		assertEquals(GameColor.RED, city.getColor());
	}
}
