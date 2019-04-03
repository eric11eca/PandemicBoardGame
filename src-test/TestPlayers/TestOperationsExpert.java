package TestPlayers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.Board;
import Initialize.City;
import Player.OperationsExpert;
import Player.Player;

public class TestOperationsExpert {
	OperationsExpert operationsExpert;
	City location;
	Board board;
	@Before
	public void setup() {
		board = new Board();
		operationsExpert = new OperationsExpert(board);
		location = operationsExpert.location;
	}

	@Test
	public void testBuildResearchStation() {
		assertFalse(location.researchStation);
		operationsExpert.buildResearchStation();
		assertTrue(location.researchStation);
		assertEquals(operationsExpert.action, 3);
	}
	
	@Test 
	public void testMoveToAnotherCity() {
		String cityName = "NewYork";
		String new_cityName = "Chicago";
		City city = new City();
		city.cityName = new_cityName;
		board.cities.put(new_cityName, city);
		PlayerCard cityCard = new PlayerCard(Board.CardType.CITYCARD, new_cityName);
		operationsExpert.hand.put(new_cityName, cityCard);
		location.cityName = cityName;
		operationsExpert.moveToAnotherCity(new_cityName);
		assertEquals(new_cityName, operationsExpert.location.cityName);
	}

}
