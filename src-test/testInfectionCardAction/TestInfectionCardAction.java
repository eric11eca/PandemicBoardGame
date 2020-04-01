package testInfectionCardAction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import cardActions.InfectionCardAction;
import data.Board;
import data.CityData;
import data.CityOLD;
import data.GameColor;

public class TestInfectionCardAction {
	Board board;
	InfectionCardAction infect;
	String cityName;
	GameColor diseaseColor;

	@Before
	public void setup() {
		cityName = "Chicago";
		diseaseColor = GameColor.RED;
		board = new Board();
		CityOLD city = new CityOLD(new CityData(cityName, diseaseColor, 10), 0, 0);
		board.cities.put(cityName, city);
		board.remainDiseaseCube.put(diseaseColor.compatibility_ColorString, 24);
		infect = new InfectionCardAction(board);
	}

	@Test
	public void testRegularDrawInfactionCard() {
		board.validInfectionCards.add(cityName);
		infect.drawOneInfectionCard();
		CityOLD infectedCity = board.cities.get(cityName);
		Map<String, Integer> diseaseCubes = infectedCity.diseaseCubes;
		int redCube = diseaseCubes.get(this.diseaseColor.compatibility_ColorString);
		assertEquals(1, redCube);
	}

	@Test(expected = RuntimeException.class)
	public void testDrawEmptyInfactionCardPile() {
		infect.drawOneInfectionCard();
	}

	@Test
	public void testInfectCityWithNewDisease() {
		infect.infectCity(cityName, diseaseColor.compatibility_ColorString);
		CityOLD city = board.cities.get(cityName);
		int numOfRedCubes = city.diseaseCubes.get(diseaseColor.compatibility_ColorString);
		assertEquals(1, numOfRedCubes);
		int numOfRemainRedCubes = board.remainDiseaseCube.get(diseaseColor.compatibility_ColorString);
		assertEquals(23, numOfRemainRedCubes);
	}

	@Test
	public void testInfectCityWithEixstingDisease() {
		CityOLD city_old = board.cities.get(cityName);
		city_old.diseaseCubes.put(diseaseColor.compatibility_ColorString, 2);
		board.cities.put(cityName, city_old);
		infect.infectCity(cityName, diseaseColor.compatibility_ColorString);
		CityOLD city_new = board.cities.get(cityName);
		int numOfRedCubes = city_new.diseaseCubes.get(diseaseColor.compatibility_ColorString);
		assertEquals(3, numOfRedCubes);
		int numOfRemainRedCubes = board.remainDiseaseCube.get(diseaseColor.compatibility_ColorString);
		assertEquals(21, numOfRemainRedCubes);
		assertFalse(city_new.isInOutbreak);
	}

	@Test
	public void testInfectCitywithOutbreak() {
		CityOLD city = board.cities.get(cityName);
		city.diseaseCubes.put(diseaseColor.compatibility_ColorString, 3);
		infect.infectCity(cityName, diseaseColor.compatibility_ColorString);
		int numOfRedCubes = city.diseaseCubes.get(diseaseColor.compatibility_ColorString);
		assertEquals(3, numOfRedCubes);
		int numOfRemainRedCubes = board.remainDiseaseCube.get(diseaseColor.compatibility_ColorString);
		assertEquals(24, numOfRemainRedCubes);
		assertTrue(city.isInOutbreak);
	}

	@Test
	public void testInfectWhenQuarantineSpecialistExist() {
		CityOLD city = board.cities.get(cityName);
		city.currentRoles.add(Board.Roles.QUARANTINESPECIALIST);
		city.diseaseCubes.put(diseaseColor.compatibility_ColorString, 1);
		infect.infectCity(cityName, diseaseColor.compatibility_ColorString);
		int numOfRedCubes = city.diseaseCubes.get(diseaseColor.compatibility_ColorString);
		assertEquals(1, numOfRedCubes);
	}

	@Test
	public void testInfectWhenQuarantineSpecialistExistInNeighbor() {
		CityOLD city = board.cities.get(cityName);
		CityOLD city1 = new CityOLD(new CityData("NewYork", GameColor.BLACK, 10), 0, 0);

		city1.currentRoles.add(Board.Roles.QUARANTINESPECIALIST);
		board.cities.put("NewYork", city1);
		city.neighbors.add(city1);
		city.diseaseCubes.put(diseaseColor.compatibility_ColorString, 2);
		infect.infectCity(cityName, diseaseColor.compatibility_ColorString);
		int numOfRedCubes = city.diseaseCubes.get(diseaseColor.compatibility_ColorString);
		assertEquals(2, numOfRedCubes);
	}

	@Test
	public void testInfectFailsWhenDiseaseIsEradicated() {
		CityOLD city = board.cities.get(cityName);
		city.diseaseCubes.put(diseaseColor.compatibility_ColorString, 0);
		board.eradicatedColor.add(diseaseColor.compatibility_ColorString);
		infect.infectCity(cityName, diseaseColor.compatibility_ColorString);
		int numOfRedCubes = city.diseaseCubes.get(diseaseColor.compatibility_ColorString);
		assertEquals(0, numOfRedCubes);
	}
}
