package TestPlayerCommonActions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.Board;
import Initialize.City;
import Player.Dispatcher;
import Player.Player;

public class TestReceiveDiscardCityCard {
	private Board board;
	private City city;
	private ArrayList<PlayerCard> citycards;

	@Before
	public void setup() {
		city = new City();
		board = new Board();
		city.cityName = "city";
		String[] cities = { "A", "B", "C", "D", "E", "F", "G", "H" };
		citycards = new ArrayList<>();
		for (String city : cities) {
			citycards.add(new PlayerCard(Board.CardType.CITYCARD, city));
		}
	}

	@Test
	public void testNormalReceiveAndDiscardCityCard() {
		Player player = new Dispatcher(board);
		player.receiveCard(citycards.get(0));
		assertTrue(player.hand.containsKey(citycards.get(0).cardName));
		player.discardCard(citycards.get(0).cardName);
		assertFalse(player.hand.containsKey(citycards.get(0).cardName));
	}

}
