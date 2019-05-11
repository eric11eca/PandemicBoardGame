package testInfectionCardAction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import cardActions.InfectionCardAction;
import data.Board;
import data.City;

public class TestInfectionCardAction {
	Board board;
	InfectionCardAction infect;
	String cityName;
	String diseaseColor;

	@Before
	public void setup() {
		cityName = "Chicago";
		diseaseColor = "RED";
		board = new Board();
		City city = new City();
		city.cityName = cityName;
		board.cities.put(cityName, city);
		city.color = diseaseColor;
		board.remainDiseaseCube.put(diseaseColor, 24);
		infect = new InfectionCardAction(board);
	}

	@Test
	public void testRegularDrawInfactionCard() {
		board.validInfectionCards.add(cityName);
		infect.drawOneInfectionCard();
		City infectedCity = board.cities.get(cityName);
		Map<String, Integer> diseaseCubes = infectedCity.diseaseCubes;
		int redCube = diseaseCubes.get(this.diseaseColor);
		assertEquals(1, redCube);
	}

	@Test(expected = RuntimeException.class)
	public void testDrawEmptyInfactionCardPile() {
		infect.drawOneInfectionCard();
	}

	@Test
	public void testInfectCityWithNewDisease() {
		infect.infectCity(cityName, diseaseColor);
		City city = board.cities.get(cityName);
		int numOfRedCubes = city.diseaseCubes.get(diseaseColor);
		assertEquals(1, numOfRedCubes);
		int numOfRemainRedCubes = board.remainDiseaseCube.get(diseaseColor);
		assertEquals(23, numOfRemainRedCubes);
	}

	@Test
	public void testInfectCityWithEixstingDisease() {
		City city_old = board.cities.get(cityName);
		city_old.diseaseCubes.put(diseaseColor, 2);
		board.cities.put(cityName, city_old);
		infect.infectCity(cityName, diseaseColor);
		City city_new = board.cities.get(cityName);
		int numOfRedCubes = city_new.diseaseCubes.get(diseaseColor);
		assertEquals(3, numOfRedCubes);
		int numOfRemainRedCubes = board.remainDiseaseCube.get(diseaseColor);
		assertEquals(21, numOfRemainRedCubes);
		assertFalse(city_new.isInOutbreak);
	}

	@Test
	public void testInfectCityOnQueitNight() {
		board.inQueitNight = true;
		City city = board.cities.get(cityName);
		city.diseaseCubes.put(diseaseColor, 1);
		infect.infectCity(cityName, diseaseColor);
		int numOfRedCubes = city.diseaseCubes.get(diseaseColor);
		assertEquals(1, numOfRedCubes);
		assertFalse(board.inQueitNight);
	}
	
	@Test
	public void testInfectCitywithOutbreak() {
		City city = board.cities.get(cityName);
		city.diseaseCubes.put(diseaseColor, 3);
		infect.infectCity(cityName, diseaseColor);
		int numOfRedCubes = city.diseaseCubes.get(diseaseColor);
		assertEquals(3, numOfRedCubes);
		int numOfRemainRedCubes = board.remainDiseaseCube.get(diseaseColor);
		assertEquals(24, numOfRemainRedCubes);
		assertTrue(city.isInOutbreak);
	}
	
	@Test public void testInfectWhenQuarantineSpecialistExist() {
		City city = board.cities.get(cityName);
		city.currentRoles.add(Board.Roles.QUARANTINESPECIALIST);
		city.diseaseCubes.put(diseaseColor, 1);
		infect.infectCity(cityName, diseaseColor);
		int numOfRedCubes = city.diseaseCubes.get(diseaseColor);
		assertEquals(1, numOfRedCubes);
	}
	
	@Test public void testInfectWhenQuarantineSpecialistExistInNeighbor() {
		City city = board.cities.get(cityName);
		City city1 = new City();
		city1.cityName = "Chicago";
		city1.currentRoles.add(Board.Roles.QUARANTINESPECIALIST);
		board.cities.put(cityName, city1);
		city.neighbors.put(cityName, city1);
		city.diseaseCubes.put(diseaseColor, 1);
		infect.infectCity(cityName, diseaseColor);
		int numOfRedCubes = city.diseaseCubes.get(diseaseColor);
		assertEquals(1, numOfRedCubes);
		assertFalse(board.inQueitNight);
	}
}
