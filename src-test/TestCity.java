import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;

import org.junit.Test;

public class TestCity {
	@Test
	public void testCoordinates() {
		City city = new City();
		city.setX(12);
		city.setY(50);
		assertEquals(city.getX(), 12);
		assertEquals(city.getY(), 50);
	}
	
	@Test
	public void testNeighbors () {
		City city = new City();
	
		City c1 = new City();
		City c2 = new City();
		City c3 = new City();
		
		c1.setName("Atalanta");
		c2.setName("New York");
		c3.setName("Boston");
		
		HashSet<City> n = new HashSet<City>();
		n.add(c1); 
		n.add(c2); 
		n.add(c3);
		city.setNeighbors(n);
		
		HashSet<City> neighbors = city.getNeighbors();
		HashSet<String> cityNames = new HashSet<String>();
		
		cityNames.add("Atalanta");
		cityNames.add("New York");
		cityNames.add("Boston");
		
		Iterator<City> it = neighbors.iterator();
	    while(it.hasNext()){
	        assertTrue(cityNames.contains(it.next().getName()));
	    } 
	}
	
	@Test
	public void testResearchStations(){
		City city = new City();
		city.placeResearchStation();
		Boolean researchStation = city.getResearchStation();
		assertTrue(researchStation);
	}
	
	@Test
	public void testDiseaseCube() {
		City city = new City();
		city.placeDiseaseCube(5);
		assertEquals(5, city.getDiseaseCube());
	}
	
	@Test 
	public void testColor() {
		City city = new City();
		city.setColor("RED");
		assertEquals("RED", city.getColor());
	}
}
