package testOutbreak;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import data.Board;
import data.GameColor;
import game.Game;
import game.city.City;
import helpers.TestAccess;
import helpers.TestCityFactory;

public class TestOutbreak {
	Board board;
	// Outbreak outBreak;
	City city;
	City city1;
	City city2;
	City city3;
	City city4;

	TestCityFactory cityFactory = new TestCityFactory();
	TestAccess access = new TestAccess();

	@Before
	public void setup() {
		board = new Board();
		city = cityFactory.makeFakeCity("Paris", GameColor.RED);
		city1 = cityFactory.makeFakeCity("Chicago", GameColor.BLUE);
		city2 = cityFactory.makeFakeCity("NewYork", GameColor.BLACK);
		city3 = cityFactory.makeFakeCity("London", GameColor.BLUE);
		city4 = cityFactory.makeFakeCity("Austin", GameColor.BLACK);
		city.neighbors.add(city1);
		city.neighbors.add(city2);
		board.cities.put(city.getName(), city);
		board.cities.put(city1.getName(), city1);
		board.cities.put(city2.getName(), city2);
		board.cities.put(city3.getName(), city3);
		board.cities.put(city4.getName(), city4);
		access.resetGame();
		Game.getInstance().initializeDiseaseCubes();
//		board.remainDiseaseCube.put("RED", 24);
//		board.remainDiseaseCube.put("BLUE", 24);
//		board.remainDiseaseCube.put("BLACK", 24);
//		board.remainDiseaseCube.put("YELLOW", 24);
//		outBreak = new Outbreak(board);
	}

	@Test
	public void testMoveOutbreakMarkForward() {
		Game.getInstance().moveOutbreakMarkForward();
		assertEquals(1, access.getGameOutbreakMark());
	}

	@Test
	public void testMoveOutbreakMarkForwardEndGame() {
		access.setGameOutbreakMark(7);
		Game.getInstance().moveOutbreakMarkForward();
		assertTrue(Game.getInstance().isLost());
	}

	@Test
	public void testPlaceDiseaseCubeOnConnectedCities() {
		access.outbreak(city, city.getColor());
		// List<City> continueOutbreak = outBreak.infectConnectedCities(city);
		// assertTrue(continueOutbreak.isEmpty());
		int numOfCubesCity1 = access.getCityDisease(city1).getDiseaseCubeCount(GameColor.RED);
		int numOfCubesCity2 = access.getCityDisease(city2).getDiseaseCubeCount(GameColor.RED);
		assertEquals(1, numOfCubesCity1);
		assertEquals(1, numOfCubesCity2);
		assertTrue(22 == access.getGameCubeData().getDiseaseCubeCount(GameColor.RED));// board.remainDiseaseCube.get("RED"));
	}

	@Test
	public void testPerformeOutbreak() {
		access.outbreak(city, city.getColor());
		// assertTrue(city.isInOutbreak);
		assertEquals(1, access.getGameOutbreakMark());
		int numOfCubesCity1 = access.getCityDisease(city1).getDiseaseCubeCount(GameColor.RED);
		int numOfCubesCity2 = access.getCityDisease(city2).getDiseaseCubeCount(GameColor.RED);
		assertEquals(1, numOfCubesCity1);
		assertEquals(1, numOfCubesCity2);
	}

//	@Test
//	public void testInfecAndCauseOtherOutbreaks() {
//		access.getCityDisease(city1).setDiseaseCubeCount(GameColor.RED, 2);
//		access.getCityDisease(city2).setDiseaseCubeCount(GameColor.RED, 3);
//		access.outbreak(city, city.getColor());
//		List<City> continueOutbreak = outBreak.infectConnectedCities(city);
//		assertTrue(!continueOutbreak.isEmpty());
//		assertEquals(city1, continueOutbreak.get(0));
//		assertEquals(city2, continueOutbreak.get(1));
//	}

	@Test
	public void testChainReaction() {
		access.getCityDisease(city1).setDiseaseCubeCount(GameColor.RED, 2);
		access.getCityDisease(city2).setDiseaseCubeCount(GameColor.RED, 3);
		city1.neighbors.add(city3);
		city2.neighbors.add(city4);

		access.setGameOutbreakMark(1);
		access.outbreak(city, city.getColor());
		// List<City> continueOutbreak = outBreak.infectConnectedCities(city);
		// outBreak.continueRestOfOutbreaks(continueOutbreak);

		assertEquals(3, access.getGameOutbreakMark());
		// assertTrue(city1.isInOutbreak);
		// assertTrue(city2.isInOutbreak);

		assertEquals(3, access.getCityDisease(city1).getDiseaseCubeCount(GameColor.RED));
		assertEquals(3, access.getCityDisease(city2).getDiseaseCubeCount(GameColor.RED));
		assertEquals(0, access.getCityDisease(city3).getDiseaseCubeCount(GameColor.RED));
		assertEquals(1, access.getCityDisease(city4).getDiseaseCubeCount(GameColor.RED));
	}

	@Test
	public void testChainReactionWithCityAlreadyInOutbreak() {
		city.neighbors.add(city3);
		HashSet<City> inOutBreak = new HashSet<>();
		inOutBreak.add(city3);
		// city3.isInOutbreak = true;
		// board.outbreakMark += 1;
		access.setGameOutbreakMark(0);
		access.outbreak(city, city.getColor(), inOutBreak);
		// List<City> continueOutbreak = outBreak.infectConnectedCities(city);
		// outBreak.continueRestOfOutbreaks(continueOutbreak);
		assertEquals(1, access.getGameOutbreakMark());
		assertEquals(0, access.getCityDisease(city3).getDiseaseCubeCount(GameColor.RED));
	}

	@Test
	public void testPerformeOutbreakWithChainReaction() {
		access.getCityDisease(city1).setDiseaseCubeCount(GameColor.RED, 3);
		city1.neighbors.add(city3);
		access.outbreak(city, city.getColor());
		// assertTrue(city.isInOutbreak);
		// assertTrue(city1.isInOutbreak);
		assertEquals(2, access.getGameOutbreakMark());
		int numOfCubesCity1 = access.getCityDisease(city1).getDiseaseCubeCount(GameColor.RED);
		int numOdCubesCity3 = access.getCityDisease(city3).getDiseaseCubeCount(GameColor.RED);
		assertEquals(3, numOfCubesCity1);
		assertEquals(1, numOdCubesCity3);
	}

	@Test
	public void testEndGameWhenOutbreakMarkIsMaximum() {
		access.setGameOutbreakMark(7);
		access.outbreak(city, city.getColor());
		assertTrue(Game.getInstance().isLost());
	}

	@Test
	public void testEndGameWhenNoMoreDiseaseCubeLeft() {
		access.getGameCubeData().setDiseaseCubeCount(GameColor.RED, 0);
		// board.remainDiseaseCube.put("RED", 0);
		access.outbreak(city, city.getColor());
		assertTrue(Game.getInstance().isLost());
	}

	@Test
	public void testEndGameInChainReactionWhenOutbreakMarkIsMaximum() {
		access.setGameOutbreakMark(6);
//		board.outbreakMark = 6;
		access.getCityDisease(city1).setDiseaseCubeCount(GameColor.RED, 3);
		city1.neighbors.add(city3);
		access.outbreak(city, city.getColor());
		assertTrue(Game.getInstance().isLost());
	}

	@Test
	public void testEndGameInChainReactionWhenNoMoreDiseaseCubeLeft() {
		access.getGameCubeData().setDiseaseCubeCount(GameColor.RED, 0);

		access.getCityDisease(city1).setDiseaseCubeCount(GameColor.RED, 3);
		access.getCityDisease(city2).setDiseaseCubeCount(GameColor.RED, 2);
		access.outbreak(city, city.getColor());// outBreak.infectConnectedCities(city);
		assertTrue(Game.getInstance().isLost());
	}
}
