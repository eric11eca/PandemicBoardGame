package testPlayerCommonActions;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cardActions.EventCardAction;
import cards.PlayerCard;
import data.Board;
import data.City;
import player.Player;
import player.PlayerData;

public class TestDrive {
	Board board;
	Player player;
	PlayerData playerData;
	EventCardAction eventCardAction;
	PlayerCard cityCard1;
	PlayerCard cityCard2;
	City location, neighborCity, notNeighborCity;

	@Before
	public void setup() {
		board = new Board();
		playerData = new PlayerData();
		
		location = new City();
		location.cityName = "Chicago";

		neighborCity = new City();
		neighborCity.cityName = "Atlanta";

		location.neighbors.put("Atlanta", neighborCity);

		notNeighborCity = new City();
		notNeighborCity.cityName = "Shanghai";

		playerData.location = location;
		playerData.action = 4;
		
		eventCardAction = new EventCardAction(board);
		player = new Player(board, playerData);
	}

	@Test
	public void testNeighborDrive() {
		player.drive(neighborCity);
		assertEquals(3, playerData.action);
		assertEquals(playerData.location.cityName, "Atlanta");
	}

}
