import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class TestInitializeBoard {
	InitializeBoard initializeBoard;
	@Before
	public void setup(){
		initializeBoard = new InitializeBoard();
	}
	
	@Test
	public void TestInitializeCity(){
		initializeBoard.initializeCity();
		assertEquals(48, initializeBoard.board.cities.size());
	}
}
