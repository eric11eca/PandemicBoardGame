package TestPlayerCardAction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Card.OneQuietNightEvent;
import Initialize.Board;

public class TestOneQueitNight {
	Board board;
	OneQuietNightEvent night;
	
	@Before
	public void setup() {
		board = new Board();
		night = new OneQuietNightEvent(board);
	}

	@Test
	public void testSkipNextInfection() {
		assertFalse(board.inQueitNight);
		night.executeEvent();
		assertTrue(board.inQueitNight);
	}

}
