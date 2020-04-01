package testPlayers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import data.Board;
import data.GameColor;
import game.Game;
import game.city.City;
import helpers.TestAccess;
import helpers.TestCityFactory;
import player.PlayerData;
import playerAction.MedicAction;

public class TestMedic {
	MedicAction medicAction;
	Board board;
	PlayerData medic;
	TestCityFactory cityFactory = new TestCityFactory();
	TestAccess access = new TestAccess();

	@Before
	public void setup() {
		board = new Board();
		medic = new PlayerData();
		medicAction = new MedicAction(board, medic);
		City city = cityFactory.makeFakeCity();
		medic.action = 4;
		medic.location = city;
		access.resetGame();
	}

	@Test
	public void testRemoveAllUncuredCubes() {
		access.getCityDisease(medic.location).setDiseaseCubeCount(GameColor.YELLOW, 2);
		access.getCityDisease(medic.location).setDiseaseCubeCount(GameColor.BLUE, 1);
		assertFalse(access.getCityDisease(medic.location).getExistingDiseases().isEmpty());
		medicAction.removeAllCubes();
		int numOfYellowCube = access.getCityDisease(medic.location).getDiseaseCubeCount(GameColor.YELLOW);
		int numOfBlueCube = access.getCityDisease(medic.location).getDiseaseCubeCount(GameColor.BLUE);
		assertEquals(2, numOfYellowCube);
		assertEquals(1, numOfBlueCube);
		assertEquals(4, medic.action);
	}

	@Test
	public void testRemoveAllCuredCubes() {
		access.getCityDisease(medic.location).setDiseaseCubeCount(GameColor.YELLOW, 2);
		access.getCityDisease(medic.location).setDiseaseCubeCount(GameColor.BLUE, 1);
		board.curedDiseases.add("YELLOW");
		board.curedDiseases.add("BLUE");
		access.getGameCubeData().setDiseaseCubeCount(GameColor.YELLOW, 10);
		access.getGameCubeData().setDiseaseCubeCount(GameColor.BLUE, 11);
		medicAction.removeAllCubes();
		int locationYellowCube = access.getCityDisease(medic.location).getDiseaseCubeCount(GameColor.YELLOW);
		int locationBlueCube = access.getCityDisease(medic.location).getDiseaseCubeCount(GameColor.BLUE);
		assertEquals(0, locationYellowCube);
		assertEquals(0, locationBlueCube);
		int remainBlueCube = access.getGameCubeData().getDiseaseCubeCount(GameColor.BLUE);
		int remainYellowCube = access.getGameCubeData().getDiseaseCubeCount(GameColor.YELLOW);
		assertEquals(12, remainBlueCube);
		assertEquals(12, remainYellowCube);
		assertEquals(4, medic.action);
		assertTrue(!Game.getInstance().isDiseaseEradicated(GameColor.YELLOW));
		assertTrue(!Game.getInstance().isDiseaseEradicated(GameColor.BLUE));
	}

	@Test
	public void testSpecialSkillCalls() {
		medicAction = EasyMock.partialMockBuilder(MedicAction.class).withConstructor(board, medic)
				.addMockedMethod("removeAllCubes").createMock();

		medicAction.removeAllCubes();
		EasyMock.replay(medicAction);
		medicAction.useSpecialSkill();
		EasyMock.verify(medicAction);
	}
}
