package testPlayerCommonActions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cardActions.EventCardAction;
import cards.PlayerCard;
import data.Board;
import data.City;
import player.Player;
import player.PlayerData;

public class TestShareKnowledge {
	Board board;
	Player player1;
	Player player2;
	Player researcher;

	PlayerData playerData1;
	PlayerData playerData2;
	PlayerData researcherData;

	String newyork;

	PlayerCard newyorkCitycard;
	PlayerCard chicagoCitycard;

	String eventName;
	PlayerCard eventcard;

	EventCardAction eventCardAction;

	@Before
	public void setup() {
		board = new Board();
		playerData1 = new PlayerData();
		playerData2 = new PlayerData();
		researcherData = new PlayerData();

		researcherData.role = Board.Roles.RESEARCHER;

		newyork = "NewYork";
		newyorkCitycard = new PlayerCard(Board.CardType.CITYCARD, newyork);
		City newyorkCity = new City(newyork);
		playerData1.location = newyorkCity;
		playerData2.location = newyorkCity;
		researcherData.location = newyorkCity;
		eventName = "Event";
		eventcard = new PlayerCard(Board.CardType.EVENTCARD, eventName);

		playerData1.action = 4;
		playerData2.action = 4;
		researcherData.action = 4;

		eventCardAction = new EventCardAction(board);

		player1 = new Player(board, playerData1);
		player2 = new Player(board, playerData2);
		researcher = new Player(board, researcherData);
	}

	@Test
	public void testShareKnowledgePlayer1GiveSuccess() {
		player1.receivedPlayerCard = newyorkCitycard;
		player1.getPlayerAction(Board.ActionName.RECEIVECARD).executeAction();
		
		board.playerToShare = player2;
		board.cityToShare = newyorkCitycard;
		board.isGiving = true;
		
		player1.getPlayerAction(Board.ActionName.SHAREKNOWLEDGE).executeAction();
		assertEquals(playerData1.location, playerData2.location);
		assertFalse(playerData1.hand.containsKey(newyork));
		assertTrue(playerData2.hand.containsKey(newyork));
		assertEquals(3, playerData1.action);
		assertEquals(4, playerData2.action);
	}

	@Test
	public void testShareKnowledgePlayer1ReceiveSuccess() {
		player2.receivedPlayerCard = newyorkCitycard;
		player2.getPlayerAction(Board.ActionName.RECEIVECARD).executeAction();
		
		board.playerToShare = player2;
		board.cityToShare = newyorkCitycard;
		board.isGiving = false;
		
		player1.getPlayerAction(Board.ActionName.SHAREKNOWLEDGE).executeAction();
		assertEquals(playerData1.location, playerData2.location);
		assertFalse(playerData2.hand.containsKey(newyork));
		assertTrue(playerData1.hand.containsKey(newyork));
		assertEquals(3, playerData1.action);
		assertEquals(4, playerData2.action);
	}

	@Test(expected = RuntimeException.class)
	public void testShareKnowledgePlayer1GivePlayer2WrongCityCard() {
		board.playerToShare = player2;
		board.cityToShare = chicagoCitycard;
		board.isGiving = true;
		player1.getPlayerAction(Board.ActionName.SHAREKNOWLEDGE).executeAction();
	}

	@Test
	public void testShareKnowledgePlayer1ReceiveFromAResearcher() {
		City chicago = new City("Chicago");
		researcher.playerData.location = chicago;
		player1.playerData.location = chicago;
		
		researcher.receivedPlayerCard = newyorkCitycard;
		researcher.getPlayerAction(Board.ActionName.RECEIVECARD).executeAction();
		
		board.playerToShare = researcher;
		board.cityToShare = newyorkCitycard;
		board.isGiving = false;
		player1.getPlayerAction(Board.ActionName.SHAREKNOWLEDGE).executeAction();
		
		assertTrue(player1.playerData.hand.containsKey(newyork));
		assertFalse(researcher.playerData.hand.containsKey(newyork));
	}
}
