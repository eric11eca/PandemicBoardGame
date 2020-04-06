//package testPlayerCommonActions;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.util.ArrayList;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import cardActions.EventCardAction;
//import cards.PlayerCard;
//import data.Board;
//import data.GameColor;
//import game.Game;
//import player.DiscoverCureNormal;
//import player.Player;
//import player.PlayerData;
//import test.util.TestAccess;
//import test.util.TestCityBuilder;
//
//public class TestPlayerDiscoverCure {
//	Board board;
//	Player player;
//	PlayerData playerData;
//	EventCardAction eventCardAction;
//	String redCityName1, redCityName2, redCityName3, redCityName4, redCityName5;
//	PlayerCard redCity1, redCity2, redCity3, redCity4, redCity5;
//	ArrayList<PlayerCard> cards;
//
//	TestCityBuilder cityFactory = new TestCityBuilder();
//	TestAccess access = new TestAccess();
//
//	@Before
//	public void setup() {
//		board = new Board();
//		playerData = new PlayerData();
//
//		redCityName1 = "redCity1";
//		redCityName2 = "redCity2";
//		redCityName3 = "redCity3";
//		redCityName4 = "redCity4";
//		redCityName5 = "redCity5";
//
//		redCity1 = new PlayerCard(Board.CardType.CITYCARD, redCityName1);
//		redCity2 = new PlayerCard(Board.CardType.CITYCARD, redCityName2);
//		redCity3 = new PlayerCard(Board.CardType.CITYCARD, redCityName3);
//		redCity4 = new PlayerCard(Board.CardType.CITYCARD, redCityName4);
//		redCity5 = new PlayerCard(Board.CardType.CITYCARD, redCityName5);
//
//		redCity1.color = "RED";
//		redCity2.color = "RED";
//		redCity3.color = "RED";
//		redCity4.color = "RED";
//		redCity5.color = "RED";
//
//		playerData.hand.put(redCityName1, redCity1);
//		playerData.hand.put(redCityName2, redCity2);
//		playerData.hand.put(redCityName3, redCity3);
//		playerData.hand.put(redCityName4, redCity4);
//		playerData.hand.put(redCityName5, redCity5);
//
//		cards = new ArrayList<PlayerCard>();
//		cards.add(redCity1);
//		cards.add(redCity2);
//		cards.add(redCity3);
//		cards.add(redCity4);
//		cards.add(redCity5);
//
//		playerData.discoverCureModel = new DiscoverCureNormal(board.curedDiseases);
//
//		eventCardAction = new EventCardAction(board);
//		player = new Player(board, playerData);
//		access.resetGame();
//		access.getGameCubeData().setDiseaseCubeCount(GameColor.RED, 7);
//		// board.remainDiseaseCube.put("RED", 7);
//	}
//
//	@Test
//	public void testEradicateDiseaseWhenDiscoverCure() {
//		access.getGameCubeData().setDiseaseCubeCount(GameColor.RED, 24);
//		playerData.location = cityFactory.makeFakeCity();
//		playerData.location.buildResearchStation();
//		player.discoverCure(cards);
//		assertTrue(board.curedDiseases.contains("RED"));
//		assertTrue(Game.getInstance().isDiseaseEradicated(GameColor.RED));// board.eradicatedColor.contains("RED"));
//	}
//
//	@Test
//	public void testPlayerdiscardCardWhenDiscoverCure() {
//		playerData.location = cityFactory.makeFakeCity();
//		playerData.location.buildResearchStation();
//		player.discoverCure(cards);
//		assertTrue(board.curedDiseases.contains("RED"));
//		assertEquals(0, playerData.hand.size());
//		assertEquals(3, playerData.action);
//	}
//
//	@Test(expected = RuntimeException.class)
//	public void testNotAtResearchStation() {
//		playerData.location = cityFactory.makeFakeCity();
//		playerData.location.removeResearchStation();
//		player.discoverCure(cards);
//	}
//
//	@Test
//	public void testDiscoverDiscoveredCure() {
//		playerData.location = cityFactory.makeFakeCity();
//		playerData.location.buildResearchStation();
//		board.curedDiseases.add("RED");
//		player.discoverCure(cards);
//		assertEquals(5, playerData.hand.size());
//	}
//
//	@Test(expected = RuntimeException.class)
//	public void testWinGameAfterDiscoverAllCures() {
//		playerData.location = cityFactory.makeFakeCity();
//		playerData.location.buildResearchStation();
//		board.curedDiseases.add("BLUE");
//		board.curedDiseases.add("BLACK");
//		board.curedDiseases.add("YELLOW");
//		player.discoverCure(cards);
//		assertEquals(4, board.curedDiseases.size());
//	}
//}