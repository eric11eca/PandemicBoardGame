package TestPlayerCommonActions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import CardActions.EventCardAction;
import Initialize.Board;
import Initialize.City;
import Player.Player;
import Player.PlayerData;
import PlayerAction.MedicAction;
import cards.PlayerCard;

public class TestCharterFlight {
	Board board;
	Player medic;
	PlayerData playerData;
	MedicAction medicAction;
	PlayerCard locationCityCard, notLocationCityCard, eventCard;
	EventCardAction eventCardAction;
	City chicagoCity, newyorkCity, seattleCity, miamiCity;

	@Before
	public void setup() {
		board = new Board();
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
		
		playerData = new PlayerData();
		
		playerData.role = Board.Roles.MEDIC;
		playerData.location = chicagoCity;
		
		locationCityCard = new PlayerCard(Board.CardType.CITYCARD, chicago);
		notLocationCityCard = new PlayerCard(Board.CardType.CITYCARD, newyork);
		
		eventCard = new PlayerCard(Board.CardType.EVENTCARD, "");
		
		playerData.hand.put(locationCityCard.cardName, locationCityCard);
		playerData.hand.put(notLocationCityCard.cardName, notLocationCityCard);
		playerData.hand.put(eventCard.cardName, eventCard);
		
		eventCardAction = new EventCardAction(board);
		medic = new Player(board, playerData);
	}

	@Test
	public void testValidConsumeAction1() {
		medic.consumeAction();
		assertEquals(3, playerData.action);
	}

	@Test
	public void testConsumeAction() {
		medic.consumeAction();
		medic.consumeAction();
		medic.consumeAction();
		medic.consumeAction();
		assertEquals(0, playerData.action);
	}

	@Test(expected = RuntimeException.class)
	public void testInvalidConsumeAction() {
		medic.consumeAction();
		medic.consumeAction();
		medic.consumeAction();
		medic.consumeAction();
		medic.consumeAction();
	}

	@Test
	public void testSuccessCharterFlight() {
		playerData.location = chicagoCity;
		playerData.action = 4;
		playerData.hand.put(locationCityCard.cardName, locationCityCard);
		playerData.hand.put(notLocationCityCard.cardName, notLocationCityCard);
		playerData.hand.put(eventCard.cardName, eventCard);
		
		String location = playerData.location.cityName;
		
		board.cityCardNameCharter = "NewYork";
		
		assertTrue(playerData.hand.containsKey(location));
		medic.charterFlight();	
		assertFalse(playerData.hand.containsKey(location));
		assertEquals("NewYork", playerData.location.cityName);
		assertEquals(2, playerData.hand.size());
		assertEquals(3, playerData.action);
	}

}
