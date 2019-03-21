package TestInitializeBoard;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.*;

public class TestInitializeBoard {
	InitializeBoard initializeBoard;
	Board board;
	ThreadLocalRandom random;
	@Before
	public void setup(){
		board = new Board();
		initializeBoard = new InitializeBoard(board);
		random = EasyMock.mock(ThreadLocalRandom.class);
	}
	
	@Test
	public void testInitializeCityWithCityData(){
		initializeBoard.initializeWithCityData();
		Map<String, City> cities = initializeBoard.board.cities;
		assertEquals(48, cities.size());
		City chicago = cities.get("Chicago");
		assertEquals("Chicago", chicago.cityName);
		assertEquals("BLUE", chicago.color);
	}
	
	@Test
	public void testInitializeCity(){
		String cityName = "Shanghai";
		initializeBoard.initializeCity(cityName, "BLUE", 12, 30, 40);
		Map<String, City> cities = initializeBoard.board.cities;
		City shanghai = cities.get(cityName);
		assertEquals("Shanghai", shanghai.cityName);
		assertEquals("BLUE", shanghai.color);
		assertEquals(12, shanghai.population);
		assertEquals(30, shanghai.x);
		assertEquals(40, shanghai.y);
	}
	
	@Test
	public void testInitializeInfectionCard() {
		String cityName = "Chicago";
		initializeBoard.initializeInfectionCard(cityName);
		List<String> valid_infection_card = initializeBoard.board.validInfectionCard;
		assertTrue(valid_infection_card.contains(cityName));
	}
	
	@Test
	public void testInitializePlayerCard() {
		initializeBoard.initializePlayerCard(Board.CardType.CITYCARD, "Chicago");
		List<PlayerCard> valid_playercard = initializeBoard.board.validPlayerCard;
		assertEquals(Board.CardType.CITYCARD, valid_playercard.get(0).cardType);
		assertEquals("Chicago", valid_playercard.get(0).cardName);
	}
}
