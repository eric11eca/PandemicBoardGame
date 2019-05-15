package testPlayers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import cards.PlayerCard;
import data.Board;
import player.DiscoverCure;
import player.DiscoverCureNormal;
import player.DiscoverCureScientist;

public class TestDiscoverCure {
	Board board;	
	DiscoverCure medicDiscoverCure, scientistDiscoverCure;
	String yellowCityName1, redCityName1, redCityName2, redCityName3, 
	       redCityName4, redCityName5, eventName, blueCityName1;
	PlayerCard yellowCity1, redCity1, redCity2, redCity3, 
	           redCity4, redCity5, event, blueCity1;

	@Before
	public void setup() {
		board = new Board();
		
		medicDiscoverCure = new DiscoverCureNormal(board.curedDiseases);
		scientistDiscoverCure = new DiscoverCureScientist(board.curedDiseases);
		
		yellowCityName1 = "yellowCity1";
		redCityName1 = "redCity1";
		redCityName2 = "redCity2";
		redCityName3 = "redCity3";
		redCityName4 = "redCity4";
		redCityName5 = "redCity5";
		eventName = "event";
		blueCityName1 = "blueCity1";

		yellowCity1 = new PlayerCard(Board.CardType.CITYCARD, yellowCityName1);
		redCity1 = new PlayerCard(Board.CardType.CITYCARD, redCityName1);
		redCity2 = new PlayerCard(Board.CardType.CITYCARD, redCityName2);
		redCity3 = new PlayerCard(Board.CardType.CITYCARD, redCityName3);
		redCity4 = new PlayerCard(Board.CardType.CITYCARD, redCityName4);
		redCity5 = new PlayerCard(Board.CardType.CITYCARD, redCityName5);
		event = new PlayerCard(Board.CardType.EVENTCARD, eventName);
		blueCity1 = new PlayerCard(Board.CardType.CITYCARD, blueCityName1);

		yellowCity1.color = "YELLOW";
		redCity1.color = "RED";
		redCity2.color = "RED";
		redCity3.color = "RED";
		redCity4.color = "RED";
		redCity5.color = "RED";
		blueCity1.color = "BLUE";
	}

	@Test
	public void testDiscoverCureRegular() {
		ArrayList<PlayerCard> cards = new ArrayList<>();
		cards.add(redCity1);
		cards.add(redCity2);
		cards.add(redCity3);
		cards.add(redCity4);
		cards.add(redCity5);
		medicDiscoverCure.discoverCure(cards);
		assertTrue(board.curedDiseases.contains("RED"));
	}

	@Test
	public void testDiscoverDiscoveredCure() {
		board.curedDiseases.add("RED");
		ArrayList<PlayerCard> cards = new ArrayList<>();
		cards.add(redCity1);
		cards.add(redCity2);
		cards.add(redCity3);
		cards.add(redCity4);
		cards.add(redCity5);
		assertFalse(medicDiscoverCure.discoverCure(cards));
	}

	@Test(expected = RuntimeException.class)
	public void testNormalLackCardSize() {
		ArrayList<PlayerCard> cards = new ArrayList<>();
		cards.add(redCity1);
		cards.add(redCity2);
		cards.add(redCity3);
		cards.add(redCity4);
		medicDiscoverCure.discoverCure(cards);
	}

	@Test(expected = RuntimeException.class)
	public void testNormalExceedCardSize() {
		ArrayList<PlayerCard> cards = new ArrayList<>();
		cards.add(yellowCity1);
		cards.add(redCity1);
		cards.add(redCity2);
		cards.add(redCity3);
		cards.add(redCity4);
		cards.add(redCity5);
		medicDiscoverCure.discoverCure(cards);
	}

	@Test(expected = RuntimeException.class)
	public void testScientistLackCardSize() {
		ArrayList<PlayerCard> cards = new ArrayList<>();
		cards.add(redCity1);
		cards.add(redCity2);
		cards.add(redCity3);
		scientistDiscoverCure.discoverCure(cards);
	}

	@Test(expected = RuntimeException.class)
	public void testScientistExceedCardSize() {
		ArrayList<PlayerCard> cards = new ArrayList<>();
		cards.add(redCity1);
		cards.add(redCity2);
		cards.add(redCity3);
		cards.add(redCity4);
		cards.add(redCity5);
		scientistDiscoverCure.discoverCure(cards);
	}

	@Test
	public void testValidCardType() {
		ArrayList<PlayerCard> cards = new ArrayList<>();
		cards.add(redCity1);
		cards.add(redCity2);
		cards.add(redCity3);
		cards.add(redCity4);
		cards.add(redCity5);
		assertTrue(medicDiscoverCure.validCardType(cards));
	}

	@Test
	public void testInvalidCardType() {
		ArrayList<PlayerCard> cards = new ArrayList<>();
		cards.add(redCity1);
		cards.add(redCity2);
		cards.add(redCity3);
		cards.add(redCity4);
		cards.add(event);
		assertFalse(medicDiscoverCure.validCardType(cards));
	}

	@Test
	public void testValidCardsColor() {
		ArrayList<PlayerCard> cards = new ArrayList<>();
		cards.add(redCity1);
		cards.add(redCity2);
		cards.add(redCity3);
		cards.add(redCity4);
		cards.add(redCity5);
		assertTrue(medicDiscoverCure.checkCureColor(cards));
	}

	@Test
	public void testInvalidCardsColor() {
		ArrayList<PlayerCard> cards = new ArrayList<>();
		cards.add(redCity1);
		cards.add(redCity2);
		cards.add(redCity3);
		cards.add(redCity4);
		cards.add(yellowCity1);
		assertFalse(medicDiscoverCure.checkCureColor(cards));
	}

}
