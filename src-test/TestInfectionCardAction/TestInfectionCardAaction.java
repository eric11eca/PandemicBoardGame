package TestInfectionCardAction;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import Card.InfectionCardAction;
import Initialize.Board;
import Initialize.City;

public class TestInfectionCardAaction {
	Board board;
	InfectionCardAction infect;
	@Before 
	public void setup(){
		board = new Board();
		City city = new City();
		city.cityName = "Chicago";
		board.cities.put("Chicago", city);
		infect = new InfectionCardAction(board);
	}
	
	@Test
	public void testInfectCityNormal() {
		infect.infectCity("Chicago", "Red");
		City city = board.cities.get("Chicago");
		int numOfRedCubes = city.diseaseCubes.get("Red");
		assertTrue(1 == numOfRedCubes);
	}
}
