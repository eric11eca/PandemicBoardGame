//package testPlayerCommonActions;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import cards.PlayerCard;
//import data.Board;
//import game.player.Player;
//import game.player.PlayerImpl;
//import game.player.action.ActionEventCard;
//import player.PlayerData;
//
//public class TestDiscardCard {
//	Board board;
//	Player player;
//	ActionEventCard eventCardAction;
//	PlayerData playerData;
//	
//	@Before
//	public void setup() {
//		board = new Board();
//		playerData = new PlayerData();
//		String[] cities = { "A", "B"};
//		for (String city : cities) {
//			playerData.hand.put(city, new PlayerCard(Board.CardType.CITYCARD, city));
//		}
//		eventCardAction = new ActionEventCard(board);
//		player = new PlayerImpl(board, playerData);
//	}
//
//	@Test
//	public void testDiscardCard() {
//		String cardName = "B";
//		board.cardToBeDiscard.add(cardName);
//		player.discardCard();
//		assertEquals(1, playerData.hand.size());
//		assertEquals(0, board.cardToBeDiscard.size());
//	}
//}
