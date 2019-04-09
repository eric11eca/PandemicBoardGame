package TestPlayerCommonActions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import Card.PlayerCard;
import Initialize.Board;
import Player.Medic;

public class TestPlayerDiscoverCure {

	@Test
	public void testPlayerdiscardCardWhenDiscoverCure() {
		Board board = new Board();
		Medic medic = new Medic(board);
		String redCityName1 = "redCity1";
		String redCityName2 = "redCity1";
		String redCityName3 = "redCity1";
		String redCityName4 = "redCity1";
		String redCityName5 = "redCity1";

		PlayerCard redCity1 = new PlayerCard(Board.CardType.CITYCARD, redCityName1);
		PlayerCard redCity2 = new PlayerCard(Board.CardType.CITYCARD, redCityName2);
		PlayerCard redCity3 = new PlayerCard(Board.CardType.CITYCARD, redCityName3);
		PlayerCard redCity4 = new PlayerCard(Board.CardType.CITYCARD, redCityName4);
		PlayerCard redCity5 = new PlayerCard(Board.CardType.CITYCARD, redCityName5);

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

		ArrayList<PlayerCard> cards = new ArrayList<PlayerCard>();
		cards.add(redCity1);
		cards.add(redCity2);
		cards.add(redCity3);
		cards.add(redCity4);
		cards.add(redCity5);

		medic.discoverCure(cards);
		assertEquals(0, medic.hand);
	}

	@Test
	public void testDiscoverDiscoveredCure() {
		fail();
	}
}
