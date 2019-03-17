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
		
		assertEquals("Atalanta", city.connections.get(0).getName());
		assertEquals("New York", city.connections.get(1).getName());
		assertEquals("Boston", city.connections.get(2).getName());
	}

}
