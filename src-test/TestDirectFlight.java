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
	PlayerCard cityCard;
	PlayerCard eventCard;
	Board board;

	@Before
	public void setup() {
		player = new Medic();
		board = new Board();
		player.board = board;
		String cityName = "NewYork";
		cityCard = new PlayerCard(Board.CardType.CITYCARD, cityName);
		eventCard = new PlayerCard(Board.CardType.EVENTCARD, "");
		board.cities.put(cityName, new City(cityName));
		player.hand.add(cityCard);
		player.hand.add(eventCard);
	}

	@Test
	public void testSuccessDirectFlight() {
		player.directFlight(cityCard);
		assertEquals(player.hand.size(), 1);
		assertEquals(player.action, 3);
		assertEquals("NewYork", player.location.cityName);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFailDirectFlight() {
		player.directFlight(eventCard);
	}

}
