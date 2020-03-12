package testPlayerCardAction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cards.OneQuietNight;
import data.Board;

public class TestOneQueitNight {
	Board board;
	OneQuietNight night;
	
	@Before
	public void setup() {
		board = Board.getInstance();
		night = new OneQuietNight(board);
	}

	@Test
	public void testSkipNextInfection() {
		assertFalse(board.inQueitNight);
		night.executeEvent();
		assertTrue(board.inQueitNight);
	}

}
