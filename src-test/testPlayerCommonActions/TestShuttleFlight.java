package testPlayerCommonActions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cardActions.EventCardAction;
import data.Board;
import data.CityOLD;
import helpers.TestCityFactory;
import player.Player;
import player.PlayerData;

public class TestShuttleFlight {
	Board board;
	Player player;
	PlayerData playerData;
	EventCardAction eventCardAction;
	CityOLD locationWithStation, locationNoStation;
	CityOLD destinationWithStation, destinationNoStation;

	TestCityFactory cityFactory = new TestCityFactory();

	@Before
	public void setup() {
		board = new Board();
		playerData = new PlayerData();
		String city1 = "Chicago";
		String city2 = "NewYork";
		String city3 = "Seattle";
		String city4 = "Miami";
		locationWithStation = cityFactory.makeFakeCity(city1);
		locationWithStation.researchStation = true;

		locationNoStation = cityFactory.makeFakeCity(city2);
		locationNoStation.researchStation = false;

		destinationWithStation = cityFactory.makeFakeCity(city3);
		destinationWithStation.researchStation = true;

		destinationNoStation = cityFactory.makeFakeCity(city4);
		destinationNoStation.researchStation = false;

		eventCardAction = new EventCardAction(board);
		player = new Player(board, playerData);
	}

	@Test
	public void testStationToStation() {
		initialPlayerLocationHasStation(true);
		player.destination = destinationWithStation;
		player.getPlayerAction(Board.ActionName.SHUTTLEFLIGHT).executeAction();
		assertEquals(destinationWithStation.getName(), playerData.location.getName());
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
