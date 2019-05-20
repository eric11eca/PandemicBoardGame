package testPlayers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import data.Board;
import data.City;
import player.PlayerData;
import playerAction.MedicAction;

public class TestMedic {
	MedicAction medicAction;
	Board board;
	PlayerData medic;

	@Before
	public void setup() {
		board = new Board();
		medic = new PlayerData();
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
		assertEquals(2, board.eradicatedColor.size());
	}
	
	@Test
	public void testSpecialSkillCalls() {
		medicAction = EasyMock.partialMockBuilder(MedicAction.class)
				.withConstructor(board, medic)
				.addMockedMethod("removeAllCubes")
				.createMock();
		
		medicAction.removeAllCubes();
		EasyMock.replay(medicAction);
		medicAction.useSpecialSkill();
		EasyMock.verify(medicAction);
	}
}
