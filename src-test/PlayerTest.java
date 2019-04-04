import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.*;
import Player.Dispatcher;
import Player.Player;
import Player.Player.*;

public class PlayerTest {
	private Board board;
	private City city;
	private PlayerCard hand_city;
	private City unconnected_city;
	private City connected_city;
	private ArrayList<PlayerCard> citycards;
	
	@Before
	public void setup(){
		city = new City();
		board = new Board();
		city.cityName = "city";
		unconnected_city = new City();
		connected_city = new City();
		city.neighbors.add(connected_city);
		connected_city.neighbors.add(city);
		String[] cities = {"A","B","C","D","E","F","G","H"}; // 8 cities
		citycards = new ArrayList<>();
		for(String city: cities){
			citycards.add(new PlayerCard(Board.CardType.CITYCARD, city));
		}
	}
	
	@Test 
	public void testNormalReceiveAndDiscardCityCard(){
		Player player = new Dispatcher(board);
		player.receiveCard(citycards.get(0));
		assertTrue(player.hand.containsKey(citycards.get(0).cardName));
		player.discardCard(citycards.get(0).cardName);
		assertFalse(player.hand.containsKey(citycards.get(0).cardName));
	}
	
//	@Test
//	public void testHandCapacity() {
//		Player p1 = new Player(4);
//		assertEquals(2, p1.getHandCapacity());
//		
//		Player p2 = new Player(3);
//		assertEquals(3, p2.getHandCapacity());
//		
//		Player p3 = new Player(2);
//		assertEquals(4, p3.getHandCapacity());
//	}
	
//	@Test 
//	public void testReceiveCardWithExceedNumberOfHandCard(){
//		Player player = new Dispatcher();
//		for(int i = 0; i < 7; i++){
//			player.receiveCard(citycards.get(i));
//		}
//		player.receiveCard(citycards.get(7));
//		assertEquals(7,player.hand.size());
//	}
	
	@Test 
	public void testMove(){
		Player player = new Dispatcher(board);
		player.location = city;
		player.move(connected_city);
		assertEquals(player.location, connected_city);
	}
	
	@Test 
	public void testInvalidMove(){
		Player player = new Dispatcher(board);	
		player.location = city;
		player.move(unconnected_city);
		assertNotEquals(player.location,unconnected_city);
	}
	
//	@Test
//	public void testValidMoveWithCityCard(){
//		Player player = new Dispatcher();
//		player.location = unconnected_city;
////		player.receiveCard();
////		player.handContains(city.getName());
//		player.move(city);
//		assertEquals(player.location, city);
//	}

}

