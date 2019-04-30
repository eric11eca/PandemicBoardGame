package TestPlayerCardAction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Card.EpidemicCardAction;
import Initialize.Board;
import Initialize.City;

public class TestEpidemicCardAction {

	Board board;
	EpidemicCardAction epidemicCardAction;
	
	@Before 
	public void setup() {
		board = new Board();
		
		board.infectionRateTrack.push(4);
		board.infectionRateTrack.push(4);
		board.infectionRateTrack.push(3);
		board.infectionRateTrack.push(3);
		board.infectionRateTrack.push(2);
		board.infectionRateTrack.push(2);
		board.infectionRateTrack.push(2);
		
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
		board.eradicatedDiseases.add(city1.color);		
		epidemicCardAction = new EpidemicCardAction(board);
		board.remainDiseaseCube.put("RED", 24);
	}
	
	@Test
	public void testInfectionRateIncrease() {
		epidemicCardAction.increaseInfectionRate();
		assertTrue(2 == board.infectionRateTrack.peek());
	}
	
	@Test
	public void testInfectionRateIncreaseWhenIsMax() {
		for (int i = 0; i < 6; i++) {
			epidemicCardAction.increaseInfectionRate();
		}
		assertTrue(4 == board.infectionRateTrack.peek());
		epidemicCardAction.increaseInfectionRate();
		assertTrue(4 == board.infectionRateTrack.peek());
	}
	
	@Test
	public void testInfectionOfEradicatedDisease() {
		board.validInfectionCard.add("cityA");
		epidemicCardAction.epidemicInfect();
		City city = board.cities.get("cityA");
		assertTrue(0 == city.diseaseCubes.get("YELLOW"));
	}
	
	@Test
	public void testInfectionOfNewDisease() {
		board.validInfectionCard.add("cityB");
		epidemicCardAction.epidemicInfect();
		City city = board.cities.get("cityB");
		assertTrue(3 == city.diseaseCubes.get("RED"));
		assertTrue(21 == board.remainDiseaseCube.get("RED"));
		assertTrue(board.validInfectionCard.isEmpty());
		assertFalse(board.discardInfectionCard.isEmpty());
		assertFalse(city.isInOutbreak);
	}
	
	@Test 
	public void testInfectionCausingOutbreak() {
		board.validInfectionCard.add("cityB");
		City cityB = board.cities.get("cityB");
		cityB.diseaseCubes.put(cityB.color, 2);
		board.remainDiseaseCube.put(cityB.color, 22);
		epidemicCardAction.epidemicInfect();
		assertTrue(cityB.isInOutbreak);
		assertTrue(3 == cityB.diseaseCubes.get("RED"));
		assertTrue(21 == board.remainDiseaseCube.get("RED"));
		assertTrue(board.validInfectionCard.isEmpty());
		assertFalse(board.discardInfectionCard.isEmpty());
	}
	
	@Test
	public void testReshuffleDiscardInfectionCardDeck() {
		board.discardInfectionCard.add("Chicaco");
		board.discardInfectionCard.add("NewYork");
		board.discardInfectionCard.add("London");
		board.discardInfectionCard.add("Atlanta");
		
		String oldDiscardInfectionCards = board.discardInfectionCard.toString();
		boolean reshuffled = epidemicCardAction.reshuffleDiscardInfectionDeck();
		String newDiscardInfectionCards = board.discardInfectionCard.toString();
		assertFalse(oldDiscardInfectionCards.equals(newDiscardInfectionCards));
		assertTrue(reshuffled);
	}
	
	@Test 
	public void testNewInfectionCardDeck() {
		board.validInfectionCard.add("Paris");
		board.discardInfectionCard.add("Chicaco");
		board.discardInfectionCard.add("NewYork");
		board.discardInfectionCard.add("London");
		board.discardInfectionCard.add("Atlanta");
		epidemicCardAction.makingNewInfectionCardDeck();
		int newSize = board.validInfectionCard.size();
		assertEquals(5, newSize);
		assertTrue(board.discardInfectionCard.isEmpty());
	}
	
	@Test
	public void testPerformeEpidemic() {
		board.infectionRateTrack.pop();
		board.infectionRateTrack.pop();
		board.discardInfectionCard.add("Chicaco");
		board.discardInfectionCard.add("NewYork");
		board.discardInfectionCard.add("London");
		board.discardInfectionCard.add("Atlanta");
		board.validInfectionCard.add("cityB");
		
		epidemicCardAction.performeEpidemic();
		assertTrue(3 == board.infectionRateTrack.peek());
		
		City city = board.cities.get("cityB");
		assertTrue(3 == city.diseaseCubes.get("RED"));
		
		int newSize = board.validInfectionCard.size();
		assertEquals(5, newSize);
		assertTrue(board.discardInfectionCard.isEmpty());
	}

	@Test 
	public void testEndGameDuringInfection() {
		board.validInfectionCard.add("cityB");
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
		board.validInfectionCard.add("cityB");
		epidemicCardAction.performeEpidemic();
		assertTrue(board.gameEnd);
		assertTrue(board.playerLose);
		assertEquals(1, board.validInfectionCard.size());
	}
}
