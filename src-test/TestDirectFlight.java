import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.Board;
import Initialize.City;
import Player.Medic;
import Player.Player;

public class TestDirectFlight {
	Player player;
	PlayerCard newyorkCityCard, chicagoCityCard;
	PlayerCard eventCard;
	Board board;

	@Before
	public void setup() {
		player = new Medic();
		board = new Board();
		player.board = board;
		String newyork = "NewYork";
		City newyorkCity = new City(newyork);
		String chicago = "Chicago";
		City chicagoCity = new City(chicago);
		player.location = chicagoCity;
		newyorkCityCard = new PlayerCard(Board.CardType.CITYCARD, newyork);
		chicagoCityCard = new PlayerCard(Board.CardType.CITYCARD, chicago);
		eventCard = new PlayerCard(Board.CardType.EVENTCARD, "");
		board.cities.put(newyork, newyorkCity);
		board.cities.put(chicago, chicagoCity);
		player.hand.add(newyorkCityCard);
		player.hand.add(chicagoCityCard);
		player.hand.add(eventCard);
	}

	@Test
	public void testSuccessDirectFlight() {
		player.directFlight(newyorkCityCard);
		assertEquals(player.hand.size(), 2);
		assertEquals(player.action, 3);
		assertEquals("NewYork", player.location.cityName);
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
