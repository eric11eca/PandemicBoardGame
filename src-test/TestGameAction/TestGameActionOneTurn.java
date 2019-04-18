package TestGameAction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Action.GameAction;
import Card.PlayerCard;
import Initialize.Board;
import Player.Medic;
import Player.Player;
import Player.Scientist;

public class TestGameActionOneTurn {
	Board board;
	GameAction action;
	Player medic;
	Player scientist;
	
	@Before
	public void setup() {
		board  = new Board();
		initialLizeBoard();
		action = new GameAction(board);
	}
	
	public void initialLizeBoard() {
		PlayerCard playerCard1 = new PlayerCard(Board.CardType.CITYCARD, "Chicago");
		PlayerCard playerCard2 = new PlayerCard(Board.CardType.CITYCARD, "NewYork");
		PlayerCard playerCard3 = new PlayerCard(Board.CardType.CITYCARD, "London");
		PlayerCard playerCard4 = new PlayerCard(Board.CardType.EVENTCARD, "NewYork");
		board.validPlayerCard.add(playerCard1);
		board.validPlayerCard.add(playerCard2);
		board.validPlayerCard.add(playerCard3);
		board.validPlayerCard.add(playerCard4);
		
		medic = new Medic(board);
		scientist = new Scientist(board);
		board.currentPlayers.add(medic);
		board.currentPlayers.add(scientist);
	}

	@Test
	public void testDrawTwoPlayerCards() {
		action.drawTwoPlayerCards();
		assertEquals(2, medic.hand.size());
		assertTrue(medic.hand.containsKey("Chicago"));
		assertEquals(2, board.validPlayerCard.size());
		assertEquals(2, board.discardPlayerCard.size());
		assertTrue(board.discardPlayerCard.containsKey("Chicago"));
	}
}
