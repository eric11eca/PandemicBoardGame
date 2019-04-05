package TestPlayers;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.Board;
import Player.DiscoverCureScientist;

public class TestDiscoverCureScientist {
	DiscoverCureScientist scienceCure;
	Map<String, PlayerCard> hand;
	Board board;

	@Before
	public void setup() {
		hand = new HashMap<>();
		board = new Board();
		scienceCure = new DiscoverCureScientist(hand, board.curedDiseases);
	}

	@Test
	public void testDiscoverCureScientist() {
		String cardName1 = "a";
		String cardName2 = "b";
		String cardName3 = "c";
		String cardName4 = "d";
		String cardName5 = "e";
		String cardName6 = "f";
		String cardName7 = "g";

		PlayerCard card1 = new PlayerCard(Board.CardType.CITYCARD, cardName1);
		PlayerCard card2 = new PlayerCard(Board.CardType.CITYCARD, cardName2);
		PlayerCard card3 = new PlayerCard(Board.CardType.CITYCARD, cardName3);
		PlayerCard card4 = new PlayerCard(Board.CardType.CITYCARD, cardName4);
		PlayerCard card5 = new PlayerCard(Board.CardType.CITYCARD, cardName5);
		PlayerCard card6 = new PlayerCard(Board.CardType.EVENTCARD, cardName6);
		PlayerCard card7 = new PlayerCard(Board.CardType.CITYCARD, cardName7);

		card1.color = "YELLOW";
		card2.color = "RED";
		card3.color = "RED";
		card4.color = "RED";
		card5.color = "RED";
		card7.color = "BLUE";

		hand.put(cardName1, card1);
		hand.put(cardName2, card2);
		hand.put(cardName3, card3);
		hand.put(cardName4, card4);
		hand.put(cardName5, card5);
		hand.put(cardName6, card6);
		hand.put(cardName7, card7);

		assertTrue(board.curedDiseases.isEmpty());
		scienceCure.discoverCure();

		assertTrue(board.curedDiseases.contains("RED"));
	}
}
