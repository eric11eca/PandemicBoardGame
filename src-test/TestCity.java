import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;

import org.junit.Test;

import Initialize.City;

public class TestCity {
	@Test
	public void testCoordinates() {
		City city = new City();
		city.X = 12;
		city.Y = 50;
		assertEquals(city.X, 12);
		assertEquals(city.Y, 50);
	}
	
	@Test
	public void testNeighbors () {
		City city = new City();
	
		City c1 = new City();
		City c2 = new City();
		City c3 = new City();
		
		c1.cityName = "Atalanta";
		c2.cityName = "New York";
		c3.cityName = "Boston";
		
		city.neighbors.add(c1); 
		city.neighbors.add(c2); 
		city.neighbors.add(c3);
		
		HashSet<String> cityNames = new HashSet<String>();
		
		cityNames.add("Atalanta");
		cityNames.add("New York");
		cityNames.add("Boston");
		
		Iterator<City> it = city.neighbors.iterator();
	    while(it.hasNext()){
	        assertTrue(cityNames.contains(it.next().cityName));
	    } 
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
