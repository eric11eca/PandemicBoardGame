package testPlayerCommonActions;


import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.Test;

import data.Board;
import player.Player;
import player.PlayerData;
import player.StationBuilderNormal;

public class TestPlayerBuildStation {
	@Test
	public void testBuildStation() {
		Board board = new Board();
		PlayerData playerData = new PlayerData();
		
		playerData.action = 4;
		playerData.buildStationModel = EasyMock.createMock(StationBuilderNormal.class);

		Player player = new Player(board, playerData);
		
		playerData.buildStationModel.buildStation();
		
		EasyMock.replay(playerData.buildStationModel);
		
		player.getPlayerAction(Board.ActionName.BUILDSTATION).executeAction();
		assertEquals(3, playerData.action);
		
		EasyMock.verify(playerData.buildStationModel);
	}

}
