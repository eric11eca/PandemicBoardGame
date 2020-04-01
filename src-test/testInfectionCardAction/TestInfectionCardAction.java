//package testInfectionCardAction;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import data.Board;
//import data.GameColor;
//import game.cards.InfectionCardAction;
//import game.city.City;
//import game.city.CubeData;
//import helpers.TestAccess;
//import helpers.TestCityFactory;
//
//public class TestInfectionCardAction {
//	Board board;
//	InfectionCardAction infect;
//	String cityName;
//	GameColor diseaseColor;
//
//	TestCityFactory cityFactory = new TestCityFactory();
//	TestAccess access = new TestAccess();
//
//	@Before
//	public void setup() {
//		cityName = "Chicago";
//		diseaseColor = GameColor.RED;
//		board = new Board();
//		City city = cityFactory.makeFakeCity(cityName, diseaseColor);
//		board.cities.put(cityName, city);
//		access.resetGame();
//		access.getGameCubeData().setDiseaseCubeCount(diseaseColor, 24);
//		// board.remainDiseaseCube.put(diseaseColor.compatibility_ColorString, 24);
//		infect = new InfectionCardAction(board);
//	}
//
//	@Test
//	public void testRegularDrawInfactionCard() {
//		board.validInfectionCards.add(cityName);
//		infect.drawOneInfectionCard();
//		City infectedCity = board.cities.get(cityName);
//		assertEquals(1, access.getCityDisease(infectedCity).getDiseaseCubeCount(diseaseColor));
//	}
//
//	@Test(expected = RuntimeException.class)
//	public void testDrawEmptyInfactionCardPile() {
//		infect.drawOneInfectionCard();
//	}
//
//	@Test
//	public void testInfectCityWithNewDisease() {
//		infect.infectCity(cityName, diseaseColor.compatibility_ColorString);
//		City city = board.cities.get(cityName);
//		assertEquals(1, access.getCityDisease(city).getDiseaseCubeCount(diseaseColor));
//		int numOfRemainRedCubes = board.remainDiseaseCube.get(diseaseColor.compatibility_ColorString);
//		assertEquals(23, numOfRemainRedCubes);
//	}
//
//	@Test
//	public void testInfectCityWithExistingDisease() {
//		City city_old = board.cities.get(cityName);
//		access.getCityDisease(city_old).addDiseaseCube(diseaseColor, 2);
//		board.cities.put(cityName, city_old);
//		infect.infectCity(cityName, diseaseColor.compatibility_ColorString);
//		City city_new = board.cities.get(cityName);
//		int numOfRedCubes = access.getCityDisease(city_new).getDiseaseCubeCount(diseaseColor);
//		assertEquals(3, numOfRedCubes);
//		int numOfRemainRedCubes = board.remainDiseaseCube.get(diseaseColor.compatibility_ColorString);
//		assertEquals(21, numOfRemainRedCubes);
//		assertFalse(city_new.isInOutbreak);
//	}
//
//	@Test
//	public void testInfectCitywithOutbreak() {
//		City city = board.cities.get(cityName);
//		CubeData cityDisease = access.getCityDisease(city);
//		cityDisease.addDiseaseCube(diseaseColor, 3);
//		infect.infectCity(cityName, diseaseColor.compatibility_ColorString);
//		int numOfRedCubes = cityDisease.getDiseaseCubeCount(diseaseColor);
//		assertEquals(3, numOfRedCubes);
//		int numOfRemainRedCubes = board.remainDiseaseCube.get(diseaseColor.compatibility_ColorString);
//		assertEquals(24, numOfRemainRedCubes);
//		assertTrue(city.isInOutbreak);
//	}
//
//	@Test
//	public void testInfectWhenQuarantineSpecialistExist() {
//		City city = board.cities.get(cityName);
//		CubeData cityDisease = access.getCityDisease(city);
//		city.currentRoles.add(Board.Roles.QUARANTINESPECIALIST);
//		cityDisease.addDiseaseCube(diseaseColor, 1);
//		infect.infectCity(cityName, diseaseColor.compatibility_ColorString);
//		int numOfRedCubes = cityDisease.getDiseaseCubeCount(diseaseColor);
//		assertEquals(1, numOfRedCubes);
//	}
//
//	@Test
//	public void testInfectWhenQuarantineSpecialistExistInNeighbor() {
//		City city = board.cities.get(cityName);
//		CubeData cityDisease = access.getCityDisease(city);
//		City city1 = cityFactory.makeFakeCity("NewYork");
//
//		city1.currentRoles.add(Board.Roles.QUARANTINESPECIALIST);
//		board.cities.put("NewYork", city1);
//		city.neighbors.add(city1);
//		cityDisease.addDiseaseCube(diseaseColor, 2);
//		infect.infectCity(cityName, diseaseColor.compatibility_ColorString);
//		int numOfRedCubes = cityDisease.getDiseaseCubeCount(diseaseColor);
//		assertEquals(2, numOfRedCubes);
//	}
//
//	@Test
//	public void testInfectFailsWhenDiseaseIsEradicated() {
//		City city = board.cities.get(cityName);
//		CubeData cityDisease = access.getCityDisease(city);
//		cityDisease.removeAllDiseaseCube(diseaseColor);
//		board.eradicatedColor.add(diseaseColor.compatibility_ColorString);
//		infect.infectCity(cityName, diseaseColor.compatibility_ColorString);
//		int numOfRedCubes = cityDisease.getDiseaseCubeCount(diseaseColor);
//		assertEquals(0, numOfRedCubes);
//	}
//}
