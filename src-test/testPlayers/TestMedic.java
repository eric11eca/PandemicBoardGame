package testPlayers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import SpeciaoPlayerAction.MedicState;
import data.Board;
import data.City;
import player.PlayerData;

public class TestMedic {
	MedicState medicState;
	Board board;
	PlayerData medic;

	@Before
	public void setup() {
		Board.setNull();
		board = Board.getInstance();
		medic = new PlayerData();
		medicState = new MedicState(board, medic);
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
		medicState.removeAllCubes();
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
		medicState.removeAllCubes();
		int locationYellowCube = medic.location.diseaseCubes.get("YELLOW");
		int locationBlueCube = medic.location.diseaseCubes.get("BLUE");
		assertEquals(0, locationYellowCube);
		assertEquals(0, locationBlueCube);
		int remainBlueCube = board.remainDiseaseCube.get("BLUE");
		int remainYellowCube = board.remainDiseaseCube.get("YELLOW");
		assertEquals(12, remainBlueCube);
		assertEquals(12, remainYellowCube);
		assertEquals(4, medic.action);
		assertEquals(2, board.eradicatedColor.size());
	}
	
	@Test
	public void testSpecialSkillCalls() {
		medicState = EasyMock.partialMockBuilder(MedicState.class)
				.withConstructor(board, medic)
				.addMockedMethod("removeAllCubes")
				.createMock();
		
		medicState.removeAllCubes();
		EasyMock.replay(medicState);
		medicState.useSpecialSkill();
		EasyMock.verify(medicState);
	}
}
