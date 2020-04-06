//package testPlayerCommonActions;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import org.easymock.EasyMock;
//import org.junit.Test;
//
//import data.Board;
//import game.player.PlayerImpl;
//import game.player.action.ActionEventCard;
//import player.PlayerData;
//
//public class TestUseSpecialEventCard {
//
//	@Test
//	public void testUseSpecialEventCard() {
//		String airlift = "Airlift";
//		Board board = new Board();
//		PlayerData contingencyPlannerData = new PlayerData();
//		
//		contingencyPlannerData.specialEventCard = airlift;
//		board.eventCardAction = EasyMock.createMock(ActionEventCard.class);
//		EasyMock.expect(board.eventCardAction.executeEventCard(airlift))
//			.andReturn(true);
//		board.idxofPlayerAirlift = 0;
//		PlayerImpl contingencyPlanner = new PlayerImpl(board, contingencyPlannerData);
//		board.currentPlayers.add(contingencyPlanner);
//		
//		EasyMock.replay(board.eventCardAction);
//	
//		int old_size = board.discardEventCards.size();
//		contingencyPlanner.useEventCard(airlift);
//		assertTrue(contingencyPlannerData.specialEventCard == null);
//		int new_size = board.discardEventCards.size();
//		assertEquals(old_size, new_size);
//		EasyMock.verify(board.eventCardAction);
//	}
//}
