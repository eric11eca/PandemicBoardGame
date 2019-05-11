package testGameAction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import cardActions.EventCardAction;
import cards.PlayerCard;
import data.Board;
import data.City;
import gameAction.GameAction;
import player.Player;
import player.PlayerData;

public class TestGameActionOneTurn {
	Board board;
	Player player;
	GameAction action;
	PlayerData playerData;
	String[] citynames = { "Chicago", "NewYork", "London", "Washington" };
	String[] handCardNames = { "city1", "city2", "city3", "city4", "city5", "city6" };
	EventCardAction eventCardAction;
	@Before
	public void setup() {
		board = new Board();
		action = new GameAction(board);

		PlayerCard playercard = new PlayerCard(Board.CardType.CITYCARD, "Shanghai");
		board.validPlayerCards.add(playercard);

		playerData = new PlayerData();
		eventCardAction = new EventCardAction(board);
		player = new Player(board, playerData);
		board.currentPlayers.add(player);
		board.currentPlayer = player;
	}

	@Test
	public void testDrawTwoPlayerCards() {
		initializePlayerCard(citynames, true);
		assertEquals(0, playerData.hand.size());
		action.drawTwoPlayerCards();
		assertEquals(2, playerData.hand.size());
		assertTrue(playerData.hand.containsKey("Chicago"));
		assertEquals(3, board.validPlayerCards.size());
	}

	private void initializePlayerCard(String[] nameList, boolean addToBoard) {
		for (String playercardName : nameList) {
			PlayerCard playercard = new PlayerCard(Board.CardType.CITYCARD, playercardName);
			if (addToBoard)
				board.validPlayerCards.add(playercard);
			else
				playerData.hand.put(playercardName, playercard);
		}
	}

	@Test (expected = RuntimeException.class)
	public void testDrawTwoCityCardsWithPlayerExceedHandLimit() {
		initializePlayerCard(citynames, true);
		initializePlayerCard(handCardNames, false);
		action.drawTwoPlayerCards();
		assertEquals(7, playerData.hand.size());
		assertEquals(3, board.validPlayerCards.size());
	}
	
	@Test
	public void testHandLimitWithSizeEqualToLimit() {
		initializePlayerCard(citynames, true);
		initializePlayerCard(handCardNames, false);
		playerData.hand.remove("city1");
		action.drawTwoPlayerCards();
		assertEquals(7, playerData.hand.size());
		assertEquals(3, board.validPlayerCards.size());
	}
	
	@Test
	public void testHandLimitWithSizeUnderLimit() {
		initializePlayerCard(citynames, true);
		initializePlayerCard(handCardNames, false);
		playerData.hand.remove("city1");
		playerData.hand.remove("city2");
		action.drawTwoPlayerCards();
		assertEquals(6, playerData.hand.size());
		assertEquals(3, board.validPlayerCards.size());
	}

	@Test
	public void testDrawOneCityCardAndOneEpidemicCard() {
		board.infectionRateTracker.push(4);
		board.infectionRateTracker.push(2);
		PlayerCard epidemicCard = new PlayerCard(Board.CardType.EPIDEMIC, "EPIDEMIC");
		board.validPlayerCards.add(epidemicCard);
		String infectCityName = "Infect";
		board.validInfectionCards.add(infectCityName);
		City infectCity = new City(infectCityName);
		infectCity.color = "BLUE";
		infectCity.diseaseCubes.put("BLUE", 1);
		board.cities.put(infectCityName, infectCity);
		board.remainDiseaseCube.put("BLUE", 13);
		action.drawTwoPlayerCards();
		assertEquals(1, playerData.hand.size());
		assertTrue(4 == board.infectionRateTracker.peek());
	}
	
	@Test
	public void testLackOfPlayerCards() {
		int oldHandSize = playerData.hand.size();
		board.validPlayerCards.clear();
		action.drawTwoPlayerCards();
		assertTrue(board.gameEnd);
		assertTrue(board.playerLose);
		int newHandSize = playerData.hand.size();
		assertEquals(oldHandSize, newHandSize);
	}
	
	@Test
	public void testInfectionPhase() {
		board.remainDiseaseCube.put("RED", 24);
		board.remainDiseaseCube.put("BLUE", 24);
		board.validInfectionCards.add("cityA");
		board.validInfectionCards.add("cityB");
		City cityA = new City();
		City cityB = new City();
		cityA.cityName = "cityA";
		cityB.cityName = "cityB";
		cityA.color = "RED";
		cityB.color = "BLUE";
		cityA.diseaseCubes.put("RED", 0);
		cityB.diseaseCubes.put("BLUE", 0);
		board.cities.put("cityA", cityA);
		board.cities.put("cityB", cityB);
		
		board.infectionRateTracker.push(2);
		action.infection();
		int newValidInfectionPileSize = board.validInfectionCards.size();
		int newDiscardInfectionPileSize = board.discardInfectionCards.size();
		assertEquals(0, newValidInfectionPileSize);
		assertEquals(2, newDiscardInfectionPileSize);
		assertTrue(23 == board.remainDiseaseCube.get("RED"));
		assertTrue(23 == board.remainDiseaseCube.get("BLUE"));
		assertTrue(1 == cityA.diseaseCubes.get("RED"));
		assertTrue(1 == cityB.diseaseCubes.get("BLUE"));
	}
	
	@Test
	public void testDirectFlight() {
		board.currentPlayer = EasyMock.createMock(Player.class);
		board.currentPlayer.playerData = playerData;
		PlayerCard citycard = new PlayerCard(Board.CardType.CITYCARD, "Shanghai");
		board.currentPlayer.playerData.hand = EasyMock.createMock(HashMap.class);
		EasyMock.expect(board.currentPlayer.playerData.hand.get(board.cityCardNameDirect)).andReturn(citycard);
		board.currentPlayer.directFlight(citycard);
		EasyMock.replay(board.currentPlayer, board.currentPlayer.playerData.hand);
		action.doAction(Board.ActionName.DIRECTFLIGHT);
		EasyMock.verify(board.currentPlayer, board.currentPlayer.playerData.hand);
	}
	
	@Test
	public void testPlayEventCard() {
		board.currentPlayer = EasyMock.createMock(Player.class);
		board.eventCardName = "Forecast";
		board.currentPlayer.useEventCard(board.eventCardName);
		EasyMock.replay(board.currentPlayer);
		action.doAction(Board.ActionName.PLAYEVENTCARD);
		EasyMock.verify(board.currentPlayer);
	}
	
	@Test
	public void testDiscoverCure() {
		board.currentPlayer = EasyMock.createMock(Player.class);
		List<PlayerCard> cardsToCureDisease = new ArrayList<>();
		board.currentPlayer.discoverCure(cardsToCureDisease);
		EasyMock.replay(board.currentPlayer);
		action.doAction(Board.ActionName.CUREDISEASE);
		EasyMock.verify(board.currentPlayer);
	}
	
	@Test
	public void testTreatDisease() {
		board.currentPlayer = EasyMock.createMock(Player.class);
		board.currentPlayer.treat(board.diseaseBeingTreated);
		EasyMock.replay(board.currentPlayer);
		action.doAction(Board.ActionName.TREATDISEASE);
		EasyMock.verify(board.currentPlayer);
	}
	
	@Test
	public void testDrive() {
		board.currentPlayer = EasyMock.createMock(Player.class);
		City city = new City("Shanghai");
		board.cities = EasyMock.createMock(HashMap.class);
		EasyMock.expect(board.cities.get(board.driveDestinationName)).andReturn(city);
		board.currentPlayer.drive(city);
		EasyMock.replay(board.currentPlayer, board.cities);
		action.doAction(Board.ActionName.DRIVE);
		EasyMock.verify(board.currentPlayer, board.cities);
	}
	
	@Test
	public void testCharterFlight() {
		board.currentPlayer = EasyMock.createMock(Player.class);
		board.currentPlayer.charterFlight();
		EasyMock.replay(board.currentPlayer);
		action.doAction(Board.ActionName.CHARTERFLIGHT);
		EasyMock.verify(board.currentPlayer);
	}
	
	@Test
	public void testShuttleFlight() {
		board.currentPlayer = EasyMock.createMock(Player.class);
		City city = new City("Shanghai");
		board.cities = EasyMock.createMock(HashMap.class);
		EasyMock.expect(board.cities.get(board.shuttleDestinationName)).andReturn(city);
		board.currentPlayer.shuttleFlight(city);
		EasyMock.replay(board.currentPlayer, board.cities);
		action.doAction(Board.ActionName.SHUTTLEFLIGHT);
		EasyMock.verify(board.currentPlayer, board.cities);
	}
	
	@Test
	public void testBuildStation() {
		board.currentPlayer = EasyMock.createMock(Player.class);
		board.currentPlayer.buildStation();
		EasyMock.replay(board.currentPlayer);
		action.doAction(Board.ActionName.BUILDRESEARCH);
		EasyMock.verify(board.currentPlayer);
	}
	
	@Test
	public void testShareKnowledge() {
		board.currentPlayer = EasyMock.createMock(Player.class);
		board.currentPlayer.shareKnowledge();
		EasyMock.replay(board.currentPlayer);
		action.doAction(Board.ActionName.SHAREKNOWLEDGE);
		EasyMock.verify(board.currentPlayer);
	}
}
