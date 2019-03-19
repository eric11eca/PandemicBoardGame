import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {
	
	
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
		City city = new City();
		player.move(city);
		assertEquals(player.location, city);
	}
}
