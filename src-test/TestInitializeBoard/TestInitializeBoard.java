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
		initializeBoard.initializeWithCityData();
		Map<String, City> cities = initializeBoard.board.cities;
		assertEquals(48, cities.size());
		City chicago = cities.get("Chicago");
		assertEquals("Chicago", chicago.cityName);
		assertEquals("BLUE", chicago.color);
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
