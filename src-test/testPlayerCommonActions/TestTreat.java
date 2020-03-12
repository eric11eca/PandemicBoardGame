package testPlayerCommonActions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import PlayerAction.TreatMedic;
import PlayerAction.TreatNormal;
import SpeciaoPlayerAction.DispatcherState;
import SpeciaoPlayerAction.MedicState;
import data.Board;
import data.City;
import player.Player;
import player.PlayerData;

public class TestTreat {
	Board board;
	MedicState medicState;
	DispatcherState dispatcherState;
	PlayerData medicData,  dispatcherData;
	Player medic, dispatcher;
	City city;
	String blue = "BLUE";
	String yellow = "YELLOW";

	@Before
	public void setup() {
		Board.setNull();
		board = Board.getInstance();
		medicData = new PlayerData();
		medicState = new MedicState(board, medicData);
		medicData.treatAction = new TreatMedic(medicData, board);
		dispatcherData = new PlayerData();
		dispatcherState = new DispatcherState(board);
		city = new City();
		medicData.location = city;
		dispatcherData.location = city;
		dispatcherData.treatAction = new TreatNormal(dispatcherData, board);
		medic = new Player(board, medicData);
		dispatcher = new Player(board,dispatcherData);
	}

	@Test
	public void testDispatcherTreat() {
		city.diseaseCubes.put(blue, 2);
		board.remainDiseaseCube.put(blue, 10);
		dispatcher.treat(blue);
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
		medic.treat(blue);
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
		medic.treat(blue);
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
		dispatcher.treat(blue);
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
		dispatcher.treat(blue);
		int numOfBlueCubes = city.diseaseCubes.get(blue);
		int numOfRemainCubes = board.remainDiseaseCube.get(blue);
		assertEquals(2, numOfBlueCubes);
		assertEquals(10, numOfRemainCubes);
	}
}
