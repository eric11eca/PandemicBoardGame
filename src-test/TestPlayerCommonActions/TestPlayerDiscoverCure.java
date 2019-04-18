package TestPlayerCommonActions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.Board;
import Initialize.City;
import Player.Medic;
import Player.Player;

public class TestPlayerDiscoverCure {
	Board board;
	Player medic;
	String redCityName1, redCityName2, redCityName3, redCityName4, redCityName5;
	PlayerCard redCity1, redCity2, redCity3, redCity4, redCity5;
	ArrayList<PlayerCard> cards;

	@Before
	public void setup() {
		board = new Board();
		medic = new Medic(board);
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

		medic.hand.put(redCityName1, redCity1);
		medic.hand.put(redCityName2, redCity2);
		medic.hand.put(redCityName3, redCity3);
		medic.hand.put(redCityName4, redCity4);
		medic.hand.put(redCityName5, redCity5);

		cards = new ArrayList<PlayerCard>();
		cards.add(redCity1);
		cards.add(redCity2);
		cards.add(redCity3);
		cards.add(redCity4);
		cards.add(redCity5);
	}

	@Test
	public void testPlayerdiscardCardWhenDiscoverCure() {
		medic.location = new City();
		medic.location.researchStation = true;
		medic.discoverCure(cards);
		assertTrue(board.curedDiseases.contains("RED"));
		assertEquals(0, medic.hand.size());
		assertEquals(3, medic.action);
	}

	@Test(expected = RuntimeException.class)
	public void testNotAtResearchStation() {
		medic.location = new City();
		medic.location.researchStation = false;
		medic.discoverCure(cards);
	}

	@Test
	public void testDiscoverDiscoveredCure() {
		medic.location = new City();
		medic.location.researchStation = true;
		board.curedDiseases.add("RED");
		medic.discoverCure(cards);
		assertEquals(5, medic.hand.size());
	}
	
	@Test
	public void testWinGameAfterDiscoverAllCures() {
		medic.location = new City();
		medic.location.researchStation = true;
		board.curedDiseases.add("BLUE");
		board.curedDiseases.add("BLACK");
		board.curedDiseases.add("YELLOW");
		medic.discoverCure(cards);
		assertEquals(4, board.curedDiseases.size());
		assertTrue(board.gameEnd);
		assertTrue(board.playerWin);
	}
}
