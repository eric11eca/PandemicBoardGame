package testPlayerCommonActions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cards.PlayerCard;
import data.Board;
import game.City;
import helpers.TestCityFactory;
import player.Player;
import player.PlayerData;

public class TestCharterFlight {
	TestCityFactory cityFactory = new TestCityFactory();

	@Test
	public void testCharterFlight() {
		Board board = new Board();
		String chicago = "Chicago";
		City chicagoCity = cityFactory.makeFakeCity(chicago);
		String newyork = "NewYork";
		City newyorkCity = cityFactory.makeFakeCity(newyork);
		board.cities.put(newyork, newyorkCity);
		
		PlayerData playerData = new PlayerData();		
		PlayerCard chicagoCityCard = new PlayerCard(Board.CardType.CITYCARD, chicago);
		playerData.location = chicagoCity;
		playerData.action = 4;
		playerData.hand.put(chicagoCityCard.cardName, chicagoCityCard);
		String location = playerData.location.getName();
		
		Player medic = new Player(board, playerData);
		assertTrue(playerData.hand.containsKey(location));
		medic.charterFlight(newyorkCity);	
		assertFalse(playerData.hand.containsKey(location));
	
		assertEquals(newyork, playerData.location.getName());
		assertEquals(0, playerData.hand.size());
		assertEquals(3, playerData.action);
	}

}
