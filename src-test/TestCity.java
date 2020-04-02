<<<<<<< HEAD
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
=======
import static org.junit.Assert.*;
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4

import java.util.Set;

import org.junit.Test;

<<<<<<< HEAD
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
=======
import game.City;

public class TestCity {
	@Test
	public void testCoordinates() {
		City city = new City();
		city.x = 12;
		city.y = 50;
		assertEquals(city.x, 12);
		assertEquals(city.y, 50);
	}
	
	@Test
	public void testNeighbors () {
		City city = new City();
	
		City c1 = new City();
		City c2 = new City();
		City c3 = new City();
		
		c1.cityName = "Atalanta";
		c2.cityName = "NewYork";
		c3.cityName = "Boston";
		
		city.neighbors.put(c1.cityName, c1); 
		city.neighbors.put(c2.cityName, c2); 
		city.neighbors.put(c3.cityName, c3);
			
		Set<String> neighbors = city.neighbors.keySet();
		assertTrue(neighbors.contains("Atalanta"));
		assertTrue(neighbors.contains("NewYork"));
		assertTrue(neighbors.contains("Boston"));
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
	}
	
	@Test
<<<<<<< HEAD
	public void testResearchStations() {
		City city = cityFactory.makeFakeCity();
		city.buildResearchStation();
		assertTrue(city.hasResearchStation());
		city.removeResearchStation();
		assertFalse(city.hasResearchStation());
=======
	public void testResearchStations(){
		City city = new City();
		city.researchStation = true;
		assertTrue(city.researchStation);
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
	}
	
	@Test
	public void testDiseaseCubes() {
<<<<<<< HEAD
		CubeData cityDisease = access.getCityDisease(cityFactory.makeFakeCity());
		cityDisease.addDiseaseCube(GameColor.YELLOW);
		cityDisease.addDiseaseCube(GameColor.YELLOW);
		cityDisease.addDiseaseCube(GameColor.RED);
		Set<GameColor> diseasesColors = cityDisease.getExistingDiseases();
		assertTrue(diseasesColors.contains(GameColor.YELLOW));
		assertEquals(1, cityDisease.getDiseaseCubeCount(GameColor.RED));
		assertTrue(diseasesColors.contains(GameColor.RED));
		assertEquals(2, cityDisease.getDiseaseCubeCount(GameColor.YELLOW));
=======
		City city = new City();
		city.diseaseCubes.put("Yellow", 1);
		city.diseaseCubes.put("Red", 2);
		assertTrue(city.diseaseCubes.containsKey("Yellow"));
		assertTrue(1 == city.diseaseCubes.get("Yellow"));
		assertTrue(city.diseaseCubes.containsKey("Red"));
		assertTrue(2 == city.diseaseCubes.get("Red"));
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
	}
	
	@Test 
	public void testColor() {
<<<<<<< HEAD
		City city = cityFactory.makeFakeCity("City", GameColor.RED);
		assertEquals(GameColor.RED, city.getColor());
=======
		City city = new City();
		city.color = "RED";
		assertEquals("RED", city.color);
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
	}
}
