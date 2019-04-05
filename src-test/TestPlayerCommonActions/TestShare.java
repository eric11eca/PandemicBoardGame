package TestPlayerCommonActions;

import org.junit.Before;
import org.junit.Test;

import Initialize.Board;
import Player.Medic;
import Player.Player;
import Player.Researcher;

public class TestShare {
	Board board;
	Player medic, researcher;

	@Before
	public void setup() {
		board = new Board();
		medic = new Medic(board);
		researcher = new Researcher(board);
		board.currentPlayers.add(medic);
		board.currentPlayers.add(researcher);

	}

	@Test
	public void testRegularShareKnowledge() {
		// Check if chosen player is in the same city
		// Give or take a city card to/from selected player

		// The player who is given a card, the number of card in its hand
		// increase by one
		// The player who is taken a card, the number of card in its hand
		// decrease by one
		// Check hand limit for the player who takes a card
		// An action is consumed.

	}

	@Test
	public void test() {

	}
}
