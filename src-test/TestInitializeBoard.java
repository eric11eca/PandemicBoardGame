import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class TestInitializeBoard {
	InitializeBoard initializeBoard;
	Board board;
	@Before
	public void setup(){
		board = new Board();
		initializeBoard = new InitializeBoard(board);
	}
	
	@Test
	public void testInitializeCity(){
		initializeBoard.initializeCity();
		Map<String, City> cities = initializeBoard.board.cities;
		assertEquals(48, cities.size());
		City chicago = cities.get("Chicago");
		assertEquals("Chicago", chicago.cityName);
		assertEquals("BLUE", chicago.color);
	}
	
	@Test
	public void testInitializeInfectionCard() {
		String cityName = "Chicago";
		initializeBoard.initializeInfectionCard(cityName);
		Set<String> valid_infection_card = initializeBoard.board.valid_infection_card;
		assertTrue(valid_infection_card.contains(cityName));
	}
	
	@Test
	public void testInitializePlayerCard() {
		
	}
}
