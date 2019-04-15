package TestOutbreak;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Card.Outbreak;
import Initialize.Board;
import Initialize.City;

public class TestOutbreak {
	Board board;
	Outbreak outBreak;
	City city;
	@Before 
	public void setup() {
		board = new Board();
		city = new City();
		city.cityName = "Paris";
		city.color = "RED";
		city.diseaseCubes.put("RED", 0);
		City city1 = new City();
		city1.cityName = "Chicago";
		city1.color = "BLUE";
		city1.diseaseCubes.put("RED", 0);
		City city2 = new City();
		city2.cityName = "NewYork";
		city2.color = "YELLOW";
		city2.diseaseCubes.put("RED", 0);
		city.neighbors.add(city1);
		city.neighbors.add(city2);
		outBreak = new Outbreak(board);
	}

	@Test
	public void testMoveOutbreakMarkForward() {
		outBreak.moveOutbreakMarkForward();
		assertTrue(1 == board.outbreakMark);
	}
	
	@Test 
	public void testMoveOutbreakMarkForwardEndGame() {
		board.outbreakMark = 7;
		outBreak.moveOutbreakMarkForward();
		assertTrue(board.gameEnd);
		assertTrue(board.playerLose);
	}
	
	@Test
	public void testPlaceDiseaseCubeOnConnectedCities() {
		boolean infected = outBreak.infectConnectedCities(city);
		assertTrue(infected);
		for(City neighbor : city.neighbors) {
			int numOfCubes = neighbor.diseaseCubes.get("RED");
			assertEquals(1, numOfCubes);
		}	
	}
	
	@Test
	public void testPerformeOutbreak() {
		boolean outbreakPerformed = outBreak.performeOutbreak(city);
		assertTrue(city.isInOutbreak);
		assertTrue(outbreakPerformed);
	}
	
	@Test
	public void testChainReaction() {
		boolean infected = outBreak.infectConnectedCities(city);
		assertTrue(infected);
	}

}
