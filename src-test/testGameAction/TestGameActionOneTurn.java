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
import data.City;
import gameAction.GameAction;
import initialize.Messages;
import player.Player;
import player.PlayerData;
import playerAction.MedicAction;

public class TestGameActionOneTurn {
	Board board;
	GameAction action;
	PlayerData playerData;

	@Before
	public void setup() {
		board = new Board();
		action = new GameAction(board);
		playerData = new PlayerData();
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

	@Test(expected = RuntimeException.class)
	public void testNoMorePlayerCardToDraw() {
		board.validPlayerCards = EasyMock.createMock(ArrayList.class);
		EasyMock.expect(board.validPlayerCards.isEmpty()).andReturn(true);
		GameAction action = new GameAction(board);
		EasyMock.replay(board.validPlayerCards);
		action.drawTwoPlayerCards();
		EasyMock.verify(board.validPlayerCards);
	}

	@Test(expected = RuntimeException.class)
	public void testDrawTwoCityCardsWithPlayerExceedHandLimit() {
		board.currentPlayer.playerData.hand = EasyMock.createMock(HashMap.class);
		EasyMock.expect(board.currentPlayer.playerData.hand.size()).andReturn(8);
		EasyMock.replay(board.currentPlayer.playerData.hand);
		action.drawTwoPlayerCards();
		EasyMock.verify(board.currentPlayer.playerData.hand);
	}
	
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
	public void testDirectFlight() {
		board.currentPlayer = EasyMock.createMock(Player.class);
		board.currentPlayer.playerData = this.playerData;
		PlayerCard citycard = new PlayerCard(Board.CardType.CITYCARD, "Shanghai");
		board.currentPlayer.playerData.hand = EasyMock.createMock(HashMap.class);
		EasyMock.expect(board.currentPlayer.playerData.hand.get(board.cityCardNameDirect)).andReturn(citycard);
		board.currentPlayer.directFlight(citycard);
		EasyMock.replay(board.currentPlayer, board.currentPlayer.playerData.hand);
		action.doAction(Board.ActionName.DIRECTFLIGHT);
		EasyMock.verify(board.currentPlayer, board.currentPlayer.playerData.hand);
	}

	@Test
	public void testAirliftEventCard() {
		board.currentPlayer = EasyMock.createMock(Player.class);
		board.eventCardName = "Airlift";
		board.currentPlayer.playerData = this.playerData;
		board.currentPlayer.playerData.role = Board.Roles.DISPATCHER;
		board.currentPlayer.useEventCard(board.eventCardName);
		board.messages = EasyMock.createMock(Messages.class);
		EasyMock.expect(board.messages.getString("Airlift")).andReturn("Airlift");
		EasyMock.replay(board.currentPlayer, board.messages);
		
		action.doAction(Board.ActionName.PLAYEVENTCARD);
		EasyMock.verify(board.currentPlayer, board.messages);
		assertTrue(action.doesChangeLocation);
	}

	@Test
	public void testPlayEventCard() {
		board.currentPlayer = EasyMock.createMock(Player.class);
		board.eventCardName = "Forecast";

		board.currentPlayer.playerData = this.playerData;
		board.currentPlayer.playerData.role = Board.Roles.DISPATCHER;
		board.currentPlayer.useEventCard(board.eventCardName);
		
		board.messages = EasyMock.createMock(Messages.class);
		EasyMock.expect(board.messages.getString("Airlift")).andReturn("Forecast");
		EasyMock.replay(board.currentPlayer, board.messages);
		
		action.doAction(Board.ActionName.PLAYEVENTCARD);
		EasyMock.verify(board.currentPlayer, board.messages);
	}

	@Test
	public void testDiscoverCure() {
		board.currentPlayer = EasyMock.createMock(Player.class);
		board.currentPlayer.playerData = this.playerData;
		board.currentPlayer.playerData.role = Board.Roles.DISPATCHER;
		List<PlayerCard> cardsToCureDisease = new ArrayList<>();
		board.currentPlayer.discoverCure(cardsToCureDisease);
		EasyMock.replay(board.currentPlayer);
		action.doAction(Board.ActionName.CUREDISEASE);
		EasyMock.verify(board.currentPlayer);
	}

	@Test
	public void testTreatDisease() {
		board.currentPlayer = EasyMock.createMock(Player.class);
		board.currentPlayer.playerData = this.playerData;
		board.currentPlayer.playerData.role = Board.Roles.DISPATCHER;
		board.currentPlayer.treat(board.diseaseBeingTreated);
		EasyMock.replay(board.currentPlayer);
		action.doAction(Board.ActionName.TREATDISEASE);
		EasyMock.verify(board.currentPlayer);
	}

	@Test
	public void testDrive() {
		board.currentPlayer = EasyMock.createMock(Player.class);
		board.currentPlayer.playerData = this.playerData;
		board.currentPlayer.playerData.role = Board.Roles.DISPATCHER;
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
		board.currentPlayer.playerData = this.playerData;
		board.currentPlayer.playerData.role = Board.Roles.DISPATCHER;
		board.currentPlayer.charterFlight();
		EasyMock.replay(board.currentPlayer);
		action.doAction(Board.ActionName.CHARTERFLIGHT);
		EasyMock.verify(board.currentPlayer);
	}

	@Test
	public void testShuttleFlight() {
		board.currentPlayer = EasyMock.createMock(Player.class);
		board.currentPlayer.playerData = this.playerData;
		board.currentPlayer.playerData.role = Board.Roles.DISPATCHER;
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
		board.currentPlayer.playerData = this.playerData;
		board.currentPlayer.playerData.role = Board.Roles.DISPATCHER;
		board.currentPlayer.buildStation();
		EasyMock.replay(board.currentPlayer);
		action.doAction(Board.ActionName.BUILDRESEARCH);
		EasyMock.verify(board.currentPlayer);
	}

	@Test
	public void testShareKnowledge() {
		board.currentPlayer = EasyMock.createMock(Player.class);
		board.currentPlayer.playerData = this.playerData;
		board.currentPlayer.playerData.role = Board.Roles.DISPATCHER;
		board.currentPlayer.shareKnowledge();
		EasyMock.replay(board.currentPlayer);
		action.doAction(Board.ActionName.SHAREKNOWLEDGE);
		EasyMock.verify(board.currentPlayer);
	}

	@Test
	public void testMedicChangeCityUseSpecialSkill() {
		board.currentPlayer = EasyMock.createMock(Player.class);
		board.currentPlayer.playerData = this.playerData;
		board.currentPlayer.playerData.specialSkill = EasyMock.createNiceMock(MedicAction.class);
		board.currentPlayer.playerData.role = Board.Roles.MEDIC;
		board.currentPlayer.playerData.specialSkill.useSpecialSkill();
		board.currentPlayer.charterFlight();
		EasyMock.replay(board.currentPlayer.playerData.specialSkill);
		action.doAction(Board.ActionName.CHARTERFLIGHT);
		EasyMock.verify(board.currentPlayer.playerData.specialSkill);
	}

	@Test
	public void testMedicNotChangeCityNotUseSpecialSkill() {
		board.currentPlayer = EasyMock.createMock(Player.class);
		board.currentPlayer.playerData = this.playerData;
		board.currentPlayer.playerData.specialSkill = EasyMock.createNiceMock(MedicAction.class);
		board.currentPlayer.playerData.role = Board.Roles.MEDIC;
		board.currentPlayer.buildStation();
		EasyMock.replay(board.currentPlayer, board.currentPlayer.playerData.specialSkill);
		action.doAction(Board.ActionName.BUILDRESEARCH);
		EasyMock.verify(board.currentPlayer, board.currentPlayer.playerData.specialSkill);
	}
}
