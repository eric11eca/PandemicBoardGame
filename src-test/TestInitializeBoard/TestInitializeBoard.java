package TestInitializeBoard;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.*;

public class TestInitializeBoard {
	InitializeBoard initializeBoard;
	Board board;
	
	@Before
	public void setup(){
		board = new Board();
		initializeBoard = new InitializeBoard(board);
	}
	
	@Test
	public void testInitializeCityWithCityData(){
	    initializeBoard.cityDataPath = "testCityData";
		initializeBoard.initializeWithCityData();
		Map<String, City> cities = initializeBoard.board.cities;
		assertEquals(5, cities.size());
		City chicago = cities.get("Chicago");
		assertEquals("Chicago", chicago.cityName);
		assertEquals("BLUE", chicago.color);
		assertEquals(2716000,chicago.population);
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
	
	@Test
	public void testShuffleCards() {
		initializeBoard.board.validPlayerCard.add(new PlayerCard(Board.CardType.CITYCARD, "city1"));
		initializeBoard.board.validPlayerCard.add(new PlayerCard(Board.CardType.CITYCARD, "city2"));
		initializeBoard.board.validPlayerCard.add(new PlayerCard(Board.CardType.CITYCARD, "city3"));
		initializeBoard.board.validPlayerCard.add(new PlayerCard(Board.CardType.EVENTCARD, "event1"));
		initializeBoard.board.validPlayerCard.add(new PlayerCard(Board.CardType.EVENTCARD, "event2"));
		
		initializeBoard.board.validInfectionCard.add("city1");
		initializeBoard.board.validInfectionCard.add("city2");
		initializeBoard.board.validInfectionCard.add("city3");
		initializeBoard.board.validInfectionCard.add("city4");
		initializeBoard.board.validInfectionCard.add("city5");
		
		String oldInfectionCards = initializeBoard.board.validInfectionCard.toString();
		String oldPlayerCards = initializeBoard.board.validPlayerCard.toString();
		
		initializeBoard.shuffleCards();	
		
		String newInfectionCards = initializeBoard.board.validInfectionCard.toString();
		String newPlayerCards = initializeBoard.board.validPlayerCard.toString();
		
		assertFalse(newInfectionCards == oldInfectionCards);
		assertFalse(newPlayerCards == oldPlayerCards);
	}
	
	@Test
	public void testInitializeEventCards() {
		initializeBoard.eventCardNames.add("event1");
		initializeBoard.eventCardNames.add("event2");
		initializeBoard.eventCardNames.add("event3");
		
		initializeBoard.initializeEventCard();
		String firstEventCardName = initializeBoard.board.validPlayerCard.get(0).cardName;
		
		assertEquals("event1", firstEventCardName);
	}
}
