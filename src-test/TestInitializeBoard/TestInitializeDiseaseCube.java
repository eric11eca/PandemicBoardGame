package TestInitializeBoard;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import Initialize.Board;
import Initialize.City;
import Initialize.InitializeCity;

public class TestInitializeDiseaseCube {
	InitializeCity initializeBoard;
	Board board;
	
	@Before
	public void setup() {
		board = new Board();
		initializeBoard = new InitializeCity(board);
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
		
		initializeBoard.board.cities.put("a", new City("a"));
		initializeBoard.board.cities.put("b", new City("b"));
		initializeBoard.board.cities.put("c", new City("c"));
		initializeBoard.board.cities.put("d", new City("d"));
		initializeBoard.board.cities.put("e", new City("e"));
		initializeBoard.board.cities.put("f", new City("f"));
		initializeBoard.board.cities.put("g", new City("g"));
		initializeBoard.board.cities.put("h", new City("h"));
		initializeBoard.board.cities.put("i", new City("i"));
		initializeBoard.board.cities.put("j", new City("j"));
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
