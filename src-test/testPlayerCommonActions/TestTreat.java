package testPlayerCommonActions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import data.Board;
<<<<<<< HEAD
import data.GameColor;
import game.Game;
import game.city.City;
import game.city.CubeData;
import helpers.TestAccess;
import helpers.TestCityFactory;
=======
import data.CityData;
import game.City;
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
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
	PlayerData medicData,  dispatcherData;
	Player medic, dispatcher;
	City city;
	CubeData cityDisease;

<<<<<<< HEAD
	TestCityFactory cityFactory = new TestCityFactory();
	TestAccess access = new TestAccess();

=======
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
	@Before
	public void setup() {
		board = new Board();
		medicData = new PlayerData();
		medicAction = new MedicAction(board, medicData);
		medicData.treatAction = new TreatMedic();
		dispatcherData = new PlayerData();
		dispatcherAction = new DispatcherAction(board);
		CityData data = new CityData("New York", null, 0);
		city = new City(data, 0 , 0);
		medicData.location = city;
		dispatcherData.location = city;
		dispatcherData.treatAction = new TreatNormal(board);
		medic = new Player(board, medicData);
<<<<<<< HEAD
		dispatcher = new Player(board, dispatcherData);
		cityDisease = access.getCityDisease(city);
		access.resetGame();
=======
		dispatcher = new Player(board,dispatcherData);
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
	}

	@Test
	public void testDispatcherTreat() {
<<<<<<< HEAD
		cityDisease.setDiseaseCubeCount(GameColor.BLUE, 2);
		access.getGameCubeData().setDiseaseCubeCount(GameColor.BLUE, 10);
		dispatcher.diseaseTobeTreated = GameColor.BLUE.compatibility_ColorString;
		dispatcher.getPlayerAction(Board.ActionName.TREATDISEASE).executeAction();
		int numOfBlueCubes = cityDisease.getDiseaseCubeCount(GameColor.BLUE);
		int numOfRemainCubes = access.getGameCubeData().getDiseaseCubeCount(GameColor.BLUE);
=======
		city.diseaseCubes.put(blue, 2);
		board.remainDiseaseCube.put(blue, 10);
		dispatcher.treat(blue);
		int numOfBlueCubes = city.diseaseCubes.get(blue);
		int numOfRemainCubes = board.remainDiseaseCube.get(blue);
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
		assertEquals(1, numOfBlueCubes);
		assertEquals(11, numOfRemainCubes);
		assertEquals(3, dispatcherData.action);
	}
	
	@Test
	public void testMedicTreatWithNoCure() {
<<<<<<< HEAD
		cityDisease.setDiseaseCubeCount(GameColor.BLUE, 2);
		access.getGameCubeData().setDiseaseCubeCount(GameColor.BLUE, 22);
		// board.remainDiseaseCube.put(GameColor.BLUE.compatibility_ColorString, 22);
		medic.diseaseTobeTreated = GameColor.BLUE.compatibility_ColorString;
		medic.getPlayerAction(Board.ActionName.TREATDISEASE).executeAction();
		int numOfBlueCubes = cityDisease.getDiseaseCubeCount(GameColor.BLUE);
		int numOfRemainCubes = access.getGameCubeData().getDiseaseCubeCount(GameColor.BLUE);
=======
		city.diseaseCubes.put(blue, 2);
		board.remainDiseaseCube.put(blue, 22);
		medic.treat(blue);
		int numOfBlueCubes = city.diseaseCubes.get(blue);
		int numOfRemainCubes = board.remainDiseaseCube.get(blue);
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
		assertEquals(0, numOfBlueCubes);
		assertEquals(24, numOfRemainCubes);
		assertTrue(Game.getInstance().isDiseaseEradicated(GameColor.BLUE));// board.eradicatedColor.contains(GameColor.BLUE.compatibility_ColorString));
		assertEquals(3, medicData.action);
	}
	
	@Test
	public void testMedicTreatWithCure() {
<<<<<<< HEAD
		cityDisease.setDiseaseCubeCount(GameColor.BLUE, 2);
		access.getGameCubeData().setDiseaseCubeCount(GameColor.BLUE, 22);
		medic.diseaseTobeTreated = GameColor.BLUE.compatibility_ColorString;
		medic.getPlayerAction(Board.ActionName.TREATDISEASE).executeAction();
		int numOfBlueCubes = cityDisease.getDiseaseCubeCount(GameColor.BLUE);
		int numOfRemainCubes = access.getGameCubeData().getDiseaseCubeCount(GameColor.BLUE);
		board.curedDiseases.add(GameColor.BLUE.compatibility_ColorString);
=======
		city.diseaseCubes.put(blue, 2);
		board.remainDiseaseCube.put(blue, 22);
		medic.treat(blue);
		int numOfBlueCubes = city.diseaseCubes.get(blue);
		int numOfRemainCubes = board.remainDiseaseCube.get(blue);
		board.curedDiseases.add(blue);
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
		assertEquals(0, numOfBlueCubes);
		assertEquals(24, numOfRemainCubes);
		assertTrue(Game.getInstance().isDiseaseEradicated(GameColor.BLUE));// board.eradicatedColor.contains(GameColor.BLUE.compatibility_ColorString));
		assertEquals(3, medicData.action);
	}
	
	@Test
	public void testTreatWithSameColorCure() {
<<<<<<< HEAD
		cityDisease.setDiseaseCubeCount(GameColor.BLUE, 3);
		access.getGameCubeData().setDiseaseCubeCount(GameColor.BLUE, 9);
		board.curedDiseases.add(GameColor.BLUE.compatibility_ColorString);
		dispatcher.diseaseTobeTreated = GameColor.BLUE.compatibility_ColorString;
		dispatcher.getPlayerAction(Board.ActionName.TREATDISEASE).executeAction();
		int numOfBlueCubes = cityDisease.getDiseaseCubeCount(GameColor.BLUE);
		int numOfRemainCubes = access.getGameCubeData().getDiseaseCubeCount(GameColor.BLUE);
=======
		city.diseaseCubes.put(blue, 3);
		board.remainDiseaseCube.put(blue, 9);
		board.curedDiseases.add(blue);
		dispatcher.treat(blue);
		int numOfBlueCubes = city.diseaseCubes.get(blue);
		int numOfRemainCubes = board.remainDiseaseCube.get(blue);
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
		assertEquals(0, numOfBlueCubes);
		assertEquals(12, numOfRemainCubes);
	}

	@Test
	public void testTreateWithDifferentColorCure() {
<<<<<<< HEAD
		cityDisease.setDiseaseCubeCount(GameColor.BLUE, 3);
		access.getGameCubeData().setDiseaseCubeCount(GameColor.BLUE, 9);
		board.curedDiseases.add(GameColor.YELLOW.compatibility_ColorString);
		dispatcher.diseaseTobeTreated = GameColor.BLUE.compatibility_ColorString;
		dispatcher.getPlayerAction(Board.ActionName.TREATDISEASE).executeAction();
		int numOfBlueCubes = cityDisease.getDiseaseCubeCount(GameColor.BLUE);
		int numOfRemainCubes = access.getGameCubeData().getDiseaseCubeCount(GameColor.BLUE);
=======
		city.diseaseCubes.put(blue, 3);
		board.remainDiseaseCube.put(blue, 9);
		board.curedDiseases.add(yellow);
		dispatcher.treat(blue);
		int numOfBlueCubes = city.diseaseCubes.get(blue);
		int numOfRemainCubes = board.remainDiseaseCube.get(blue);
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
		assertEquals(2, numOfBlueCubes);
		assertEquals(10, numOfRemainCubes);
	}
}
