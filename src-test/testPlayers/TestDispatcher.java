package testPlayers;

import static org.junit.Assert.assertEquals;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import data.Board;
import game.city.City;
import helpers.TestCityFactory;
import player.Player;
import player.PlayerData;
import playerAction.DispatcherAction;
import playerAction.SpecialSkill;

public class TestDispatcher {
	Board board;
	Player scientist;
	PlayerData scientistData;
	SpecialSkill dispatcherAction;
	TestCityFactory cityFactory = new TestCityFactory();

	@Before
	public void setup() {
		board = new Board();

		City paris = cityFactory.makeFakeCity("Paris");
		City delhi = cityFactory.makeFakeCity("Delhi");

		board.cities.put("Paris", paris);
		board.cities.put("Delhi", delhi);

		dispatcherAction = new DispatcherAction(board);

		scientistData = new PlayerData();
		scientistData.role = Board.Roles.SCIENTIST;
		scientistData.location = paris;
		scientistData.action = 4;
		scientist = EasyMock.createMock(Player.class);
		scientist.board = board;
		scientist.playerData = scientistData;
		board.currentPlayers.add(scientist);
		board.pawnTobeMoved = 0;
	}

	@Test
	public void testMoveOtherPlayer() {
		board.dispatcherCase = 0;
		board.newLocationName = "Delhi";
		EasyMock.replay(scientist);
		dispatcherAction.useSpecialSkill();
		assertEquals("Delhi", scientistData.location.getName());
		EasyMock.verify(scientist);
	}
}
