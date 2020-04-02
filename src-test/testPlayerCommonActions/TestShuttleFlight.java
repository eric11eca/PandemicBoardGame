package testPlayerCommonActions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cardActions.EventCardAction;
import data.Board;
import game.city.City;
import helpers.TestCityFactory;
import player.Player;
import player.PlayerData;

public class TestShuttleFlight {
	Board board;
	Player player;
	PlayerData playerData;
	EventCardAction eventCardAction;
	City locationWithStation, locationNoStation;
	City destinationWithStation, destinationNoStation;

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
		locationWithStation.buildResearchStation();

		locationNoStation = cityFactory.makeFakeCity(city2);
		locationNoStation.removeResearchStation();

		destinationWithStation = cityFactory.makeFakeCity(city3);
		destinationWithStation.buildResearchStation();

		destinationNoStation = cityFactory.makeFakeCity(city4);
		destinationNoStation.removeResearchStation();

		eventCardAction = new EventCardAction(board);
		player = new Player(board, playerData);
	}

	@Test
	public void testStationToStation() {
		this.initialPlayerLocationHasStation(true);
		player.shuttleFlight(this.destinationWithStation);
		assertEquals(this.destinationWithStation.getName(), playerData.location.getName());
		assertEquals(3, playerData.action);
	}

	private void initialPlayerLocationHasStation(boolean hasStation) {
		if (hasStation) {
			playerData.location = this.locationWithStation;
		} else {
			playerData.location = this.locationNoStation;
		}
	}

}
