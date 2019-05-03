package TestPlayerCommonActions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Card.EventCardAction;
import Card.PlayerCard;
import Initialize.Board;
import Initialize.City;
import Player.Player;
import Player.PlayerData;

public class TestDirectFlight {
	Board board;
	Player player;
	PlayerData playerData;
	PlayerCard eventCard;
	EventCardAction eventCardAction;
	PlayerCard newyorkCityCard, chicagoCityCard;
	

	@Before
	public void setup() {
		board = new Board();
		playerData = new PlayerData();
		String newyork = "NewYork";
		City newyorkCity = new City(newyork);
		String chicago = "Chicago";
		City chicagoCity = new City(chicago);
		playerData.location = chicagoCity;
		newyorkCityCard = new PlayerCard(Board.CardType.CITYCARD, newyork);
		chicagoCityCard = new PlayerCard(Board.CardType.CITYCARD, chicago);
		eventCard = new PlayerCard(Board.CardType.EVENTCARD, "");
		board.cities.put(newyork, newyorkCity);
		board.cities.put(chicago, chicagoCity);
		playerData.hand.put(newyorkCityCard.cardName, newyorkCityCard);
		playerData.hand.put(chicagoCityCard.cardName, chicagoCityCard);
		playerData.hand.put(eventCard.cardName, eventCard);
		eventCardAction = new EventCardAction(board);
		player = new Player(board, playerData, eventCardAction);
	}

	@Test
	public void testSuccessDirectFlight() {
		player.directFlight(newyorkCityCard);
		assertEquals(playerData.hand.size(), 2);
		assertEquals(playerData.action, 3);
		assertEquals("NewYork", playerData.location.cityName);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFailDirectFlight() {
		player.directFlight(eventCard);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFlyToCurrentCity() {
		player.directFlight(chicagoCityCard);
	}

}
