package TestPlayerCardAction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Card.GovernmentGrantEvent;
import Initialize.Board;
import Initialize.City;

public class TestGovernmentGrantEvent {
	Board board;
	GovernmentGrantEvent grant;
	String cityName = "NewYork";
	
	@Before
	public void setup() {
		board = new Board();
		grant = new GovernmentGrantEvent(board);
		City city = new City();
		city.cityName = cityName;
		board.cities.put(cityName, city);
	}

	@Test
	public void testAddResearchStation() {
		board.cityWithGrant = cityName;
		City city_old = board.cities.get(cityName);
		assertFalse(city_old.researchStation);
		grant.executeEvent();
		City city_new = board.cities.get(cityName);
		assertTrue(city_new.researchStation);
	}

}
