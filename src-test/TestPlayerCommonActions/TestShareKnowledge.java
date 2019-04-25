package TestPlayerCommonActions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.Board;
import Player.Medic;
import Player.Player;
import Player.Scientist;

public class TestShareKnowledge {
	Player player1;
	Player player2;
	Board board;
	String cityName;
	PlayerCard citycard;
	String eventName;
	PlayerCard eventcard;

	@Before
	public void setup() {
		board = new Board();
		player1 = new Medic(board);
		player2 = new Scientist(board);
		cityName = "NewYork";
		citycard = new PlayerCard(Board.CardType.CITYCARD, cityName);
		eventName = "Event";
		eventcard = new PlayerCard(Board.CardType.EVENTCARD, eventName);
		player1.action = 4;
		player2.action = 4;
	}

	@Test
	public void testShareKnowledgePlayer1GiveSuccess() {
		player1.receiveCard(citycard);
		board.playerToShare = player2;
		board.cityToShare = citycard;
		board.isGiving = true;
		player1.shareKnowledge();
		assertEquals(player1.location, player2.location);
		assertFalse(player1.hand.containsKey(cityName));
		assertTrue(player2.hand.containsKey(cityName));
		assertEquals(3, player1.action);
		assertEquals(4, player2.action);
	}

	@Test
	public void testShareKnowledgePlayer1ReceiveSuccess() {
		player2.receiveCard(citycard);
		board.playerToShare = player2;
		board.cityToShare = citycard;
		board.isGiving = false;
		player1.shareKnowledge();
		assertEquals(player1.location, player2.location);
		assertFalse(player2.hand.containsKey(cityName));
		assertTrue(player1.hand.containsKey(cityName));
		assertEquals(3, player1.action);
		assertEquals(4, player2.action);
	}

	@Test(expected = RuntimeException.class)
	public void testShareKnowledegePlayer1GiveEventCard() {
		board.playerToShare = player2;
		board.cityToShare = citycard;
		board.isGiving = true;
		player1.shareKnowledge();
	}
	
	@Test(expected = RuntimeException.class)
	public void testPlayer1GiveAwayInvalidCitycard() {
		board.playerToShare = player2;
		board.cityToShare = citycard;
		board.isGiving = true;
		player1.shareKnowledge();
	}
	
	@Test(expected = RuntimeException.class)
	public void testPlayer1ReceiveInvalidCitycard() {
		board.playerToShare = player2;
		board.cityToShare = citycard;
		board.isGiving = false;
		player1.shareKnowledge();
	}

}
