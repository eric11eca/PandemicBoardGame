package TestPlayers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.Board;
import Player.ContingencyPlanner;
import Player.Medic;
import Player.Researcher;

public class TestContingencyPlanner {
	Board board;
	ContingencyPlanner contingencyPlanner;
	String specialCardName = "Airlift";
	@Before
	public void setup() {
		board = new Board();
		contingencyPlanner = new ContingencyPlanner(board);
	}

	@Test
	public void testPickCardFromDispach() {
		String cardName1 = "Chicago";
		contingencyPlanner.hand.put(cardName1, new PlayerCard(Board.CardType.CITYCARD, cardName1));
		PlayerCard playerCard = new PlayerCard(Board.CardType.EVENTCARD, specialCardName);
		board.discardPlayerCard.put(specialCardName, playerCard);
		contingencyPlanner.pickFromDiscardPlayerCard(specialCardName);
		assertEquals(contingencyPlanner.specialEventCard.cardName, specialCardName);
		assertFalse(board.discardPlayerCard.containsKey(specialCardName));
	}

	@Test
	public void testRemoveSpecialEventCardCompletly() {
		PlayerCard playerCard = new PlayerCard(Board.CardType.EVENTCARD, specialCardName);
		contingencyPlanner.specialEventCard = playerCard;
		board.idxofPlayerAirlift = 0;
		
		Medic medic = new Medic(board);
		Researcher researcher = new Researcher(board);
		board.currentPlayers.add(0, medic);
		board.currentPlayers.add(1, researcher);
		board.currentPlayers.add(2, contingencyPlanner);
		

		String cardName1 = "Chicago";
		String cardName2 = "New York";
		PlayerCard playerCard1 = new PlayerCard(Board.CardType.CITYCARD, cardName1);
		PlayerCard playerCard2 = new PlayerCard(Board.CardType.CITYCARD, cardName2);
		board.discardPlayerCard.put(cardName1, playerCard1);
		board.discardPlayerCard.put(cardName2, playerCard2);
		int old_size = board.discardPlayerCard.size();

		boolean cardUsed = contingencyPlanner.useEventCard(specialCardName);
		assertTrue(cardUsed);
		assertTrue(contingencyPlanner.specialEventCard == null);
		int new_size = board.discardPlayerCard.size();
		assertEquals(old_size, new_size);
	}

}
