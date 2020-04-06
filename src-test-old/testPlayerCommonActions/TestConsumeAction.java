//package testPlayerCommonActions;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Test;
//
//import data.Board;
//import game.player.Player;
//import game.player.PlayerImpl;
//import player.PlayerData;
//
//public class TestConsumeAction {
//
//	@Test
//	public void testConsumeAction() {
//		PlayerData medicData = new PlayerData();
//		medicData.action = 4;
//		Player medic = new PlayerImpl(new Board(), medicData);
//		medic.consumeAction();
//		assertEquals(3, medicData.action);
//	}
//}
