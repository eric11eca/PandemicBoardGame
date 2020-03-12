package testPlayerCardAction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cards.GovernmentGrant;
import data.Board;
import data.City;

public class TestGovernmentGrantEvent {
	Board board;
	GovernmentGrant grant;
	String cityName = "NewYork";
	
	@Before
	public void setup() {
		Board.setNull();
		board = Board.getInstance();
		grant = new GovernmentGrant(board);
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
