package TestPlayerCommonActions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.Board;
import Player.Medic;
import Player.Player;

public class TestDiscardCard {
	Board board;
	Player player;

	
	@Before
	public void setup() {
		board = new Board();
		player = new Medic(board);
		String[] cities = { "A", "B"};
		for (String city : cities) {
			player.hand.put(city, new PlayerCard(Board.CardType.CITYCARD, city));
		}
	}

	@Test
	public void testDiscardCard() {
		String cardName = "B";
		player.discardCard(cardName);
		assertEquals(1, player.hand.size());
		assertEquals(1, board.discardPlayerCard.size());
	}
	
	@Test (expected = RuntimeException.class)
	public void testDiscardCardWithInvalidCardName() {
		String cardName = "C";
		player.discardCard(cardName);
	}

}
