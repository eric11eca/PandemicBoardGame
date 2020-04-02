package testPlayerCommonActions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cardActions.EventCardAction;
import data.Board;
<<<<<<< HEAD
import game.city.City;
=======
import data.CityData;
import game.City;
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
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
<<<<<<< HEAD
		locationWithStation = cityFactory.makeFakeCity(city1);
		locationWithStation.buildResearchStation();

		locationNoStation = cityFactory.makeFakeCity(city2);
		locationNoStation.removeResearchStation();

		destinationWithStation = cityFactory.makeFakeCity(city3);
		destinationWithStation.buildResearchStation();

		destinationNoStation = cityFactory.makeFakeCity(city4);
		destinationNoStation.removeResearchStation();
=======
		
		CityData data1 = new CityData(city1, null, 0);
		CityData data2 = new CityData(city2, null, 0);
		CityData data3 = new CityData(city3, null, 0);
		CityData data4 = new CityData(city4, null, 0);
		
		this.locationWithStation = new City(data1, 0, 0);
		this.locationWithStation.researchStation = true;

		this.locationNoStation = new City(data2, 0, 0);
		this.locationNoStation.researchStation = false;

		this.destinationWithStation = new City(data3, 0, 0);
		this.destinationWithStation.researchStation = true;

		this.destinationNoStation = new City(data4, 0, 0);
		this.destinationNoStation.researchStation = false;
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4

		eventCardAction = new EventCardAction(board);
		player = new Player(board, playerData);
	}

	@Test
	public void testStationToStation() {
		this.initialPlayerLocationHasStation(true);
		player.shuttleFlight(this.destinationWithStation);
		assertEquals(this.destinationWithStation.data.getCityName(), playerData.location.data.getCityName());
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
