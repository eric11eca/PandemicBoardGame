package testPlayerCardAction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cards.GovernmentGrant;
import data.Board;
import data.CityData;
import data.CityOLD;
import data.GameColor;

public class TestGovernmentGrantEvent {
	Board board;
	GovernmentGrant grant;
	String cityName = "NewYork";

	@Before
	public void setup() {
		board = new Board();
		grant = new GovernmentGrant(board);
		CityOLD city = new CityOLD(new CityData(cityName, GameColor.RED, 10), 0, 0);
		board.cities.put(cityName, city);
	}

	@Test
	public void testAddResearchStation() {
		board.cityWithGrant = cityName;
		CityOLD city_old = board.cities.get(cityName);
		assertFalse(city_old.researchStation);
		grant.executeEvent();
		CityOLD city_new = board.cities.get(cityName);
		assertTrue(city_new.researchStation);
	}

}
