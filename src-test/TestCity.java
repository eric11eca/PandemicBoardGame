import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;

import org.junit.Test;

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
		
		c1.name = "Atalanta";
		c2.name = "New York";
		c3.name = "Boston";
		
		HashSet<City> n = new HashSet<City>();
		n.add(c1); 
		n.add(c2); 
		n.add(c3);
		city.neighbors = n;
		
		HashSet<String> cityNames = new HashSet<String>();
		
		cityNames.add("Atalanta");
		cityNames.add("New York");
		cityNames.add("Boston");
		
		Iterator<City> it = city.neighbors.iterator();
	    while(it.hasNext()){
	        assertTrue(cityNames.contains(it.next().name));
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
		//city.diseaseCubes = 5;
		//assertEquals(5, city.diseaseCubes);
	}
	
	@Test 
	public void testColor() {
		City city = new City();
		city.color = "RED";
		assertEquals("RED", city.color);
	}
}
