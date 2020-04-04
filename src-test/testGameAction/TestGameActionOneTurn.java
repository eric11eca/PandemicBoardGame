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

import cards.PlayerCard;
import data.Board;
import game.EpidemicCardAction;
import game.GameColor;
import game.city.City;
import game.disease.CubeData;
import game.disease.CubeDataImpl;
import game.player.PlayerImpl;
import gameAction.GameAction;
import lang.I18n;
import player.PlayerData;
import test.util.TestAccess;
import test.util.TestCityBuilder;

public class TestGameActionOneTurn {
	Board board;
	GameAction action;
	PlayerData playerData;

	TestCityBuilder cityFactory = new TestCityBuilder();
	TestAccess access = new TestAccess();

	@Before
	public void setup() {
		board = new Board();
		action = new GameAction(board);
		playerData = new PlayerData();

		board.currentPlayer = EasyMock.createMock(PlayerImpl.class);
		board.currentPlayer.playerData = playerData;
		board.currentPlayer.playerData.role = Board.Role.OPERATIONSEXPERT;

	}

	@Test
	public void testInfectCityOnQueitNight() {
		board.inQueitNight = true;
		CubeData cityDisease = new CubeDataImpl();
		cityDisease.addDiseaseCube(GameColor.RED);
		City city = new TestCityBuilder().cubeData(cityDisease).build();
		board.cities.put("NewYork", city);

		action.infection();
		int numOfRedCubes = cityDisease.getDiseaseCubeCount(GameColor.RED);
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

		board.currentPlayer = EasyMock.strictMock(PlayerImpl.class);
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

		board.currentPlayer = EasyMock.strictMock(PlayerImpl.class);
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

		board.currentPlayer = EasyMock.createMock(PlayerImpl.class);
		board.currentPlayer.receiveCard(newyork);

		EpidemicCardAction epidemic = EasyMock.createMock(EpidemicCardAction.class);

		String infectCityName = "Infect";
		board.validInfectionCards.add(infectCityName);
		CubeData cityDisease = new CubeDataImpl();
		City infectCity = new TestCityBuilder().name(infectCityName).color(GameColor.BLUE).cubeData(cityDisease)
				.build();
		cityDisease.addDiseaseCube(GameColor.BLUE);
		board.cities.put(infectCityName, infectCity);
		// FIXME Current implementation not supporting this yet.
//		access.getGameCubeData().setDiseaseCubeCount(GameColor.BLUE, 13);
//		// board.remainDiseaseCube.put("BLUE", 13);
//
//		action.epidemic = epidemic;
//		epidemic.performEpidemic();
//
//		board.currentPlayer.playerData = new PlayerData();
//		board.currentPlayer.playerData.hand = EasyMock.createMock(HashMap.class);
//		EasyMock.expect(board.currentPlayer.playerData.hand.size()).andReturn(6);
//
//		EasyMock.replay(board.validPlayerCards, board.currentPlayer, epidemic, board.currentPlayer.playerData.hand);
//
//		action.drawTwoPlayerCards();
//
//		EasyMock.verify(board.validPlayerCards, board.currentPlayer, epidemic, board.currentPlayer.playerData.hand);
	}

	@Test
	public void testInfectionPhase() {
//		access.getGameCubeData().setDiseaseCubeCount(GameColor.RED, 23);
//		access.getGameCubeData().setDiseaseCubeCount(GameColor.BLUE, 23);
//
//		// board.remainDiseaseCube.put("RED", 24);
//		// board.remainDiseaseCube.put("BLUE", 24);
//		board.validInfectionCards.add("cityA");
//		board.validInfectionCards.add("cityB");
//		City cityA = cityFactory.makeFakeCity("cityA", GameColor.RED);
//		City cityB = cityFactory.makeFakeCity("cityB", GameColor.BLUE);
//		board.cities.put("cityA", cityA);
//		board.cities.put("cityB", cityB);
//
//		board.infectionRateTracker.push(2);
//
//		action.infection();
//		int newValidInfectionPileSize = board.validInfectionCards.size();
//		int newDiscardInfectionPileSize = board.discardInfectionCards.size();
//		assertEquals(0, newValidInfectionPileSize);
//		assertEquals(2, newDiscardInfectionPileSize);
//		assertEquals(22, access.getGameCubeData().getDiseaseCubeCount(GameColor.RED));// board.remainDiseaseCube.get("RED").intValue());
//		assertEquals(22, access.getGameCubeData().getDiseaseCubeCount(GameColor.BLUE));
//		assertEquals(1, access.getCityDisease(cityA).getDiseaseCubeCount(GameColor.RED));
//		assertEquals(1, access.getCityDisease(cityB).getDiseaseCubeCount(GameColor.BLUE));
	}

	@Test
	public void testAirliftEventCard() {
		String eventCardName = "Airlift";
		board.currentPlayer.useEventCard(eventCardName);
		board.messages = EasyMock.createMock(I18n.class);
		// TODO partially broken
		// EasyMock.expect(board.messages.getString("Airlift")).andReturn("Airlift");
		// EasyMock.replay(board.currentPlayer, board.messages);
		action.playEventCard(eventCardName);
		// EasyMock.verify(board.currentPlayer, board.messages);
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
