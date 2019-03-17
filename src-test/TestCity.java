import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class TestCity {

	@Test
	public void testCoordinates() {
		City city = new City();
		city.setX(12);
		city.setY(50);
		assertEquals(city.getX(), 12);
		assertEquals(city.getY(), 50);
	}
	
	@Test
	public void testConnections () {
		City city = new City();
	
		City c1 = new City();
		City c2 = new City();
		City c3 = new City();
		
		c1.setName("Atalanta");
		c2.setName("New York");
		c3.setName("Boston");

		city.addConnections(c1);
		city.addConnections(c2);
		city.addConnections(c3);
		
		ArrayList<City> connections = city.getConnections();
		
		assertEquals("Atalanta", connections.get(0).getName());
		assertEquals("New York", connections.get(1).getName());
		assertEquals("Boston", connections.get(2).getName());
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
	
}
