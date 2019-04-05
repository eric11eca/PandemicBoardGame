package TestPlayerCommonActions;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
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
	
	@Test 
	public void testMove(){
		Player player = new Dispatcher(board);
		player.location = city;
		player.drive(connected_city);
		assertEquals(player.location, connected_city);
	}
	
	@Test 
	public void testInvalidMove() throws Exception{
		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("Invalid destination: Not a neighbour!!");
		Player player = new Dispatcher(board);	
		player.location = city;
		player.drive(unconnected_city);
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
