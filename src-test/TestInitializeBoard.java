import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class TestInitializeBoard {
	InitializeBoard initializeBoard;
	Board board;
	@Before
	public void setup(){
		initializeBoard = new InitializeBoard();
		board = new Board();
	}
	
	@Test
	public void TestInitializeCity(){
		initializeBoard.initializeCity();
		assertEquals(48, board.cities.size());
		
	}
}
