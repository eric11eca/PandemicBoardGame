package TestPlayerCardAction;

import static org.junit.Assert.*;

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
		playerData = new PlayerData();	
		City city = new City();
		city.cityName = "Atlanta";
		playerData.location = city;
		player = new Player(board, playerData);
		board.currentPlayers.add(player);
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
		assertEquals("Atlanta", playerData.location.cityName);	
		
		airlift.executeEvent();
		assertEquals("Chicago", playerData.location.cityName);
	}

}
