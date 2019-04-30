package TestInfectionCardAction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import Card.InfectionCardAction;
import Initialize.Board;
import Initialize.City;

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
		board.validInfectionCard.add(cityName);
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
		city_old.diseaseCubes.put(diseaseColor, 1);
		board.cities.put(cityName, city_old);
		infect.infectCity(cityName, diseaseColor);
		City city_new = board.cities.get(cityName);
		int numOfRedCubes = city_new.diseaseCubes.get(diseaseColor);
		assertEquals(2, numOfRedCubes);
		int numOfRemainRedCubes = board.remainDiseaseCube.get(diseaseColor);
		assertEquals(22, numOfRemainRedCubes);
	}

	@Test
	public void testInfectCityOnQueitNight() {
		board.inQueitNight = true;
		City city_old = board.cities.get(cityName);
		city_old.diseaseCubes.put(diseaseColor, 1);
		infect.infectCity(cityName, diseaseColor);
		City city_new = board.cities.get(cityName);
		int numOfRedCubes = city_new.diseaseCubes.get(diseaseColor);
		assertEquals(1, numOfRedCubes);
		assertFalse(board.inQueitNight);
	}

}
