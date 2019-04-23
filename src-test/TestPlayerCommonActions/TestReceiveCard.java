package TestPlayerCommonActions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.Board;
import Player.Dispatcher;
import Player.Player;

public class TestReceiveCard {
	private Board board;
	private ArrayList<PlayerCard> citycards;
	Player player;

	@Before
	public void setup() {
		board = new Board();
		player = new Dispatcher(board);
		
		String[] cities = { "A", "B", "C", "D", "E", "F", "G", "H" };
		citycards = new ArrayList<>();
		for (String city : cities) {
			citycards.add(new PlayerCard(Board.CardType.CITYCARD, city));
		}
		
		player.hand.put("C", citycards.get(2));
	}


	@Test
	public void testNormalReceiveAndDiscardCityCard() {
		player.receiveCard(citycards.get(0));
		assertTrue(player.hand.containsKey(citycards.get(0).cardName));
		player.discardCard(citycards.get(0).cardName);
		assertFalse(player.hand.containsKey(citycards.get(0).cardName));
	}

}
