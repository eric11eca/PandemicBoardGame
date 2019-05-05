package TestPlayers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import Initialize.Board;
import Initialize.City;
import Player.MedicAction;
import Player.Player;
import Player.PlayerData;

public class TestMedic {
	MedicAction medicAction;
	Board board;
	PlayerData medic;
	Player player;

	@Before
	public void setup() {
		board = new Board();
		medic = new PlayerData();
		medic.role = Board.Roles.MEDIC;
		medicAction = new MedicAction(board, medic);
		City city = new City();
		city.cityName = "a";
		medic.action = 4;
		medic.location = city;
	}

	@Test
	public void testRemoveAllUncuredCubes() {
		medic.location.diseaseCubes.put("YELLOW", 2);
		medic.location.diseaseCubes.put("BLUE", 1);
		assertFalse(medic.location.diseaseCubes.isEmpty());
		medicAction.removeAllCubes();
		int numOfYellowCube = medic.location.diseaseCubes.get("YELLOW");
		int numOfBlueCube = medic.location.diseaseCubes.get("BLUE");
		assertEquals(2, numOfYellowCube);
		assertEquals(1, numOfBlueCube);
		assertEquals(4, medic.action);
	}

	@Test
	public void testRemoveAllCuredCubes() {
		medic.location.diseaseCubes.put("YELLOW", 2);
		medic.location.diseaseCubes.put("BLUE", 1);
		board.curedDiseases.add("YELLOW");
		board.curedDiseases.add("BLUE");
		board.remainDiseaseCube.put("YELLOW", 10);
		board.remainDiseaseCube.put("BLUE", 11);
		medicAction.removeAllCubes();
		int locationYellowCube = medic.location.diseaseCubes.get("YELLOW");
		int locationBlueCube = medic.location.diseaseCubes.get("BLUE");
		assertEquals(0, locationYellowCube);
		assertEquals(0, locationBlueCube);
		int remainBlueCube = board.remainDiseaseCube.get("BLUE");
		int remainYellowCube = board.remainDiseaseCube.get("YELLOW");
		assertEquals(12, remainBlueCube);
		assertEquals(12, remainYellowCube);
		assertEquals(4, medic.action);
	}
}
