import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
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
	public void testInitializeCity(){
		initializeBoard.initializeWithCityData();
		Map<String, City> cities = initializeBoard.board.cities;
		assertEquals(48, cities.size());
		City chicago = cities.get("Chicago");
		assertEquals("Chicago", chicago.cityName);
		assertEquals("BLUE", chicago.color);
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
	public void testInitializeEpiDemicCard1() {
		ThreadLocalRandom random = EasyMock.mock(ThreadLocalRandom.class);
		EasyMock.expect(random.nextInt(0, 7)).andReturn(43);
		EasyMock.expect(random.nextInt(7, 14)).andReturn(43);
		EasyMock.expect(random.nextInt(14, 21)).andReturn(43);
		EasyMock.expect(random.nextInt(21, 28)).andReturn(43);
		EasyMock.expect(random.nextInt(28, 35)).andReturn(43);
		EasyMock.expect(random.nextInt(35, 45)).andReturn(43);

		EasyMock.replay(random);
		initializeBoard.random = random;

		List<PlayerCard> validPlayerCard = initializeBoard.board.validPlayerCard;
		for (int i = 0; i < 48; i++) {
			validPlayerCard.add(new PlayerCard(Board.CardType.CITYCARD, ""));
		}
		
		for (int i = 0; i < 5; i++) {
			validPlayerCard.add(new PlayerCard(Board.CardType.EVENTCARD, ""));
		}
		
		initializeBoard.initializeEpidemicCard(45, 6);
		
		Collections.shuffle(validPlayerCard);
		assertEquals(Board.CardType.EPIDEMIC, validPlayerCard.get(47).cardType);
		
		EasyMock.verify(random);
	}
}
