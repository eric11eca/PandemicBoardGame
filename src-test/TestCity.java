import static org.junit.jupiter.api.Assertions.*;

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
		
	}

}
