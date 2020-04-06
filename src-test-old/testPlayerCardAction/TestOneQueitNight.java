//package testPlayerCardAction;
//
//import static org.junit.Assert.*;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import data.Board;
//import game.event.EventOneQuietNight;
//
//public class TestOneQueitNight {
//	Board board;
//	EventOneQuietNight night;
//	
//	@Before
//	public void setup() {
//		board = new Board();
//		night = new EventOneQuietNight(board);
//	}
//
//	@Test
//	public void testSkipNextInfection() {
//		assertFalse(board.inQueitNight);
//		night.executeEvent();
//		assertTrue(board.inQueitNight);
//	}
//
//}
