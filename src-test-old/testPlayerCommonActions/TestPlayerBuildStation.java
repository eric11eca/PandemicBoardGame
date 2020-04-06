//package testPlayerCommonActions;
//
//
//import static org.junit.Assert.assertEquals;
//
//import org.easymock.EasyMock;
//import org.junit.Test;
//
//import data.Board;
//import game.player.Player;
//import game.player.PlayerImpl;
//import game.player.action.StationBuilderNormal;
//import player.PlayerData;
//
//public class TestPlayerBuildStation {
//	@Test
//	public void testBuildStation() {
//		Board board = new Board();
//		PlayerData playerData = new PlayerData();
//		
//		playerData.action = 4;
//		playerData.buildStationModel = EasyMock.createMock(StationBuilderNormal.class);
//
//		Player player = new PlayerImpl(board, playerData);
//		
//		playerData.buildStationModel.buildStation();
//		
//		EasyMock.replay(playerData.buildStationModel);
//		
//		player.buildStation();
//		assertEquals(3, playerData.action);
//		
//		EasyMock.verify(playerData.buildStationModel);
//	}
//
//}
