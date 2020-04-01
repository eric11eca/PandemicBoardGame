package testPlayerCommonActions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cardActions.EventCardAction;
import data.Board;
import data.City;
import player.Player;
import player.PlayerData;

public class TestShuttleFlight {
	Board board;
	Player player;
	PlayerData playerData;
	EventCardAction eventCardAction;
	City locationWithStation, locationNoStation;
	City destinationWithStation, destinationNoStation;

	@Before
	public void setup() {
		board = new Board();
		playerData = new PlayerData();
		String city1 = "Chicago";
		String city2 = "NewYork";
		String city3 = "Seattle";
		String city4 = "Miami";
		locationWithStation = new City(city1);
		locationWithStation.researchStation = true;

		locationNoStation = new City(city2);
		locationNoStation.researchStation = false;

		destinationWithStation = new City(city3);
		destinationWithStation.researchStation = true;

		destinationNoStation = new City(city4);
		destinationNoStation.researchStation = false;
		
		eventCardAction = new EventCardAction(board);
		player = new Player(board, playerData);
	}

	@Test
	public void testStationToStation() {
		initialPlayerLocationHasStation(true);
		player.destination = destinationWithStation;
		player.getPlayerAction(Board.ActionName.SHUTTLEFLIGHT).executeAction();
		assertEquals(destinationWithStation.cityName, playerData.location.cityName);
		assertEquals(3, playerData.action);
	}

	private void initialPlayerLocationHasStation(boolean hasStation) {
		if (hasStation) {
			playerData.location = locationWithStation;
		} else {
			playerData.location = locationNoStation;
		}
	}

}
