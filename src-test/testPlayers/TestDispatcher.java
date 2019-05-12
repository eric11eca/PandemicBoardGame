package testPlayers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import initialize.Board;
import initialize.City;
import player.Player;
import player.PlayerData;
import playerAction.DispatcherAction;
import playerAction.SpecialSkill;

public class TestDispatcher {
	Board board;
	Player dispatcher;
	Player scientist;
	PlayerData scientistData;
	PlayerData dispatcherData;
	SpecialSkill dispatcherAction;

	@Before
	public void setup() {
		board  = new Board();
		City milan = new City();
		milan.cityName = "Milan";
		City paris = new City();
		paris.cityName = "Paris";
		City delhi = new City();
		delhi.cityName = "Delhi";
		
		board.cities.put("Milan", milan);
		board.cities.put("Paris", paris);
		board.cities.put("Delhi", delhi);
		
		dispatcherData = new PlayerData();
		dispatcherData.role = Board.Roles.DISPATCHER;
		dispatcherData.location = delhi;
		dispatcherAction = new DispatcherAction(board);
		dispatcherData.specialSkill = dispatcherAction;
		dispatcher = new Player(board, dispatcherData);
		board.currentPlayers.add(dispatcher);
		
		scientistData = new PlayerData();
		scientistData.role = Board.Roles.SCIENTIST;
		scientistData.location = paris;
		scientist = new Player(board, scientistData);
		board.currentPlayers.add(scientist);
		board.pawnTobeMoved = 1;
	}
	
	@Test
	public void testMoveOtherPlayer() {
		board.dispatcherCase = 0;
		board.newLocationName = "Delhi";
		dispatcher.playerData.specialSkill.specialSkill();
		assertEquals("Delhi", scientistData.location.cityName);
	}
	


}
