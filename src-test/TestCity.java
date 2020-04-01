import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import data.GameColor;
import game.city.City;
import game.city.CubeData;
import helpers.TestAccess;
import helpers.TestCityFactory;

public class TestCity {
	TestCityFactory cityFactory = new TestCityFactory();
	TestAccess access = new TestAccess();

	@Test
	public void testNeighbors() {
		City city = cityFactory.makeFakeCity();

		City c1 = cityFactory.makeFakeCity("Atalanta");
		City c2 = cityFactory.makeFakeCity("NewYork");
		City c3 = cityFactory.makeFakeCity("Boston");

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
		City city = cityFactory.makeFakeCity();
		city.buildResearchStation();
		assertTrue(city.hasResearchStation());
		city.removeResearchStation();
		assertFalse(city.hasResearchStation());
	}

	@Test
	public void testDiseaseCubes() {
		CubeData cityDisease = access.getCityDisease(cityFactory.makeFakeCity());
		cityDisease.addDiseaseCube(GameColor.YELLOW);
		cityDisease.addDiseaseCube(GameColor.YELLOW);
		cityDisease.addDiseaseCube(GameColor.RED);
		Set<GameColor> diseasesColors = cityDisease.getExistingDiseases();
		assertTrue(diseasesColors.contains(GameColor.YELLOW));
		assertEquals(1, cityDisease.getDiseaseCubeCount(GameColor.RED));
		assertTrue(diseasesColors.contains(GameColor.RED));
		assertEquals(2, cityDisease.getDiseaseCubeCount(GameColor.YELLOW));
	}

	@Test
	public void testColor() {
		City city = cityFactory.makeFakeCity("City", GameColor.RED);
		assertEquals(GameColor.RED, city.getColor());
	}
}
