package TestInitializeBoard;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Initialize.Board;
import Initialize.City;
import Player.Medic;
import Player.Player;

public class TestShuttleFlight {
	Board board;
	Player player;
	City locationWithStation, locationNoStation;
	City destinationWithStation, destinationNoStation;

	@Before
	public void setup() {
		board = new Board();
		player = new Medic(board);
		String city1 = "Chicago";
		String city2 = "NewYork";
		String city3 = "Seattle";
		String city4 = "Miami";
		this.locationWithStation = new City(city1);
		this.locationWithStation.researchStation = true;

		this.locationNoStation = new City(city2);
		this.locationNoStation.researchStation = false;

		this.destinationWithStation = new City(city3);
		this.destinationWithStation.researchStation = true;

		this.destinationNoStation = new City(city4);
		this.destinationNoStation.researchStation = false;

	}

	@Test
	public void testStationToStation() {
		this.initialPlayerLocationHasStation(true);
		player.shuttleFlight(this.destinationWithStation);
		assertEquals(this.destinationWithStation.cityName, player.location.cityName);
		assertEquals(3, player.action);
	}

	@Test(expected = RuntimeException.class)
	public void testStationToNoStation() {
		initialPlayerLocationHasStation(true);
		player.shuttleFlight(this.destinationNoStation);
	}

	@Test(expected = RuntimeException.class)
	public void testNoStationToStation() {
		initialPlayerLocationHasStation(false);
		player.shuttleFlight(this.destinationNoStation);
	}

	@Test(expected = RuntimeException.class)
	public void testNoStationToNoStation() {
		initialPlayerLocationHasStation(false);
		player.shuttleFlight(this.destinationNoStation);
	}

	private void initialPlayerLocationHasStation(boolean hasStation) {
		if (hasStation) {
			player.location = this.locationWithStation;
		} else {
			player.location = this.locationNoStation;
		}
	}

}
