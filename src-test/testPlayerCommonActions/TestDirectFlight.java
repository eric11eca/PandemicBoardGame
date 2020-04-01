package testPlayerCommonActions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cardActions.EventCardAction;
import cards.PlayerCard;
import data.Board;
import game.city.City;
import helpers.TestCityFactory;
import player.Player;
import player.PlayerData;

public class TestDirectFlight {
	Board board;
	Player player;
	PlayerData playerData;
	PlayerCard eventCard;
	EventCardAction eventCardAction;
	PlayerCard newyorkCityCard, chicagoCityCard;

	TestCityFactory cityFactory = new TestCityFactory();

	@Before
	public void setup() {
		board = new Board();
		playerData = new PlayerData();
		String chicago = "Chicago";
		City chicagoCity = cityFactory.makeFakeCity(chicago);
		String newyork = "NewYork";
		City newyorkCity = cityFactory.makeFakeCity(newyork);
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
		player = new Player(board, playerData);
	}

	@Test
	public void testSuccessDirectFlight() {
		player.cityCard = newyorkCityCard;
		player.getPlayerAction(Board.ActionName.DIRECTFLIGHT).executeAction();
		assertEquals(playerData.hand.size(), 2);
		assertEquals(playerData.action, 3);
		assertEquals("NewYork", playerData.location.getName());
	}

}
