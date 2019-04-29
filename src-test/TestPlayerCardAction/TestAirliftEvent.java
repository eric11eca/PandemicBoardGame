package TestPlayerCardAction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Card.AirliftEvent;
import Initialize.Board;
import Initialize.City;
import Player.Medic;
import Player.Player;


public class TestAirliftEvent {
	Board board;
	AirliftEvent airlift;
	Player player;
	
	@Before 
	public void setup() {
		board = new Board();
		airlift = new AirliftEvent(board);
		player = new Medic(board);
		City city = new City();
		city.cityName = "Atlanta";
		player.location = city;
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

		int index = board.idxofPlayerAirlift;
		Player player_old = board.currentPlayers.get(index);
		assertEquals("Atlanta", player_old.location.cityName);
		
		airlift.airlift();
		Player player_new = board.currentPlayers.get(index);
		assertEquals("Chicago", player_new.location.cityName);
	}

}
