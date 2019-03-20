import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
	private City city;
	private City unconnected_city;
	private City connected_city;
	@Before
	public void setup(){
		city = new City();
		unconnected_city = new City();
		connected_city = new City();
		city.addConnections(connected_city);
		connected_city.addConnections(city);
	}
	
	@Test 
	public void testNormalReceiveAndDiscardCityCard(){
		Player player = new Dispatcher();
		player.receiveCard(new CityCard("A"));
		assertTrue(player.handContains("A"));
		player.discardCard("A");
		assertFalse(player.handContains("A"));
	}
	
	@Test 
	public void testReceiveCardWithExceedNumberOfHandCard(){
		Player player = new Dispatcher();
		String[] cities = {"A","B","C","D","E","F","G"};
		for(String city: cities){
			player.receiveCard(new CityCard(city));
		}
		player.receiveCard(new CityCard("Q"));
		assertEquals(7,player.hand.size());
	}
	@Test 
	public void testMove(){
		Player player = new Dispatcher();
		player.location = city;
		player.move(connected_city);
		assertEquals(player.location, connected_city);
	}
	
	@Test 
	public void testInvalidMove(){
		Player player = new Dispatcher();	
		player.location = city;
		player.move(unconnected_city);
		assertNotEquals(player.location,unconnected_city);
	}
}

