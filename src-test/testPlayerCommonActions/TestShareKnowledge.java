package testPlayerCommonActions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cardActions.EventCardAction;
import cards.PlayerCard;
import initialize.Board;
import player.Player;
import player.PlayerData;

public class TestShareKnowledge {
	Board board;
	Player player1;
	Player player2;
	PlayerData playerData1;
	PlayerData playerData2;
	String cityName;
	PlayerCard citycard;
	String eventName;
	PlayerCard eventcard;
	EventCardAction eventCardAction;

	@Before
	public void setup() {
		board = new Board();
		playerData1 = new PlayerData();
		playerData2 = new PlayerData();
		cityName = "NewYork";
		citycard = new PlayerCard(Board.CardType.CITYCARD, cityName);
		eventName = "Event";
		eventcard = new PlayerCard(Board.CardType.EVENTCARD, eventName);
		playerData1.action = 4;
		playerData2.action = 4;
		eventCardAction = new EventCardAction(board);
		player1 = new Player(board, playerData1);
		player2 = new Player(board, playerData2);
	}

	@Test
	public void testShareKnowledgePlayer1GiveSuccess() {
		player1.receiveCard(citycard);
		board.playerToShare = player2;
		board.cityToShare = citycard;
		board.isGiving = true;
		player1.shareKnowledge();
		assertEquals(playerData1.location, playerData2.location);
		assertFalse(playerData1.hand.containsKey(cityName));
		assertTrue(playerData2.hand.containsKey(cityName));
		assertEquals(3, playerData1.action);
		assertEquals(4, playerData2.action);
	}

	@Test
	public void testShareKnowledgePlayer1ReceiveSuccess() {
		player2.receiveCard(citycard);
		board.playerToShare = player2;
		board.cityToShare = citycard;
		board.isGiving = false;
		player1.shareKnowledge();
		assertEquals(playerData1.location, playerData2.location);
		assertFalse(playerData2.hand.containsKey(cityName));
		assertTrue(playerData1.hand.containsKey(cityName));
		assertEquals(3, playerData1.action);
		assertEquals(4, playerData2.action);
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
