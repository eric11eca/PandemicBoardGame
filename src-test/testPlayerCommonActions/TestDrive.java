package testPlayerCommonActions;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cardActions.EventCardAction;
import cards.PlayerCard;
import data.Board;
import game.city.City;
import helpers.TestCityFactory;
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

	TestCityFactory cityFactory = new TestCityFactory();

	@Before
	public void setup() {
		board = new Board();
		playerData = new PlayerData();
		
		location = cityFactory.makeFakeCity("Chicago");

		neighborCity = cityFactory.makeFakeCity("Atlanta");

		location.neighbors.add(neighborCity);

		notNeighborCity = cityFactory.makeFakeCity("Shanghai");

		playerData.location = location;
		playerData.action = 4;
		
		eventCardAction = new EventCardAction(board);
		player = new Player(board, playerData);
	}

	@Test
	public void testNeighborDrive() {
		player.drive(neighborCity);
		assertEquals(3, playerData.action);
		assertEquals(playerData.location.getName(), "Atlanta");
	}

}
