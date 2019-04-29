package TestPlayerCardAction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Card.OneQueitNightEvent;
import Initialize.Board;

public class TestOneQueitNight {
	Board board;
	OneQueitNightEvent night;
	
	@Before
	public void setup() {
		board = new Board();
		night = new OneQueitNightEvent(board);
	}

	@Test
	public void testSkipNextInfection() {
		assertFalse(board.inQueitNight);
		night.skipNextInfection();
		assertTrue(board.inQueitNight);
	}

}
