package testPlayerCommonActions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import data.Board;
import data.GameColor;
import game.Game;
import game.city.City;
import game.city.CubeData;
import helpers.TestAccess;
import helpers.TestCityFactory;
import player.Player;
import player.PlayerData;
import player.TreatMedic;
import player.TreatNormal;
import playerAction.DispatcherAction;
import playerAction.MedicAction;

public class TestTreat {
	Board board;
	MedicAction medicAction;
	DispatcherAction dispatcherAction;
	PlayerData medicData, dispatcherData;
	Player medic, dispatcher;
	City city;
	CubeData cityDisease;

	TestCityFactory cityFactory = new TestCityFactory();
	TestAccess access = new TestAccess();

	@Before
	public void setup() {
		board = new Board();
		medicData = new PlayerData();
		medicAction = new MedicAction(board, medicData);
		medicData.treatAction = new TreatMedic();
		dispatcherData = new PlayerData();
		dispatcherAction = new DispatcherAction(board);
		city = cityFactory.makeFakeCity("NewYork");
		medicData.location = city;
		dispatcherData.location = city;
		dispatcherData.treatAction = new TreatNormal(board);
		medic = new Player(board, medicData);

		dispatcher = new Player(board, dispatcherData);
		cityDisease = access.getCityDisease(city);
		access.resetGame();
	}

	@Test
	public void testDispatcherTreat() {
		cityDisease.setDiseaseCubeCount(GameColor.BLUE, 2);
		access.getGameCubeData().setDiseaseCubeCount(GameColor.BLUE, 10);
		dispatcher.treat(GameColor.BLUE);
		int numOfBlueCubes = cityDisease.getDiseaseCubeCount(GameColor.BLUE);
		int numOfRemainCubes = access.getGameCubeData().getDiseaseCubeCount(GameColor.BLUE);

		assertEquals(1, numOfBlueCubes);
		assertEquals(11, numOfRemainCubes);
		assertEquals(3, dispatcherData.action);
	}

	@Test
	public void testMedicTreatWithNoCure() {

		cityDisease.setDiseaseCubeCount(GameColor.BLUE, 2);
		access.getGameCubeData().setDiseaseCubeCount(GameColor.BLUE, 22);
		medic.treat(GameColor.BLUE);
		int numOfBlueCubes = cityDisease.getDiseaseCubeCount(GameColor.BLUE);
		int numOfRemainCubes = access.getGameCubeData().getDiseaseCubeCount(GameColor.BLUE);

		assertEquals(0, numOfBlueCubes);
		assertEquals(24, numOfRemainCubes);
		assertTrue(Game.getInstance().isDiseaseEradicated(GameColor.BLUE));// board.eradicatedColor.contains(GameColor.BLUE.compatibility_ColorString));
		assertEquals(3, medicData.action);
	}

	@Test
	public void testMedicTreatWithCure() {

		cityDisease.setDiseaseCubeCount(GameColor.BLUE, 2);
		access.getGameCubeData().setDiseaseCubeCount(GameColor.BLUE, 22);

		medic.treat(GameColor.BLUE);
		int numOfBlueCubes = cityDisease.getDiseaseCubeCount(GameColor.BLUE);
		int numOfRemainCubes = access.getGameCubeData().getDiseaseCubeCount(GameColor.BLUE);
		board.curedDiseases.add(GameColor.BLUE.compatibility_ColorString);

		assertEquals(0, numOfBlueCubes);
		assertEquals(24, numOfRemainCubes);
		assertTrue(Game.getInstance().isDiseaseEradicated(GameColor.BLUE));// board.eradicatedColor.contains(GameColor.BLUE.compatibility_ColorString));
		assertEquals(3, medicData.action);
	}

	@Test
	public void testTreatWithSameColorCure() {

		cityDisease.setDiseaseCubeCount(GameColor.BLUE, 3);
		access.getGameCubeData().setDiseaseCubeCount(GameColor.BLUE, 9);
		board.curedDiseases.add(GameColor.BLUE.compatibility_ColorString);
		dispatcher.treat(GameColor.BLUE);
		int numOfBlueCubes = cityDisease.getDiseaseCubeCount(GameColor.BLUE);
		int numOfRemainCubes = access.getGameCubeData().getDiseaseCubeCount(GameColor.BLUE);

		assertEquals(0, numOfBlueCubes);
		assertEquals(12, numOfRemainCubes);
	}

	@Test
	public void testTreateWithDifferentColorCure() {

		cityDisease.setDiseaseCubeCount(GameColor.BLUE, 3);
		access.getGameCubeData().setDiseaseCubeCount(GameColor.BLUE, 9);
		board.curedDiseases.add(GameColor.YELLOW.compatibility_ColorString);

		dispatcher.treat(GameColor.BLUE);
		int numOfBlueCubes = cityDisease.getDiseaseCubeCount(GameColor.BLUE);
		int numOfRemainCubes = access.getGameCubeData().getDiseaseCubeCount(GameColor.BLUE);

		assertEquals(2, numOfBlueCubes);
		assertEquals(10, numOfRemainCubes);
	}
}
