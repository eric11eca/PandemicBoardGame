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
		epidemicCardAction.infect();
		City city = board.cities.get("cityA");
		assertTrue(0 == city.diseaseCubes.get("YELLOW"));
	}
	
	@Test
	public void testInfectionOfNewDisease() {
		board.validInfectionCard.add("cityB");
		epidemicCardAction.infect();
		City city = board.cities.get("cityB");
		assertTrue(3 == city.diseaseCubes.get("RED"));
	}
	
	@Test
	public void testReshuffleDiscardInfectionCardDeck() {
		board.discardInfectionCard.add("Chicaco");
		board.discardInfectionCard.add("NewYork");
		board.discardInfectionCard.add("London");
		board.discardInfectionCard.add("Atlanta");
		
		String oldDiscardInfectionCards = board.discardInfectionCard.toString();
		epidemicCardAction.reshuffleDiscardInfectionDeck();
		String newDiscardInfectionCards = board.discardInfectionCard.toString();
		assertFalse(oldDiscardInfectionCards.equals(newDiscardInfectionCards));
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

}
