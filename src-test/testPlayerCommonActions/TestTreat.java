package testPlayerCommonActions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import data.Board;
import data.CityOLD;
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
	CityOLD city;
	String blue = "BLUE";
	String yellow = "YELLOW";

	TestCityFactory cityFactory = new TestCityFactory();

	@Before
	public void setup() {
		board = new Board();
		medicData = new PlayerData();
		medicAction = new MedicAction(board, medicData);
		medicData.treatAction = new TreatMedic(medicData, board);
		dispatcherData = new PlayerData();
		dispatcherAction = new DispatcherAction(board);
		city = cityFactory.makeFakeCity();
		medicData.location = city;
		dispatcherData.location = city;
		dispatcherData.treatAction = new TreatNormal(dispatcherData, board);
		medic = new Player(board, medicData);
		dispatcher = new Player(board, dispatcherData);
	}

	@Test
	public void testDispatcherTreat() {
		city.diseaseCubes.put(blue, 2);
		board.remainDiseaseCube.put(blue, 10);
		dispatcher.diseaseTobeTreated = blue;
		dispatcher.getPlayerAction(Board.ActionName.TREATDISEASE).executeAction();
		int numOfBlueCubes = city.diseaseCubes.get(blue);
		int numOfRemainCubes = board.remainDiseaseCube.get(blue);
		assertEquals(1, numOfBlueCubes);
		assertEquals(11, numOfRemainCubes);
		assertEquals(3, dispatcherData.action);
	}

	@Test
	public void testMedicTreatWithNoCure() {
		city.diseaseCubes.put(blue, 2);
		board.remainDiseaseCube.put(blue, 22);
		medic.diseaseTobeTreated = blue;
		medic.getPlayerAction(Board.ActionName.TREATDISEASE).executeAction();
		int numOfBlueCubes = city.diseaseCubes.get(blue);
		int numOfRemainCubes = board.remainDiseaseCube.get(blue);
		assertEquals(0, numOfBlueCubes);
		assertEquals(24, numOfRemainCubes);
		assertTrue(board.eradicatedColor.contains(blue));
		assertEquals(3, medicData.action);
	}

	@Test
	public void testMedicTreatWithCure() {
		city.diseaseCubes.put(blue, 2);
		board.remainDiseaseCube.put(blue, 22);
		medic.diseaseTobeTreated = blue;
		medic.getPlayerAction(Board.ActionName.TREATDISEASE).executeAction();
		int numOfBlueCubes = city.diseaseCubes.get(blue);
		int numOfRemainCubes = board.remainDiseaseCube.get(blue);
		board.curedDiseases.add(blue);
		assertEquals(0, numOfBlueCubes);
		assertEquals(24, numOfRemainCubes);
		assertTrue(board.eradicatedColor.contains(blue));
		assertEquals(3, medicData.action);
	}

	@Test
	public void testTreatWithSameColorCure() {
		city.diseaseCubes.put(blue, 3);
		board.remainDiseaseCube.put(blue, 9);
		board.curedDiseases.add(blue);
		dispatcher.diseaseTobeTreated = blue;
		dispatcher.getPlayerAction(Board.ActionName.TREATDISEASE).executeAction();
		int numOfBlueCubes = city.diseaseCubes.get(blue);
		int numOfRemainCubes = board.remainDiseaseCube.get(blue);
		assertEquals(0, numOfBlueCubes);
		assertEquals(12, numOfRemainCubes);
	}

	@Test
	public void testTreateWithDifferentColorCure() {
		city.diseaseCubes.put(blue, 3);
		board.remainDiseaseCube.put(blue, 9);
		board.curedDiseases.add(yellow);
		dispatcher.diseaseTobeTreated = blue;
		dispatcher.getPlayerAction(Board.ActionName.TREATDISEASE).executeAction();
		int numOfBlueCubes = city.diseaseCubes.get(blue);
		int numOfRemainCubes = board.remainDiseaseCube.get(blue);
		assertEquals(2, numOfBlueCubes);
		assertEquals(10, numOfRemainCubes);
	}
}
