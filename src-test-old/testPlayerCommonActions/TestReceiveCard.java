//package testPlayerCommonActions;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
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
//public class TestReceiveCard {
//	private Board board;
//	Player player;
//	private ArrayList<PlayerCard> citycards;
//	ActionEventCard eventCardAction;
//	PlayerData playerData;
//
//	@Before
//	public void setup() {
//		board = new Board();
//		playerData = new PlayerData();
//
//		String[] cities = { "city1", "city2", "city3", "city4", "city5", "city6", "city7", "city8" };
//		citycards = new ArrayList<>();
//		for (String city : cities) {
//			citycards.add(new PlayerCard(Board.CardType.CITYCARD, city));
//		}
//		eventCardAction = new ActionEventCard(board);
//		player = new PlayerImpl(board, playerData);
//	}
//
//	@Test
//	public void testNormalReceiveCard() {
//		player.receiveCard(citycards.get(0));
//		assertEquals(1, playerData.hand.size());
//	}
//	
//	@Test
//	public void testNormalReceiveSevenCard() {
//		for (int i = 0; i < 7; i++) {
//			PlayerCard playercard = citycards.get(i);
//			player.receiveCard(playercard);
//		}
//		assertEquals(7, playerData.hand.size());
//	}
//
//}