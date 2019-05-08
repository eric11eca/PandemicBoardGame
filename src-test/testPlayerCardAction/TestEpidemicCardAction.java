package testPlayerCardAction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cardActions.EpidemicCardAction;
import initialize.Board;
import initialize.City;

public class TestEpidemicCardAction {

	Board board;
	EpidemicCardAction epidemicCardAction;
	
	@Before 
	public void setup() {
		board = new Board();
		
		board.infectionRateTracker.push(4);
		board.infectionRateTracker.push(4);
		board.infectionRateTracker.push(3);
		board.infectionRateTracker.push(3);
		board.infectionRateTracker.push(2);
		board.infectionRateTracker.push(2);
		board.infectionRateTracker.push(2);
		
		City city1 = new City();
		city1.cityName = "cityA";
		city1.color = "YELLOW";
		city1.diseaseCubes.put(city1.color, 0);
		board.cities.put(city1.cityName, city1);
		City city2 = new City();
		city2.cityName = "cityB";
		city2.color = "RED";
		city2.diseaseCubes.put(city2.color, 0);
		board.cities.put(city2.cityName, city2);
		board.eradicatedColor.add(city1.color);		
		epidemicCardAction = new EpidemicCardAction(board);
		board.remainDiseaseCube.put("RED", 24);
	}
	
	@Test
	public void testInfectionRateIncrease() {
		epidemicCardAction.increaseInfectionRate();
		assertTrue(2 == board.infectionRateTracker.peek());
	}
	
	@Test
	public void testInfectionRateIncreaseWhenIsMax() {
		for (int i = 0; i < 6; i++) {
			epidemicCardAction.increaseInfectionRate();
		}
		assertTrue(4 == board.infectionRateTracker.peek());
		epidemicCardAction.increaseInfectionRate();
		assertTrue(4 == board.infectionRateTracker.peek());
	}
	
	@Test
	public void testInfectionOfEradicatedDisease() {
		board.validInfectionCards.add("cityA");
		epidemicCardAction.epidemicInfect();
		City city = board.cities.get("cityA");
		assertTrue(0 == city.diseaseCubes.get("YELLOW"));
	}
	
	@Test
	public void testInfectionOfNewDisease() {
		board.validInfectionCards.add("cityB");
		epidemicCardAction.epidemicInfect();
		City city = board.cities.get("cityB");
		assertTrue(3 == city.diseaseCubes.get("RED"));
		assertTrue(21 == board.remainDiseaseCube.get("RED"));
		assertTrue(board.validInfectionCards.isEmpty());
		assertFalse(board.discardInfectionCards.isEmpty());
		assertFalse(city.isInOutbreak);
	}
	
	@Test 
	public void testInfectionCausingOutbreak() {
		board.validInfectionCards.add("cityB");
		City cityB = board.cities.get("cityB");
		cityB.diseaseCubes.put(cityB.color, 2);
		board.remainDiseaseCube.put(cityB.color, 22);
		epidemicCardAction.epidemicInfect();
		assertTrue(cityB.isInOutbreak);
		assertTrue(3 == cityB.diseaseCubes.get("RED"));
		assertTrue(21 == board.remainDiseaseCube.get("RED"));
		assertTrue(board.validInfectionCards.isEmpty());
		assertFalse(board.discardInfectionCards.isEmpty());
	}
	
	@Test
	public void testReshuffleDiscardInfectionCardDeck() {
		board.discardInfectionCards.add("Chicaco");
		board.discardInfectionCards.add("NewYork");
		board.discardInfectionCards.add("London");
		board.discardInfectionCards.add("Atlanta");
		
		String oldDiscardInfectionCards = board.discardInfectionCards.toString();
		boolean reshuffled = epidemicCardAction.reshuffleDiscardInfectionDeck();
		String newDiscardInfectionCards = board.discardInfectionCards.toString();
		assertFalse(oldDiscardInfectionCards.equals(newDiscardInfectionCards));
		assertTrue(reshuffled);
	}
	
	@Test 
	public void testNewInfectionCardDeck() {
		board.validInfectionCards.add("Paris");
		board.discardInfectionCards.add("Chicaco");
		board.discardInfectionCards.add("NewYork");
		board.discardInfectionCards.add("London");
		board.discardInfectionCards.add("Atlanta");
		epidemicCardAction.makingNewInfectionCardDeck();
		int newSize = board.validInfectionCards.size();
		assertEquals(5, newSize);
		assertTrue(board.discardInfectionCards.isEmpty());
	}
	
	@Test
	public void testPerformeEpidemic() {
		board.infectionRateTracker.pop();
		board.infectionRateTracker.pop();
		board.discardInfectionCards.add("Chicaco");
		board.discardInfectionCards.add("NewYork");
		board.discardInfectionCards.add("London");
		board.discardInfectionCards.add("Atlanta");
		board.validInfectionCards.add("cityB");
		
		epidemicCardAction.performEpidemic();
		assertTrue(3 == board.infectionRateTracker.peek());
		
		City city = board.cities.get("cityB");
		assertTrue(3 == city.diseaseCubes.get("RED"));
		
		int newSize = board.validInfectionCards.size();
		assertEquals(5, newSize);
		assertTrue(board.discardInfectionCards.isEmpty());
	}

	@Test 
	public void testEndGameDuringInfection() {
		board.validInfectionCards.add("cityB");
		board.remainDiseaseCube.put("RED", 0);
		epidemicCardAction.epidemicInfect();
		City city = board.cities.get("cityB");
		assertTrue(board.gameEnd);
		assertTrue(board.playerLose);
		assertTrue(0 == city.diseaseCubes.get("RED"));
		assertFalse(city.isInOutbreak);
	}
	
	@Test
	public void testEndGameWhenPerformeEpidemic() {
		board.remainDiseaseCube.put("RED", 0);
		board.validInfectionCards.add("cityB");
		epidemicCardAction.performEpidemic();
		assertTrue(board.gameEnd);
		assertTrue(board.playerLose);
		assertEquals(1, board.validInfectionCards.size());
	}
}
