package testPlayerCardAction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.Board;
import game.cards.event.OneQuietNight;

public class TestOneQueitNight {
	Board board;
	OneQuietNight night;
	
	@Before
	public void setup() {
		board = new Board();
		night = new OneQuietNight(board);
	}

	@Test
	public void testSkipNextInfection() {
		assertFalse(board.inQueitNight);
		night.executeEvent();
		assertTrue(board.inQueitNight);
	}

}
