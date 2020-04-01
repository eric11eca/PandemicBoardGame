package testOutbreak;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cardActions.Outbreak;
import data.Board;
import data.CityData;
import data.CityOLD;
import data.GameColor;

public class TestOutbreak {
	Board board;
	Outbreak outBreak;
	CityOLD city;
	CityOLD city1;
	CityOLD city2;
	CityOLD city3;
	CityOLD city4;

	@Before
	public void setup() {
		board = new Board();
		city = new CityOLD(new CityData("Paris", GameColor.RED, 10), 0, 0);
		city.diseaseCubes.put("RED", 0);
		city1 = new CityOLD(new CityData("Chicago", GameColor.BLUE, 10), 0, 0);
		city1.diseaseCubes.put("RED", 0);
		city2 = new CityOLD(new CityData("NewYork", GameColor.BLACK, 10), 0, 0);
		city2.diseaseCubes.put("RED", 0);
		city3 = new CityOLD(new CityData("London", GameColor.BLUE, 10), 0, 0);
		city3.diseaseCubes.put("RED", 0);
		city3.diseaseCubes.put("BLUE", 0);
		city3.diseaseCubes.put("BLACK", 0);
		city4 = new CityOLD(new CityData("Austin", GameColor.BLACK, 10), 0, 0);
		city4.diseaseCubes.put("RED", 0);
		city4.diseaseCubes.put("BLUE", 0);
		city4.diseaseCubes.put("BLACK", 0);
		city.neighbors.add(city1);
		city.neighbors.add(city2);
		board.cities.put(city.getName(), city);
		board.cities.put(city1.getName(), city1);
		board.cities.put(city2.getName(), city2);
		board.cities.put(city3.getName(), city3);
		board.cities.put(city4.getName(), city4);
		board.remainDiseaseCube.put("RED", 24);
		board.remainDiseaseCube.put("BLUE", 24);
		board.remainDiseaseCube.put("BLACK", 24);
		board.remainDiseaseCube.put("YELLOW", 24);
		outBreak = new Outbreak(board);
	}

	@Test
	public void testMoveOutbreakMarkForward() {
		outBreak.moveOutbreakMarkForward();
		assertTrue(1 == board.outbreakMark);
	}

	@Test(expected = RuntimeException.class)
	public void testMoveOutbreakMarkForwardEndGame() {
		board.outbreakMark = 7;
		outBreak.moveOutbreakMarkForward();
	}

	@Test
	public void testPlaceDiseaseCubeOnConnectedCities() {
		List<CityOLD> continueOutbreak = outBreak.infectConnectedCities(city);
		assertTrue(continueOutbreak.isEmpty());
		int numOfCubesCity1 = city1.diseaseCubes.get("RED");
		int numOfCubesCity2 = city2.diseaseCubes.get("RED");
		assertEquals(1, numOfCubesCity1);
		assertEquals(1, numOfCubesCity2);
		assertTrue(22 == board.remainDiseaseCube.get("RED"));
	}

	@Test
	public void testPerformeOutbreak() {
		outBreak.performeOutbreak(city);
		assertTrue(city.isInOutbreak);
		assertTrue(1 == board.outbreakMark);
		int numOfCubesCity1 = city1.diseaseCubes.get("RED");
		int numOfCubesCity2 = city2.diseaseCubes.get("RED");
		assertEquals(1, numOfCubesCity1);
		assertEquals(1, numOfCubesCity2);
	}

	@Test
	public void testInfecAndCauseOtherOutbreaks() {
		city1.diseaseCubes.put("RED", 2);
		city2.diseaseCubes.put("RED", 3);
		List<CityOLD> continueOutbreak = outBreak.infectConnectedCities(city);
		assertTrue(!continueOutbreak.isEmpty());
		assertEquals(city1, continueOutbreak.get(0));
		assertEquals(city2, continueOutbreak.get(1));
	}

	@Test
	public void testChainReaction() {
		city1.diseaseCubes.put("RED", 2);
		city2.diseaseCubes.put("RED", 3);
		city1.neighbors.add(city3);
		city2.neighbors.add(city4);

		board.outbreakMark += 1;
		List<CityOLD> continueOutbreak = outBreak.infectConnectedCities(city);
		outBreak.continueRestOfOutbreaks(continueOutbreak);

		assertEquals(3, board.outbreakMark);
		assertTrue(city1.isInOutbreak);
		assertTrue(city2.isInOutbreak);

		assertTrue(3 == city1.diseaseCubes.get("RED"));
		assertTrue(3 == city2.diseaseCubes.get("RED"));
		assertTrue(1 == city3.diseaseCubes.get("BLUE"));
		assertTrue(1 == city4.diseaseCubes.get("BLACK"));
	}

	@Test
	public void testChainReactionWithCityAlreadyInOutbreak() {
		city.neighbors.add(city3);
		city3.isInOutbreak = true;
		board.outbreakMark += 1;
		List<CityOLD> continueOutbreak = outBreak.infectConnectedCities(city);
		outBreak.continueRestOfOutbreaks(continueOutbreak);
		assertEquals(1, board.outbreakMark);
		assertTrue(0 == city3.diseaseCubes.get("RED"));
	}

	@Test
	public void testPerformeOutbreakWithChainReaction() {
		city1.diseaseCubes.put("RED", 3);
		city1.neighbors.add(city3);
		outBreak.performeOutbreak(city);
		assertTrue(city.isInOutbreak);
		assertTrue(city1.isInOutbreak);
		assertTrue(2 == board.outbreakMark);
		int numOfCubesCity1 = city1.diseaseCubes.get("RED");
		int numOdCubesCity3 = city3.diseaseCubes.get("BLUE");
		assertEquals(3, numOfCubesCity1);
		assertEquals(1, numOdCubesCity3);
	}

	@Test(expected = RuntimeException.class)
	public void testEndGameWhenOutbreakMarkIsMaximum() {
		board.outbreakMark = 7;
		outBreak.performeOutbreak(city);
	}

	@Test(expected = RuntimeException.class)
	public void testEndGameWhenNoMoreDiseaseCubeLeft() {
		board.remainDiseaseCube.put("RED", 0);
		outBreak.performeOutbreak(city);
	}

	@Test(expected = RuntimeException.class)
	public void testEndGameInChainReactionWhenOutbreakMarkIsMaximum() {
		board.outbreakMark = 6;
		city1.diseaseCubes.put("RED", 3);
		city1.neighbors.add(city3);
		outBreak.performeOutbreak(city);
	}

	@Test(expected = RuntimeException.class)
	public void testEndGameInChainReactionWhenNoMoreDiseaseCubeLeft() {
		board.remainDiseaseCube.put("RED", 0);
		city1.diseaseCubes.put("RED", 3);
		city2.diseaseCubes.put("RED", 2);
		outBreak.infectConnectedCities(city);
	}
}
