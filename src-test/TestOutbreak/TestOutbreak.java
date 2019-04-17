package TestOutbreak;

import static org.junit.Assert.*;

import java.util.List;

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
	City city3;
	City city4;
	
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
		city3 = new City();
		city3.cityName = "London";
		city3.color = "RED";
		city3.diseaseCubes.put("RED", 0);
		city3.diseaseCubes.put("BLUE", 0);
		city3.diseaseCubes.put("BLACK", 0);
		city4 = new City();
		city4.cityName = "Austin";
		city4.color = "BLACK";
		city4.diseaseCubes.put("RED", 0);
		city4.diseaseCubes.put("BLUE", 0);
		city4.diseaseCubes.put("BLACK", 0);
		city.neighbors.put(city1.cityName, city1);
		city.neighbors.put(city2.cityName, city2);
		board.cities.put(city.cityName, city);
		board.cities.put(city1.cityName, city1);
		board.cities.put(city2.cityName, city2);
		board.cities.put(city3.cityName, city3);
		board.cities.put(city4.cityName, city4);
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
		List<City> continueOutbreak = outBreak.infectConnectedCities(city);
		assertTrue(continueOutbreak.isEmpty());
		int numOfCubesCity1 = city1.diseaseCubes.get("RED");
		int numOfCubesCity2 = city2.diseaseCubes.get("RED");
		assertEquals(1, numOfCubesCity1);
		assertEquals(1, numOfCubesCity2);
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
		List<City> continueOutbreak = outBreak.infectConnectedCities(city);
		assertTrue(!continueOutbreak.isEmpty());
		assertEquals(city1, continueOutbreak.get(0));
		assertEquals(city2, continueOutbreak.get(1));
	}
	
	@Test
	public void testChainReaction() {
		city1.diseaseCubes.put("RED", 2);
		city2.diseaseCubes.put("RED", 3);
		city1.neighbors.put(city3.cityName, city3);
		city2.neighbors.put(city4.cityName, city4);
		
		board.outbreakMark += 1;
		List<City> continueOutbreak = outBreak.infectConnectedCities(city);
		outBreak.continueRestOfOutbreaks(continueOutbreak);
		
		assertEquals(3, board.outbreakMark);
		assertTrue(city1.isInOutbreak);
		assertTrue(city2.isInOutbreak);
		
		assertTrue(3 == city1.diseaseCubes.get("RED"));
		assertTrue(3 == city2.diseaseCubes.get("RED"));
		assertTrue(1 == city3.diseaseCubes.get("BLUE"));
		assertTrue(1 == city4.diseaseCubes.get("BLACK"));
	}
	
	@Test public void testChainReactionWithCityAlreadyInOutbreak() {
		city.neighbors.put(city3.cityName, city3);
		city3.isInOutbreak = true;
		board.outbreakMark += 1;
		List<City> continueOutbreak = outBreak.infectConnectedCities(city);
		outBreak.continueRestOfOutbreaks(continueOutbreak);
		assertEquals(1, board.outbreakMark);
		assertTrue(0 == city3.diseaseCubes.get("RED"));
	}
	
	@Test
	public void testPerformeOutbreakWithChainReaction() {
		city1.diseaseCubes.put("RED", 3);
		outBreak.performeOutbreak(city);
		assertTrue(city.isInOutbreak);
		assertTrue(city1.isInOutbreak);
		assertTrue(2 == board.outbreakMark);
		int numOfCubesCity = city1.diseaseCubes.get("RED");
		assertEquals(3, numOfCubesCity);
	}

	


}
