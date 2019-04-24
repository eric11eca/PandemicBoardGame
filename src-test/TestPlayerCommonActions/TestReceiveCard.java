package TestPlayerCommonActions;

import static org.junit.Assert.assertEquals;

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

		String[] cities = { "city1", "city2", "city3", "city4", "city5", "city6", "city7", "city8" };
		citycards = new ArrayList<>();
		for (String city : cities) {
			citycards.add(new PlayerCard(Board.CardType.CITYCARD, city));
		}
	}

	@Test
	public void testNormalReceiveCard() {
		player.receiveCard(citycards.get(0));
		assertEquals(1, player.hand.size());
	}

	@Test(expected = RuntimeException.class)
	public void testHandOverflow() {
		for (int i = 0; i < 8; i++) {
			PlayerCard playercard = citycards.get(i);
			player.receiveCard(playercard);
		}
	}

}
