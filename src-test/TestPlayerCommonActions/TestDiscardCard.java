package TestPlayerCommonActions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Card.EventCardAction;
import Card.PlayerCard;
import Initialize.Board;
import Player.Player;
import Player.PlayerData;

public class TestDiscardCard {
	Board board;
	Player player;
	EventCardAction eventCardAction;
	PlayerData playerData;
	
	@Before
	public void setup() {
		board = new Board();
		playerData = new PlayerData();
		String[] cities = { "A", "B"};
		for (String city : cities) {
			playerData.hand.put(city, new PlayerCard(Board.CardType.CITYCARD, city));
		}
		eventCardAction = new EventCardAction(board);
		player = new Player(board, playerData, eventCardAction);
	}

	@Test
	public void testDiscardCard() {
		String cardName = "B";
		board.cardToBeDiscard.add(cardName);
		player.discardCard();
		assertEquals(1, playerData.hand.size());
		assertEquals(1, board.discardPlayerCard.size());
		assertEquals(0, board.cardToBeDiscard.size());
	}
	
	@Test (expected = RuntimeException.class)
	public void testDiscardCardWithInvalidCardName() {
		String cardName = "C";
		board.cardToBeDiscard.add(cardName);
		player.discardCard();
	}

}
