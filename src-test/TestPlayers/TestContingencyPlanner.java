package TestPlayers;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.Board;
import Player.ContingencyPlanner;
import Player.Player;

public class TestContingencyPlanner {
	Board board;
	Player contingencyPlanner; 
	
	@Before 
	public void setup() {
		board = new Board();
		contingencyPlanner = new ContingencyPlanner(board);
	}
	

	@Test
	public void testPickCardFromDispach() {
		String cardName = "Peaceful Night";
		contingencyPlanner.hand.add(new PlayerCard(Board.CardType.CITYCARD, "Chicago"));
		PlayerCard playerCard = new PlayerCard(Board.CardType.EVENTCARD, cardName);
		board.discardPlayerCard.add(playerCard);
		contingencyPlanner.pickFromDiscardPlayerCard(cardName);
		assertEquals(contingencyPlanner.specialEventCard.cardName, cardName);
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
		board.discardPlayerCard.add(playerCard1);
		board.discardPlayerCard.add(playerCard2);
		int old_size = board.discardPlayerCard.size();
		
		boolean cardUsed = contingencyPlanner.useCard(cardName);
		assertTrue(cardUsed);
		assertTrue(contingencyPlanner.specialEventCard == null);
		int new_size = board.discardPlayerCard.size();
		assertEquals(old_size, new_size);
	}

}
