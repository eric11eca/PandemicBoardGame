//package testPlayerCardAction;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import cardActions.EpidemicCardAction;
//import data.Board;
//import data.GameColor;
//import game.Game;
//import game.city.City;
//import game.disease.CubeData;
//import test.util.TestAccess;
//import test.util.TestCityBuilder;
//
//public class TestEpidemicCardAction {
//	Board board;
//	EpidemicCardAction epidemicCardAction;
//	TestCityBuilder cityFactory = new TestCityBuilder();
//	TestAccess access = new TestAccess();
//
//	@Before
//	public void setup() {
//		board = new Board();
//
//		board.infectionRateTracker.push(4);
//		board.infectionRateTracker.push(4);
//		board.infectionRateTracker.push(3);
//		board.infectionRateTracker.push(3);
//		board.infectionRateTracker.push(2);
//		board.infectionRateTracker.push(2);
//		board.infectionRateTracker.push(2);
//
//		City city1 = cityFactory.makeFakeCity("cityA", GameColor.YELLOW);
//		board.cities.put(city1.getName(), city1);
//		City city2 = cityFactory.makeFakeCity("cityB", GameColor.RED);
//
//		board.cities.put(city2.getName(), city2);
//		// board.eradicatedColor.add(city1.getColor().compatibility_ColorString);
//		epidemicCardAction = new EpidemicCardAction(board);
//		// board.remainDiseaseCube.put("RED", 24);
//		access.resetGame();
//		access.getGameCubeData().setDiseaseCubeCount(GameColor.YELLOW, 24);
//		access.getGameCubeData().setDiseaseCubeCount(GameColor.RED, 24);
//	}
//
//	@Test
//	public void testInfectionRateIncrease() {
//		epidemicCardAction.increaseInfectionRate();
//		assertTrue(2 == board.infectionRateTracker.peek());
//	}
//
//	@Test
//	public void testInfectionRateIncreaseWhenIsMax() {
//		for (int i = 0; i < 6; i++) {
//			epidemicCardAction.increaseInfectionRate();
//		}
//		assertTrue(4 == board.infectionRateTracker.peek());
//		epidemicCardAction.increaseInfectionRate();
//		assertTrue(4 == board.infectionRateTracker.peek());
//	}
//
//	@Test
//	public void testInfectionOfEradicatedDisease() {
//		board.validInfectionCards.add("cityA");
//		epidemicCardAction.epidemicInfect();
//		City city = board.cities.get("cityA");
//		assertEquals(0, access.getCityDisease(city).getDiseaseCubeCount(GameColor.YELLOW));
//	}
//
//	@Test
//	public void testInfectionOfNewDisease() {
//		access.getGameCubeData().setDiseaseCubeCount(GameColor.RED, 23);
//		board.validInfectionCards.add("cityB");
//		epidemicCardAction.epidemicInfect();
//		City city = board.cities.get("cityB");
//		assertEquals(3, access.getCityDisease(city).getDiseaseCubeCount(GameColor.RED));
//		assertEquals(20, access.getGameCubeData().getDiseaseCubeCount(GameColor.RED));
//		assertTrue(board.validInfectionCards.isEmpty());
//		assertFalse(board.discardInfectionCards.isEmpty());
//	}
//
//	@Test
//	public void testInfectionCausingOutbreak() {
//		board.validInfectionCards.add("cityB");
//		City cityB = board.cities.get("cityB");
//		CubeData cityBDisease = access.getCityDisease(cityB);
//		cityBDisease.setDiseaseCubeCount(cityB.getColor(), 2);
//		access.getGameCubeData().setDiseaseCubeCount(cityB.getColor(), 22);
//		// board.remainDiseaseCube.put(cityB.getColor().compatibility_ColorString, 22);
//		epidemicCardAction.epidemicInfect();
//		// assertTrue(cityB.isInOutbreak);
//		assertEquals(3, cityBDisease.getDiseaseCubeCount(GameColor.RED));
//		assertEquals(21, access.getGameCubeData().getDiseaseCubeCount(GameColor.RED));
//		assertTrue(board.validInfectionCards.isEmpty());
//		assertFalse(board.discardInfectionCards.isEmpty());
//	}
//
//	@Test
//	public void testReshuffleDiscardInfectionCardDeck() {
//		board.discardInfectionCards.add("Chicaco");
//		board.discardInfectionCards.add("NewYork");
//		board.discardInfectionCards.add("London");
//		board.discardInfectionCards.add("Atlanta");
//		String oldDiscardInfectionCards, newDiscardInfectionCards;
//		boolean reshuffled;
//		do {
//			oldDiscardInfectionCards = board.discardInfectionCards.toString();
//			reshuffled = epidemicCardAction.reshuffleDiscardInfectionDeck();
//			newDiscardInfectionCards = board.discardInfectionCards.toString();
//		} while (oldDiscardInfectionCards.equals(newDiscardInfectionCards));
//
//		assertFalse(oldDiscardInfectionCards.equals(newDiscardInfectionCards));
//		assertTrue(reshuffled);
//	}
//
//	@Test
//	public void testNewInfectionCardDeck() {
//		board.validInfectionCards.add("Paris");
//		board.discardInfectionCards.add("Chicaco");
//		board.discardInfectionCards.add("NewYork");
//		board.discardInfectionCards.add("London");
//		board.discardInfectionCards.add("Atlanta");
//		epidemicCardAction.makingNewInfectionCardDeck();
//		int newSize = board.validInfectionCards.size();
//		assertEquals(5, newSize);
//		assertTrue(board.discardInfectionCards.isEmpty());
//	}
//
//	@Test
//	public void testPerformeEpidemic() {
//		access.getGameCubeData().setDiseaseCubeCount(GameColor.RED, 23);
//		board.infectionRateTracker.pop();
//		board.infectionRateTracker.pop();
//		board.discardInfectionCards.add("Chicaco");
//		board.discardInfectionCards.add("NewYork");
//		board.discardInfectionCards.add("London");
//		board.discardInfectionCards.add("Atlanta");
//		board.validInfectionCards.add("cityB");
//
//		epidemicCardAction.performEpidemic();
//		assertEquals(3, board.infectionRateTracker.peek().intValue());
//
//		City city = board.cities.get("cityB");
//		assertEquals(3, access.getCityDisease(city).getDiseaseCubeCount(GameColor.RED));
//
//		int newSize = board.validInfectionCards.size();
//		assertEquals(5, newSize);
//		assertTrue(board.discardInfectionCards.isEmpty());
//	}
//
//	@Test
//	public void testEndGameDuringInfection() {
//		board.validInfectionCards.add("cityB");
//		access.getGameCubeData().setDiseaseCubeCount(GameColor.RED, 0);
//		// board.remainDiseaseCube.put("RED", 0);
//		epidemicCardAction.epidemicInfect();
//		assertTrue(Game.getInstance().isLost());
//	}
//
//	@Test
//	public void testEndGameWhenPerformeEpidemic() {
//		access.getGameCubeData().setDiseaseCubeCount(GameColor.RED, 0);
//		board.validInfectionCards.add("cityB");
//		epidemicCardAction.performEpidemic();
//		assertTrue(Game.getInstance().isLost());
//	}
//}
