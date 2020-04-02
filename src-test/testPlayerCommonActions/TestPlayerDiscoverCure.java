package testPlayerCommonActions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import cardActions.EventCardAction;
import cards.PlayerCard;
import data.Board;
<<<<<<< HEAD
import data.GameColor;
import game.Game;
import helpers.TestAccess;
import helpers.TestCityFactory;
=======
import data.CityData;
import game.City;
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
import player.DiscoverCureNormal;
import player.Player;
import player.PlayerData;

public class TestPlayerDiscoverCure {
	Board board;
	Player player;
	PlayerData playerData;
	EventCardAction eventCardAction;
	String redCityName1, redCityName2, redCityName3, redCityName4, redCityName5;
	PlayerCard redCity1, redCity2, redCity3, redCity4, redCity5;
	ArrayList<PlayerCard> cards;

<<<<<<< HEAD
	TestCityFactory cityFactory = new TestCityFactory();
	TestAccess access = new TestAccess();

=======
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
	@Before
	public void setup() {
		board = new Board();
		playerData = new PlayerData();
		
		redCityName1 = "redCity1";
		redCityName2 = "redCity2";
		redCityName3 = "redCity3";
		redCityName4 = "redCity4";
		redCityName5 = "redCity5";

		redCity1 = new PlayerCard(Board.CardType.CITYCARD, redCityName1);
		redCity2 = new PlayerCard(Board.CardType.CITYCARD, redCityName2);
		redCity3 = new PlayerCard(Board.CardType.CITYCARD, redCityName3);
		redCity4 = new PlayerCard(Board.CardType.CITYCARD, redCityName4);
		redCity5 = new PlayerCard(Board.CardType.CITYCARD, redCityName5);

		redCity1.color = "RED";
		redCity2.color = "RED";
		redCity3.color = "RED";
		redCity4.color = "RED";
		redCity5.color = "RED";

		playerData.hand.put(redCityName1, redCity1);
		playerData.hand.put(redCityName2, redCity2);
		playerData.hand.put(redCityName3, redCity3);
		playerData.hand.put(redCityName4, redCity4);
		playerData.hand.put(redCityName5, redCity5);

		cards = new ArrayList<PlayerCard>();
		cards.add(redCity1);
		cards.add(redCity2);
		cards.add(redCity3);
		cards.add(redCity4);
		cards.add(redCity5);
		
		playerData.discoverCureModel = new DiscoverCureNormal(board.curedDiseases);
		
		eventCardAction = new EventCardAction(board);
		player = new Player(board, playerData);
		access.resetGame();
		access.getGameCubeData().setDiseaseCubeCount(GameColor.RED, 7);
		// board.remainDiseaseCube.put("RED", 7);
	}
	
	@Test
	public void testEradicateDiseaseWhenDiscoverCure() {
<<<<<<< HEAD
		access.getGameCubeData().setDiseaseCubeCount(GameColor.RED, 24);
		playerData.location = cityFactory.makeFakeCity();
		playerData.location.buildResearchStation();
		player.cardsToCureDisease = cards;
		player.getPlayerAction(Board.ActionName.CUREDISEASE).executeAction();
=======
		board.remainDiseaseCube.put("RED", 24);
		String newyork = "NewYork";
		CityData newyork_data = new CityData(newyork, null, 0);
		City newyorkCity = new City(newyork_data, 0, 0);
		playerData.location = newyorkCity;
		playerData.location.researchStation = true;
		player.discoverCure(cards);
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
		assertTrue(board.curedDiseases.contains("RED"));
		assertTrue(Game.getInstance().isDiseaseEradicated(GameColor.RED));// board.eradicatedColor.contains("RED"));
	}

	@Test
	public void testPlayerdiscardCardWhenDiscoverCure() {
<<<<<<< HEAD
		playerData.location = cityFactory.makeFakeCity();
		playerData.location.buildResearchStation();
		player.cardsToCureDisease = cards;
		player.getPlayerAction(Board.ActionName.CUREDISEASE).executeAction();
=======
		String newyork = "NewYork";
		CityData newyork_data = new CityData(newyork, null, 0);
		City newyorkCity = new City(newyork_data, 0, 0);
		playerData.location = newyorkCity;
		playerData.location.researchStation = true;
		player.discoverCure(cards);
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
		assertTrue(board.curedDiseases.contains("RED"));
		assertEquals(0, playerData.hand.size());
		assertEquals(3, playerData.action);
	}

	@Test(expected = RuntimeException.class)
	public void testNotAtResearchStation() {
<<<<<<< HEAD
		playerData.location = cityFactory.makeFakeCity();
		playerData.location.removeResearchStation();
		player.cardsToCureDisease = cards;
		player.getPlayerAction(Board.ActionName.CUREDISEASE).executeAction();
=======
		String newyork = "NewYork";
		CityData newyork_data = new CityData(newyork, null, 0);
		City newyorkCity = new City(newyork_data, 0, 0);
		playerData.location = newyorkCity;
		playerData.location.researchStation = false;
		player.discoverCure(cards);
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
	}

	@Test
	public void testDiscoverDiscoveredCure() {
<<<<<<< HEAD
		playerData.location = cityFactory.makeFakeCity();
		playerData.location.buildResearchStation();
=======
		String newyork = "NewYork";
		CityData newyork_data = new CityData(newyork, null, 0);
		City newyorkCity = new City(newyork_data, 0, 0);
		playerData.location = newyorkCity;
		playerData.location.researchStation = true;
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
		board.curedDiseases.add("RED");
		player.discoverCure(cards);
		assertEquals(5, playerData.hand.size());
	}
	
	@Test(expected= RuntimeException.class)
	public void testWinGameAfterDiscoverAllCures() {
<<<<<<< HEAD
		playerData.location = cityFactory.makeFakeCity();
		playerData.location.buildResearchStation();
=======
		String newyork = "NewYork";
		CityData newyork_data = new CityData(newyork, null, 0);
		City newyorkCity = new City(newyork_data, 0, 0);
		playerData.location = newyorkCity;
		playerData.location.researchStation = true;
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
		board.curedDiseases.add("BLUE");
		board.curedDiseases.add("BLACK");
		board.curedDiseases.add("YELLOW");
		player.discoverCure(cards);
		assertEquals(4, board.curedDiseases.size());
	}
}
