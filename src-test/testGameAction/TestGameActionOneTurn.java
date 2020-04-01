package testGameAction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import cardActions.EpidemicCardAction;
import cards.PlayerCard;
import data.Board;
import game.City;
import data.CityData;
import data.GameColor;
import gameAction.GameAction;
import initialize.Messages;
import player.Player;
import player.PlayerData;

public class TestGameActionOneTurn {
	Board board;
	GameAction action;
	PlayerData playerData;

	@Before
	public void setup() {
		board = new Board();
		action = new GameAction(board);
		playerData = new PlayerData();

		board.currentPlayer = EasyMock.createMock(Player.class);
		board.currentPlayer.playerData = playerData;
		board.currentPlayer.playerData.role = Board.Roles.OPERATIONSEXPERT;
	}

	@Test
	public void testInfectCityOnQueitNight() {
		board.inQueitNight = true;
		City city = new City(new CityData("NewYork", GameColor.BLACK, 10), 0, 0);
		board.cities.put("NewYork", city);
		city.diseaseCubes.put("RED", 1);
		action.infection();
		int numOfRedCubes = city.diseaseCubes.get("RED");
		assertEquals(1, numOfRedCubes);
		assertFalse(board.inQueitNight);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = RuntimeException.class)
	public void testNoMorePlayerCardToDraw() {
		board.validPlayerCards = EasyMock.createMock(ArrayList.class);
		EasyMock.expect(board.validPlayerCards.isEmpty()).andReturn(true);
		GameAction action = new GameAction(board);
		EasyMock.replay(board.validPlayerCards);
		action.drawTwoPlayerCards();
		EasyMock.verify(board.validPlayerCards);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = RuntimeException.class)
	public void testDrawTwoCityCardsWithPlayerExceedHandLimit() {
		board.currentPlayer.playerData.hand = EasyMock.createMock(HashMap.class);
		EasyMock.expect(board.currentPlayer.playerData.hand.size()).andReturn(8);
		EasyMock.replay(board.currentPlayer.playerData.hand);
		action.drawTwoPlayerCards();
		EasyMock.verify(board.currentPlayer.playerData.hand);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDrawTwoCityCardWithFull() {
		board.validPlayerCards = EasyMock.strictMock(ArrayList.class);
		EasyMock.expect(board.validPlayerCards.isEmpty()).andReturn(false);
		PlayerCard newyork = new PlayerCard(Board.CardType.CITYCARD, "NewYork");
		EasyMock.expect(board.validPlayerCards.get(0)).andReturn(newyork);
		EasyMock.expect(board.validPlayerCards.remove(0)).andReturn(newyork);

		EasyMock.expect(board.validPlayerCards.isEmpty()).andReturn(false);
		PlayerCard chicago = new PlayerCard(Board.CardType.CITYCARD, "Chicago");
		EasyMock.expect(board.validPlayerCards.get(0)).andReturn(chicago);
		EasyMock.expect(board.validPlayerCards.remove(0)).andReturn(chicago);

		board.currentPlayer = EasyMock.strictMock(Player.class);
		board.currentPlayer.receiveCard(newyork);
		board.currentPlayer.receiveCard(chicago);
		board.currentPlayer.playerData = new PlayerData();
		board.currentPlayer.playerData.hand = EasyMock.createMock(HashMap.class);
		EasyMock.expect(board.currentPlayer.playerData.hand.size()).andReturn(7);

		EasyMock.replay(board.validPlayerCards, board.currentPlayer, board.currentPlayer.playerData.hand);

		action.drawTwoPlayerCards();

		EasyMock.verify(board.validPlayerCards, board.currentPlayer, board.currentPlayer.playerData.hand);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDrawTwoCityCard() {
		board.validPlayerCards = EasyMock.strictMock(ArrayList.class);
		EasyMock.expect(board.validPlayerCards.isEmpty()).andReturn(false);
		PlayerCard newyork = new PlayerCard(Board.CardType.CITYCARD, "NewYork");
		EasyMock.expect(board.validPlayerCards.get(0)).andReturn(newyork);
		EasyMock.expect(board.validPlayerCards.remove(0)).andReturn(newyork);

		EasyMock.expect(board.validPlayerCards.isEmpty()).andReturn(false);
		PlayerCard chicago = new PlayerCard(Board.CardType.CITYCARD, "Chicago");
		EasyMock.expect(board.validPlayerCards.get(0)).andReturn(chicago);
		EasyMock.expect(board.validPlayerCards.remove(0)).andReturn(chicago);

		board.currentPlayer = EasyMock.strictMock(Player.class);
		board.currentPlayer.receiveCard(newyork);
		board.currentPlayer.receiveCard(chicago);
		board.currentPlayer.playerData = new PlayerData();
		board.currentPlayer.playerData.hand = EasyMock.createMock(HashMap.class);
		EasyMock.expect(board.currentPlayer.playerData.hand.size()).andReturn(6);

		EasyMock.replay(board.validPlayerCards, board.currentPlayer, board.currentPlayer.playerData.hand);

		action.drawTwoPlayerCards();

		EasyMock.verify(board.validPlayerCards, board.currentPlayer, board.currentPlayer.playerData.hand);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDrawOneCityCardAndOneEpidemicCard() {
		board.validPlayerCards = EasyMock.strictMock(ArrayList.class);

		EasyMock.expect(board.validPlayerCards.isEmpty()).andReturn(false);
		PlayerCard newyork = new PlayerCard(Board.CardType.CITYCARD, "NewYork");
		EasyMock.expect(board.validPlayerCards.get(0)).andReturn(newyork);
		EasyMock.expect(board.validPlayerCards.remove(0)).andReturn(newyork);

		EasyMock.expect(board.validPlayerCards.isEmpty()).andReturn(false);
		PlayerCard epidemicCard = new PlayerCard(Board.CardType.EPIDEMIC, "EPIDEMIC");
		EasyMock.expect(board.validPlayerCards.get(0)).andReturn(epidemicCard);
		EasyMock.expect(board.validPlayerCards.remove(0)).andReturn(epidemicCard);

		board.currentPlayer = EasyMock.createMock(Player.class);
		board.currentPlayer.receiveCard(newyork);

		EpidemicCardAction epidemic = EasyMock.createMock(EpidemicCardAction.class);

		String infectCityName = "Infect";
		board.validInfectionCards.add(infectCityName);
		City infectCity = new City(new CityData(infectCityName, GameColor.BLUE, 10), 0, 0);
		infectCity.diseaseCubes.put("BLUE", 1);
		board.cities.put(infectCityName, infectCity);
		board.remainDiseaseCube.put("BLUE", 13);

		action.epidemic = epidemic;
		epidemic.performEpidemic();

		board.currentPlayer.playerData = new PlayerData();
		board.currentPlayer.playerData.hand = EasyMock.createMock(HashMap.class);
		EasyMock.expect(board.currentPlayer.playerData.hand.size()).andReturn(6);

		EasyMock.replay(board.validPlayerCards, board.currentPlayer, epidemic, board.currentPlayer.playerData.hand);

		action.drawTwoPlayerCards();

		EasyMock.verify(board.validPlayerCards, board.currentPlayer, epidemic, board.currentPlayer.playerData.hand);
	}

	@Test
	public void testInfectionPhase() {
		board.remainDiseaseCube.put("RED", 24);
		board.remainDiseaseCube.put("BLUE", 24);
		board.validInfectionCards.add("cityA");
		board.validInfectionCards.add("cityB");
		City cityA = new City(new CityData("cityA", GameColor.RED, 10), 0, 0);
		City cityB = new City(new CityData("cityB", GameColor.BLUE, 10), 0, 0);
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
	public void testAirliftEventCard() {
		String eventCardName = "Airlift";
		board.currentPlayer.useEventCard(eventCardName);
		board.messages = EasyMock.createMock(Messages.class);

		EasyMock.expect(board.messages.getString("Airlift")).andReturn("Airlift");
		EasyMock.replay(board.currentPlayer, board.messages);
		action.playEventCard(eventCardName);
		EasyMock.verify(board.currentPlayer, board.messages);
		assertTrue(action.doesChangeLocation);
	}

	@Test
	public void testDoPlayerAction() {
		List<PlayerCard> cardsToCureDisease = new ArrayList<PlayerCard>();
		board.currentPlayer.discoverCure(cardsToCureDisease);

		EasyMock.replay(board.currentPlayer);
		action.cureDisease(cardsToCureDisease);
		EasyMock.verify(board.currentPlayer);
	}
}
