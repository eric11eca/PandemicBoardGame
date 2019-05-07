package TestPlayerCardAction;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Card.AirliftEvent;
import Initialize.Board;
import Initialize.City;
import Player.Player;
import Player.PlayerData;


public class TestAirliftEvent {
	Board board;
	AirliftEvent airlift;
	Player player;
	PlayerData playerData;
	
	@Before 
	public void setup() {
		board = new Board();
		airlift = new AirliftEvent(board);
		player = new Player(board, new PlayerData());	
		City city = new City();
		city.cityName = "Atlanta";
		player.playerData.location = city;
		board.currentPlayers.add(player);
		board.currentPlayer = player;
	}
	
	@Test
	public void testAirlift() {
		board.idxofPlayerAirlift = 0;
		board.nameofCityAirlift = "Chicago";
		City city1 = new City();
		City city2 = new City();
		
		city1.cityName = "Atlanta";
		city2.cityName = "Chicago";
		
		board.cities.put("Atlanta", city1);
		board.cities.put("Chicago", city2);
		
		assertEquals("Atlanta", board.currentPlayer.playerData.location.cityName);	
		
		airlift.executeEvent();
		assertEquals("Chicago", board.currentPlayer.playerData.location.cityName);
	}

}
