package TestPlayers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.Board;
import Player.ContingencyPlanner;

public class TestContingencyPlanner {
	Board board;
	ContingencyPlanner contingencyPlanner;

	@Before
	public void setup() {
		board = new Board();
		contingencyPlanner = new ContingencyPlanner(board);
	}

	@Test
	public void testPickCardFromDispach() {
		String cardName = "Airlift";
		String cardName1 = "Chicago";
		contingencyPlanner.hand.put(cardName1, new PlayerCard(Board.CardType.CITYCARD, cardName1));
		PlayerCard playerCard = new PlayerCard(Board.CardType.EVENTCARD, cardName);
		board.discardPlayerCard.put(cardName, playerCard);
		contingencyPlanner.pickFromDiscardPlayerCard(cardName);
		assertEquals(contingencyPlanner.specialEventCard.cardName, cardName);
		assertFalse(board.discardPlayerCard.containsKey(cardName));
	}

	@Test
	public void testRemoveSpecialEventCardCompletly() {
		String cardName = "Airlift";
		PlayerCard playerCard = new PlayerCard(Board.CardType.EVENTCARD, cardName);
		contingencyPlanner.specialEventCard = playerCard;

		String cardName1 = "Chicago";
		String cardName2 = "New York";
		PlayerCard playerCard1 = new PlayerCard(Board.CardType.CITYCARD, cardName1);
		PlayerCard playerCard2 = new PlayerCard(Board.CardType.CITYCARD, cardName2);
		board.discardPlayerCard.put(cardName1, playerCard1);
		board.discardPlayerCard.put(cardName2, playerCard2);
		int old_size = board.discardPlayerCard.size();

		boolean cardUsed = contingencyPlanner.useEventCard(cardName);
		assertTrue(cardUsed);
		assertTrue(contingencyPlanner.specialEventCard == null);
		int new_size = board.discardPlayerCard.size();
		assertEquals(old_size, new_size);
	}

}
