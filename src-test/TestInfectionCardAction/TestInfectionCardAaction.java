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
	public void testInfectCityWithNewDisease() {
		infect.infectCity("Chicago", "Red");
		City city = board.cities.get("Chicago");
		int numOfRedCubes = city.diseaseCubes.get("Red");
		assertTrue(1 == numOfRedCubes);
	}
	
	@Test
	public void testInfectCityWithEixstingDisease() {
		City city_old = board.cities.get("Chicago");
		city_old.diseaseCubes.put("Red", 1);
		board.cities.put("Chicago", city_old);
		
		infect.infectCity("Chicago", "Red");
		City city_new = board.cities.get("Chicago");
		int numOfRedCubes = city_new.diseaseCubes.get("Red");
		assertTrue(2 == numOfRedCubes);
	}
	
	@Test
	public void testInfectCityOnQueitNight() {
		City city_old = board.cities.get("Chicago");
		city_old.diseaseCubes.put("Red", 1); 
		infect.infectCity("Chicago", "Red");
	}
	
}
