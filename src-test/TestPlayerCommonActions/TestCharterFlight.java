package TestPlayerCommonActions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.Board;
import Initialize.City;
import Player.Medic;
import Player.Player;

public class TestCharterFlight {
	Board board;
	Player player;
	PlayerCard locationCityCard, notLocationCityCard, eventCard;
	City chicagoCity, newyorkCity, seattleCity, miamiCity;

	@Before
	public void setup() {
		board = new Board();
		player = new Medic(board);
		String chicago = "Chicago";
		chicagoCity = new City(chicago);
		String newyork = "NewYork";
		newyorkCity = new City(newyork);
		String seattle = "Seattle";
		seattleCity = new City(seattle);
		String miami = "Miami";
		miamiCity = new City(miami);

		board.cities.put(chicago, chicagoCity);
		board.cities.put(newyork, newyorkCity);
		board.cities.put(seattle, seattleCity);
		board.cities.put(miami, miamiCity);

		player.location = chicagoCity;
		
		locationCityCard = new PlayerCard(Board.CardType.CITYCARD, chicago);
		notLocationCityCard = new PlayerCard(Board.CardType.CITYCARD, newyork);
		
		eventCard = new PlayerCard(Board.CardType.EVENTCARD, "");
		
		player.hand.put(locationCityCard.cardName, locationCityCard);
		player.hand.put(notLocationCityCard.cardName, notLocationCityCard);
		player.hand.put(eventCard.cardName, eventCard);
		
	}

	@Test
	public void testValidConsumeAction1() {
		player.consumeAction();
		assertEquals(3, player.action);
	}

	@Test
	public void testConsumeAction() {
		player.consumeAction();
		player.consumeAction();
		player.consumeAction();
		player.consumeAction();
		assertEquals(0, player.action);
	}

	@Test(expected = RuntimeException.class)
	public void testInvalidConsumeAction() {
		player.consumeAction();
		player.consumeAction();
		player.consumeAction();
		player.consumeAction();
		player.consumeAction();
	}

	@Test
	public void testSuccessCharterFlight() {
		player.location = chicagoCity;
		player.action = 4;
		player.hand.put(locationCityCard.cardName, locationCityCard);
		player.hand.put(notLocationCityCard.cardName, notLocationCityCard);
		player.hand.put(eventCard.cardName, eventCard);
		
		String location = player.location.cityName;
		
		board.cityCardNameCharter = "NewYork";
		
		assertTrue(player.hand.containsKey(location));
		player.charterFlight();	
		assertFalse(player.hand.containsKey(location));
		assertEquals("NewYork", player.location.cityName);
		assertEquals(2, player.hand.size());
		assertEquals(3, player.action);
	}

}
