package TestPlayers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.Board;
import Initialize.City;
import Player.Player;
import Player.Scientist;

public class testScientist {
	Scientist scientist;
	Board board;
	@Before
	public void setup() {
		board = new Board();
		scientist = new Scientist(board);
	}

	@Test
	public void testDiscoverCure() {
		String cityName1 = "a";
		String cityName2 = "b";
		String cityName3 = "c";
		String cityName4 = "d";
		String cityName5 = "e";
		String cityName6 = "f";
		PlayerCard city1 = new PlayerCard(Board.CardType.CITYCARD, cityName1);
		PlayerCard city2 = new PlayerCard(Board.CardType.CITYCARD, cityName2);
		PlayerCard city3 = new PlayerCard(Board.CardType.CITYCARD, cityName3);
		PlayerCard city4 = new PlayerCard(Board.CardType.CITYCARD, cityName4);
		PlayerCard city5 = new PlayerCard(Board.CardType.CITYCARD, cityName5);
		PlayerCard city6 = new PlayerCard(Board.CardType.CITYCARD, cityName6);
		city1.color = "Yellow";
		city2.color = "Yellow";
		city3.color = "Yellow";
		city4.color = "Yellow";
		city5.color = "Red";
		city6.color = "Blue";
		scientist.hand.put(cityName1, city1);
		scientist.hand.put(cityName2, city2);
		scientist.hand.put(cityName3, city3);
		scientist.hand.put(cityName4, city4);
		scientist.hand.put(cityName5, city5);
		scientist.hand.put(cityName6, city6);
		scientist.discoverCure("Yellow");
		assertTrue(board.curedDiseases.contains(city1.color));
	}

}
