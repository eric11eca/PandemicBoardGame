package testPlayers;

import static org.junit.Assert.assertEquals;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import data.Board;
import data.City;
import player.Player;
import player.PlayerData;
import playerAction.DispatcherAction;
import playerAction.SpecialSkill;

public class TestDispatcher {
	Board board;
	Player scientist;
	PlayerData scientistData;
	SpecialSkill dispatcherAction;

	@Before
	public void setup() {
		board  = new Board();

		City paris = new City();
		paris.cityName = "Paris";
		City delhi = new City();
		delhi.cityName = "Delhi";

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
		assertEquals("Delhi", scientistData.location.cityName);
		EasyMock.verify(scientist);
	}
}
