package TestInitializeBoard;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import Initialize.Board;
import Initialize.City;
import Initialize.InitializeBoard;

public class TestInitializeDiseaseCube {
	InitializeBoard initializeBoard;
	Board board;
	
	@Before
	public void setup() {
		board = new Board();
		initializeBoard = new InitializeBoard(board);
		initializeBoard.board.validInfectionCard.add("a");
		initializeBoard.board.validInfectionCard.add("b");
		initializeBoard.board.validInfectionCard.add("c");
		initializeBoard.board.validInfectionCard.add("d");
		initializeBoard.board.validInfectionCard.add("e");
		initializeBoard.board.validInfectionCard.add("f");
		initializeBoard.board.validInfectionCard.add("g");
		initializeBoard.board.validInfectionCard.add("h");
		initializeBoard.board.validInfectionCard.add("i");
		initializeBoard.board.validInfectionCard.add("j");
		
		initializeBoard.board.cities.put("a", new City("a", "RED"));
		initializeBoard.board.cities.put("b", new City("b", "RED"));
		initializeBoard.board.cities.put("c", new City("c", "YELLOW"));
		initializeBoard.board.cities.put("d", new City("d", "BLUE"));
		initializeBoard.board.cities.put("e", new City("e", "YELLOW"));
		initializeBoard.board.cities.put("f", new City("f", "BLACK"));
		initializeBoard.board.cities.put("g", new City("g", "BLUE"));
		initializeBoard.board.cities.put("h", new City("h", "YELLOW"));
		initializeBoard.board.cities.put("i", new City("i", "RED"));
		initializeBoard.board.cities.put("j", new City("j", "BLUE"));
		
		board.remainDiseaseCube.put("RED", 24);
		board.remainDiseaseCube.put("BLUE", 24);
		board.remainDiseaseCube.put("BLACK", 24);
		board.remainDiseaseCube.put("YELLOW", 24);
	}
	
	@Test
	public void testPutCubeInFirstThreeInfectedCities() {		
		initializeBoard.initializeDiseaseCube();
		List<String> discardInfectionCard = initializeBoard.board.discardInfectionCard;
		Map<String, City> cities = initializeBoard.board.cities;
		String cardName = discardInfectionCard.get(0);
		City city = cities.get(cardName);
		String color = city.color;
		assertTrue(1 == city.diseaseCubes.get(color));
	}
	
	@Test
	public void testPutCubeInThirdThreeInfectedCities() {		
		initializeBoard.initializeDiseaseCube();
		List<String> discardInfectionCard = initializeBoard.board.discardInfectionCard;
		Map<String, City> cities = initializeBoard.board.cities;
		String cardName = discardInfectionCard.get(8);
		City city = cities.get(cardName);
		String color = city.color;
		assertTrue(3 == city.diseaseCubes.get(color));
	}
	
	@Test
	public void testPutCubeInSecondThreeInfectedCities() {
		initializeBoard.initializeDiseaseCube();
		List<String> discardInfectionCard = initializeBoard.board.discardInfectionCard;
		Map<String, City> cities = initializeBoard.board.cities;
		String cardName = discardInfectionCard.get(5);
		City city = cities.get(cardName);
		String color = city.color;
		assertTrue(2 == city.diseaseCubes.get(color));
	}
}
