package TestGameAction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import Action.GameAction;
import Card.PlayerCard;
import Initialize.Board;
import Initialize.City;
import Player.Medic;
import Player.Player;

public class TestGameActionOneTurn {
	Board board;
	GameAction action;
	Player medic;
	String[] citynames = { "Chicago", "NewYork", "London", "Washington" };
	String[] handCardNames = { "city1", "city2", "city3", "city4", "city5", "city6" };

	@Before
	public void setup() {
		board = new Board();
		action = new GameAction(board);

		PlayerCard playercard = new PlayerCard(Board.CardType.CITYCARD, "Shanghai");
		board.validPlayerCard.add(playercard);

		medic = new Medic(board);
		board.currentPlayers.add(medic);
		board.currentPlayer = medic;
	}

	@Test
	public void testDrawTwoPlayerCards() {
		initializePlayerCard(citynames, true);
		assertEquals(0, medic.hand.size());
		action.drawTwoPlayerCards();
		assertEquals(2, medic.hand.size());
		assertTrue(medic.hand.containsKey("Chicago"));
		assertEquals(3, board.validPlayerCard.size());
	}

	private void initializePlayerCard(String[] nameList, boolean addToBoard) {
		for (String playercardName : nameList) {
			PlayerCard playercard = new PlayerCard(Board.CardType.CITYCARD, playercardName);
			if (addToBoard)
				board.validPlayerCard.add(playercard);
			else
				medic.hand.put(playercardName, playercard);
		}
	}

	@Test (expected = RuntimeException.class)
	public void testDrawTwoCityCardsWithPlayerExceedHandLimit() {
		initializePlayerCard(citynames, true);
		initializePlayerCard(handCardNames, false);
		action.drawTwoPlayerCards();
		assertEquals(7, medic.hand.size());
		assertEquals(3, board.validPlayerCard.size());
	}

	@Test
	public void testDrawOneCityCardAndOneEpidemicCard() {
		PlayerCard epidemicCard = new PlayerCard(Board.CardType.EPIDEMIC, "EPIDEMIC");
		board.validPlayerCard.add(epidemicCard);
		String infectCityName = "Infect";
		board.validInfectionCard.add(infectCityName);
		City infectCity = new City(infectCityName);
		infectCity.color = "BLUE";
		infectCity.diseaseCubes.put("BLUE", 1);
		board.cities.put(infectCityName, infectCity);
		board.remainDiseaseCube.put("BLUE", 13);
		action.drawTwoPlayerCards();
		assertEquals(1, medic.hand.size());
	}
	
	@Test(expected = RuntimeException.class)
	public void testLackOfPlayerCards() {
		action.drawTwoPlayerCards();
	}
}
