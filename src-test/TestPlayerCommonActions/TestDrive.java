package TestPlayerCommonActions;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.Board;
import Initialize.City;
import Player.Medic;
import Player.Player;

public class TestDrive {
	Player player;
	Board board;
	PlayerCard cityCard1;
	PlayerCard cityCard2;
	City location, neighborCity, notNeighborCity;

	@Before
	public void setup() {
		board = new Board();
		player = new Medic(board);
		
		location = new City();
		location.cityName = "Chicago";

		neighborCity = new City();
		neighborCity.cityName = "Atlanta";

		location.neighbors.add(neighborCity);

		notNeighborCity = new City();
		notNeighborCity.cityName = "Shanghai";

		player.location = location;

	}

	@Test
	public void testNeighborDrive() {
		player.drive(neighborCity);
		assertEquals(player.location.cityName, "Atlanta");
	}

	@Test(expected = RuntimeException.class)
	public void testNotNeighborDrive() {
		player.drive(notNeighborCity);
	}
}
