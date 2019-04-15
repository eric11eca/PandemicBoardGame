import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import Initialize.City;

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
	}
	
	@Test
	public void testResearchStations(){
		City city = new City();
		city.researchStation = true;
		assertTrue(city.researchStation);
	}
	
	@Test
	public void testDiseaseCubes() {
		City city = new City();
		city.diseaseCubes.put("Yellow", 1);
		city.diseaseCubes.put("Red", 2);
		assertTrue(city.diseaseCubes.containsKey("Yellow"));
		assertTrue(1 == city.diseaseCubes.get("Yellow"));
		assertTrue(city.diseaseCubes.containsKey("Red"));
		assertTrue(2 == city.diseaseCubes.get("Red"));
	}
	
	@Test 
	public void testColor() {
		City city = new City();
		city.color = "RED";
		assertEquals("RED", city.color);
	}
}
