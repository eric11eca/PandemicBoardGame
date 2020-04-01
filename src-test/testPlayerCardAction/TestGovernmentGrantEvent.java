package testPlayerCardAction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cards.GovernmentGrant;
import data.Board;
import data.CityData;
import data.GameColor;
import game.city.City;
import helpers.TestCityFactory;

public class TestGovernmentGrantEvent {
	Board board;
	GovernmentGrant grant;
	String cityName = "NewYork";
	TestCityFactory cityFactory = new TestCityFactory();

	@Before
	public void setup() {
		board = new Board();
		grant = new GovernmentGrant(board);
		City city = cityFactory.makeFakeCity(cityName);
		board.cities.put(cityName, city);
	}

	@Test
	public void testAddResearchStation() {
		board.cityWithGrant = cityName;
		City city_old = board.cities.get(cityName);
		assertFalse(city_old.hasResearchStation());
		grant.executeEvent();
		City city_new = board.cities.get(cityName);
		assertTrue(city_new.hasResearchStation());
	}

}
