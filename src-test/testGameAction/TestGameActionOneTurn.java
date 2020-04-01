package testGameAction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import cardActions.EpidemicCardAction;
import cards.PlayerCard;
import data.Board;
import data.Board.ActionName;
import data.City;
import gameAction.GameAction;
import player.Player;
import player.PlayerData;
import playerAction.CureDisease;
import playerAction.PlayEventCard;
import playerAction.PlayerAction;

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
		City city = new City();
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
		City infectCity = new City(infectCityName);
		infectCity.color = "BLUE";
		infectCity.diseaseCubes.put("BLUE", 1);
		board.cities.put(infectCityName, infectCity);
		board.remainDiseaseCube.put("BLUE", 13);

		action.epidemic = epidemic;
		epidemic.performEpidemic();

		board.currentPlayer.playerData = new PlayerData();
		board.currentPlayer.playerData.hand = EasyMock.createMock(HashMap.class);
		EasyMock.expect(board.currentPlayer.playerData.hand.size()).andReturn(6);

		EasyMock.replay(board.validPlayerCards, board.currentPlayer, 
				epidemic, board.currentPlayer.playerData.hand);

		action.drawTwoPlayerCards();

		EasyMock.verify(board.validPlayerCards, board.currentPlayer, 
				epidemic, board.currentPlayer.playerData.hand);
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
	public void testAirliftEventCard() {
		board.eventCardName = "Airlift";
		board.currentPlayer.eventCardName = "Airlift";
		
		PlayerAction playEventCard = EasyMock.createMock(PlayEventCard.class);
		EasyMock.expect(board.currentPlayer.getPlayerAction(ActionName.PLAYEVENTCARD)).andReturn(playEventCard);
		
		EasyMock.replay(board.currentPlayer);
		action.doAction(ActionName.PLAYEVENTCARD);
		EasyMock.verify(board.currentPlayer);
		assertTrue(action.doesChangeLocation);
	}

	@Test
	public void testPlayEventCard() {
		board.eventCardName = "Forecast";
		board.currentPlayer.eventCardName = "Forecast";
		
		PlayerAction playEventCard = EasyMock.createMock(PlayEventCard.class);
		EasyMock.expect(board.currentPlayer.getPlayerAction(ActionName.PLAYEVENTCARD)).andReturn(playEventCard);
		
		EasyMock.replay(board.currentPlayer);
		action.doAction(Board.ActionName.PLAYEVENTCARD);
		EasyMock.verify(board.currentPlayer);
	}

	@Test
	public void testDoPlayerAction() {
		board.currentPlayer.cardsToCureDisease = new ArrayList<PlayerCard>();
		
		PlayerAction cureDisease = EasyMock.createNiceMock(CureDisease.class);
		EasyMock.expect(board.currentPlayer.getPlayerAction(ActionName.CUREDISEASE)).andReturn(cureDisease);
		
		EasyMock.replay(board.currentPlayer);
		action.doAction(ActionName.CUREDISEASE);
		EasyMock.verify(board.currentPlayer);
	}
}
