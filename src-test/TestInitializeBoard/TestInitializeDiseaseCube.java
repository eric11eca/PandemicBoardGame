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
		
		initializeBoard.board.cities.put("a", new City("a", "", 0, 0, 0));
		initializeBoard.board.cities.put("b", new City("b", "", 0, 0, 0));
		initializeBoard.board.cities.put("c", new City("c", "", 0, 0, 0));
		initializeBoard.board.cities.put("d", new City("d", "", 0, 0, 0));
		initializeBoard.board.cities.put("e", new City("e", "", 0, 0, 0));
		initializeBoard.board.cities.put("f", new City("f", "", 0, 0, 0));
		initializeBoard.board.cities.put("g", new City("g", "", 0, 0, 0));
		initializeBoard.board.cities.put("h", new City("h", "", 0, 0, 0));
		initializeBoard.board.cities.put("i", new City("i", "", 0, 0, 0));
		initializeBoard.board.cities.put("j", new City("j", "", 0, 0, 0));
	}
	
	@Test
	public void testPutCubeInFirstThreeInfectedCities() {		
		initializeBoard.initializeDiseaseCube();
		List<String> discardInfectionCard = initializeBoard.board.discardInfectionCard;
		Map<String, City> cities = initializeBoard.board.cities;
		String cardName = discardInfectionCard.get(0);
		City city = cities.get(cardName);
		String cubeColor = city.color;
		assertTrue(1 == city.diseaseCubes.get(cubeColor));
	}
	
	@Test
	public void testPutCubeInThirdThreeInfectedCities() {		
		initializeBoard.initializeDiseaseCube();
		List<String> discardInfectionCard = initializeBoard.board.discardInfectionCard;
		Map<String, City> cities = initializeBoard.board.cities;
		String cardName = discardInfectionCard.get(8);
		City city = cities.get(cardName);
		String cubeColor = city.color;
		assertTrue(3 == city.diseaseCubes.get(cubeColor));
	}
	
	@Test
	public void testPutCubeInSecondThreeInfectedCities() {
		initializeBoard.initializeDiseaseCube();
		List<String> discardInfectionCard = initializeBoard.board.discardInfectionCard;
		Map<String, City> cities = initializeBoard.board.cities;
		String cardName = discardInfectionCard.get(5);
		City city = cities.get(cardName);
		String cubeColor = city.color;
		assertTrue(2 == city.diseaseCubes.get(cubeColor));
	}
}
