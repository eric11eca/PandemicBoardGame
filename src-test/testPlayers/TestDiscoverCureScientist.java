package testPlayers;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import cards.PlayerCard;
import data.Board;
import player.DiscoverCure;
import player.DiscoverCureScientist;

public class TestDiscoverCureScientist {
	Board board;
	DiscoverCure scientistDiscoverCure;
	String redCityName1, redCityName2, redCityName3, 
    		redCityName4, redCityName5;
	PlayerCard redCity1, redCity2, redCity3, 
        		redCity4, redCity5;
	
	@Before
	public void setup() {
		board = new Board();
		scientistDiscoverCure = new DiscoverCureScientist(board.curedDiseases);
		
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
	}
	
	@Test(expected = RuntimeException.class)
	public void testScientistLackCardSize() {
		ArrayList<PlayerCard> cards = new ArrayList<>();
		cards.add(redCity1);
		cards.add(redCity2);
		cards.add(redCity3);
		scientistDiscoverCure.discover(cards);
	}

	@Test(expected = RuntimeException.class)
	public void testScientistExceedCardSize() {
		ArrayList<PlayerCard> cards = new ArrayList<>();
		cards.add(redCity1);
		cards.add(redCity2);
		cards.add(redCity3);
		cards.add(redCity4);
		cards.add(redCity5);
		scientistDiscoverCure.discover(cards);
	}
}
