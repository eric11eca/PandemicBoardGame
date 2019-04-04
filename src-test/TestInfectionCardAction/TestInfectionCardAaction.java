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
	String cityName;
	String diseaseColor;
	@Before 
	public void setup(){
		cityName = "Chicago";
		diseaseColor = "Red";
		board = new Board();
		City city = new City();
		city.cityName = cityName;
		board.cities.put(cityName, city);
		infect = new InfectionCardAction(board);
	}
	
	@Test
	public void testInfectCityWithNewDisease() {
		infect.infectCity(cityName, diseaseColor);
		City city = board.cities.get(cityName);
		int numOfRedCubes = city.diseaseCubes.get(diseaseColor);
		assertTrue(1 == numOfRedCubes);
	}
	
	@Test
	public void testInfectCityWithEixstingDisease() {
		City city_old = board.cities.get(cityName);
		city_old.diseaseCubes.put(diseaseColor, 1);
		board.cities.put(cityName, city_old);
		
		infect.infectCity(cityName, diseaseColor);
		City city_new = board.cities.get(cityName);
		int numOfRedCubes = city_new.diseaseCubes.get(diseaseColor);
		assertTrue(2 == numOfRedCubes);
	}
	
	@Test
	public void testInfectCityOnQueitNight() {
		board.inQueitNight = true;
		City city_old = board.cities.get(cityName);
		city_old.diseaseCubes.put(diseaseColor, 1); 
		infect.infectCity(cityName, diseaseColor);
		City city_new = board.cities.get(cityName);
		int numOfRedCubes = city_new.diseaseCubes.get(diseaseColor);
		assertTrue(1 == numOfRedCubes);
	}
	
}
