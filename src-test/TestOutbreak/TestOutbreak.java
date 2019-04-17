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
	City city1;
	City city2;
	@Before 
	public void setup() {
		board = new Board();
		city = new City();
		city.cityName = "Paris";
		city.color = "RED";
		city.diseaseCubes.put("RED", 0);
		city1 = new City();
		city1.cityName = "Chicago";
		city1.color = "BLUE";
		city1.diseaseCubes.put("RED", 0);
		city2 = new City();
		city2.cityName = "NewYork";
		city2.color = "BLACK";
		city2.diseaseCubes.put("RED", 0);
		city.neighbors.put(city1.cityName, city1);
		city.neighbors.put(city2.cityName, city2);
		board.cities.put(city.cityName, city);
		board.cities.put(city1.cityName, city1);
		board.cities.put(city2.cityName, city2);
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
		int numOfCubesCity1 = city1.diseaseCubes.get("RED");
		int numOfCubesCity2 = city2.diseaseCubes.get("RED");
		assertEquals(1, numOfCubesCity1);
		assertEquals(1, numOfCubesCity2);
	}
	
	@Test
	public void testPerformeOutbreak() {
		boolean outbreakPerformed = outBreak.performeOutbreak(city);
		assertTrue(city.isInOutbreak);
		assertTrue(outbreakPerformed);
	}
	
	@Test 
	public void testInfecAndCauseOtherOutbreaks() {
		city1.diseaseCubes.put("RED", 2);
		city2.diseaseCubes.put("RED", 3);
		boolean infected = outBreak.infectConnectedCities(city);
		assertTrue(infected);
		assertEquals(city1, outBreak.continueOutbreak.get(0));
		assertEquals(city2, outBreak.continueOutbreak.get(1));
	}
	
	//@Test
	public void testChainReaction() {
		City city1 = board.cities.get("Chicago");
		City city2 = board.cities.get("NewYork");
		
		city1.diseaseCubes.put("RED", 2);
		city2.diseaseCubes.put("RED", 3);

		boolean outbreak = outBreak.performeOutbreak(city);
		
		assertTrue(outbreak);
		assertTrue(city.isInOutbreak);
		assertEquals(3, board.outbreakMark);
		
		assertTrue(city1.isInOutbreak);
		assertTrue(city2.isInOutbreak);
		
		assertTrue(3 == city1.diseaseCubes.get("RED"));
		assertTrue(3 == city2.diseaseCubes.get("RED"));
	}

	
	@Test
	public void testChainReactionWithCityAlreadyHadOutbrek() {
		City city1 = board.cities.get("Chicago");
		
		City city3 = new City();
		city3.cityName = "London";
		city3.color = "BLACK";
		city3.diseaseCubes.put("RED", 0);
		city3.diseaseCubes.put("BLUE", 0);
		
		board.cities.put(city3.cityName, city3);
		city.neighbors.put(city3.cityName, city3);
		city1.neighbors.put(city3.cityName, city3);
		city1.diseaseCubes.put("RED", 3);

		boolean outbreak1 = outBreak.performeOutbreak(city);
		assertTrue(outbreak1);
		assertTrue(city1.isInOutbreak);
		assertTrue(city3.isInOutbreak);
		
		boolean outbreak2 = outBreak.performeOutbreak(city1);
		assertTrue(outbreak2);
		assertTrue(0 == city3.diseaseCubes.get("BLUE"));
		assertTrue(1 == city3.diseaseCubes.get("RED"));
	}

}
