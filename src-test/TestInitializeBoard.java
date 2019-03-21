import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.easymock.EasyMock;
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
		// TODO: Generate Data to the CityData
		Map<String, City> cities = initializeBoard.board.cities;
		assertEquals(48, cities.size());
		City chicago = cities.get("Chicago");
		assertEquals("Chicago", chicago.cityName);
		assertEquals("BLUE", chicago.color);
	}
	
	@Test
	public void testInitializeHandCardWithTwoPlayer(){
		for(int i = 0; i < board.playernumber; i++){
			for(int j = 0; j < board.initialhandcard; j++){
				board.currentPlayers.get(i).hand.add(); // the top card
			}
		}
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
	public void testInitializeEpiDemicCard() {
		ThreadLocalRandom random = EasyMock.mock(ThreadLocalRandom.class);
		EasyMock.expect(random.nextInt()).andReturn(5);
		EasyMock.replay(random);
		initializeBoard.random = random;
		EasyMock.verify(random);
		List<PlayerCard> valid_playercard = initializeBoard.board.validPlayerCard;
		assertEquals(Board.CardType.EPIDEMIC, valid_playercard.get(5).cardType);
	}
}
