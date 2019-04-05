package TestPlayerCommonActions;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.Board;
import Initialize.City;
import Player.Medic;
import Player.Player;

public class TestCharterFlight {
	Board board;
	Player player;
	PlayerCard locationCityCard, notLocationCityCard, eventCard;
	City chicagoCity, newyorkCity, seattleCity, miamiCity;

	@Before
	public void setup() {
		board = new Board();
		player = new Medic(board);
		String chicago = "Chicago";
		chicagoCity = new City(chicago);
		String newyork = "NewYork";
		newyorkCity = new City(newyork);
		String seattle = "Seattle";
		seattleCity = new City(seattle);
		String miami = "Miami";
		miamiCity = new City(miami);
		board.cities.put(chicago, chicagoCity);
		board.cities.put(newyork, newyorkCity);
		board.cities.put(seattle, seattleCity);
		board.cities.put(miami, miamiCity);
		player.location = chicagoCity;
		locationCityCard = new PlayerCard(Board.CardType.CITYCARD, chicago);
		notLocationCityCard = new PlayerCard(Board.CardType.CITYCARD, newyork);
		eventCard = new PlayerCard(Board.CardType.EVENTCARD, "");
		player.hand.put(locationCityCard.cardName,locationCityCard);
		player.hand.put(notLocationCityCard.cardName,notLocationCityCard);
		player.hand.put(eventCard.cardName,eventCard);
	}

	@Test
	public void testValidConsumeAction1() {
		player.consumeAction();
		assertEquals(3, player.action);
	}

	@Test
	public void testConsumeAction() {
		player.consumeAction();
		player.consumeAction();
		player.consumeAction();
		player.consumeAction();
		assertEquals(0, player.action);
	}

	@Test(expected = RuntimeException.class)
	public void testInvalidConsumeAction() {
		player.consumeAction();
		player.consumeAction();
		player.consumeAction();
		player.consumeAction();
		player.consumeAction();
	}

	@Test
	public void testRandomDestination0() {
		Random random = EasyMock.createMock(Random.class);
		board.cities = EasyMock.createMock(HashMap.class);
		player = new Medic(board, random);
		EasyMock.expect(random.nextInt(4)).andReturn(0);
		ArrayList<City> cities = new ArrayList<City>();
		addCities(cities);
		EasyMock.expect(board.cities.values()).andReturn(cities);
		EasyMock.replay(board.cities, random);
		assertEquals("Chicago", player.randomDestination().cityName);
	}

	private void addCities(ArrayList<City> cities) {
		cities.add(chicagoCity);
		cities.add(newyorkCity);
		cities.add(seattleCity);
		cities.add(miamiCity);
	}

	@Test
	public void testRandomDestinationEnd() {
		Random random = EasyMock.createMock(Random.class);
		board.cities = EasyMock.createMock(HashMap.class);
		player = new Medic(board, random);
		EasyMock.expect(random.nextInt(4)).andReturn(3);
		ArrayList<City> cities = new ArrayList<City>();
		addCities(cities);
		EasyMock.expect(board.cities.values()).andReturn(cities);
		EasyMock.replay(board.cities, random);
		assertEquals("Miami", player.randomDestination().cityName);
	}

	@Test
	public void testRandomDestination2() {
		Random random = EasyMock.createMock(Random.class);
		board.cities = EasyMock.createMock(HashMap.class);
		player = new Medic(board, random);
		EasyMock.expect(random.nextInt(4)).andReturn(1);
		ArrayList<City> cities = new ArrayList<City>();
		addCities(cities);
		EasyMock.expect(board.cities.values()).andReturn(cities);
		EasyMock.replay(board.cities, random);
		assertEquals("NewYork", player.randomDestination().cityName);
	}

	@Test
	public void testSuccessCharterFlight() {
		Player mockRandom = EasyMock.partialMockBuilder(Player.class).addMockedMethod("randomDestination")
				.withConstructor(board).createMock();
		EasyMock.expect(mockRandom.randomDestination()).andReturn(newyorkCity);
		EasyMock.replay(mockRandom);
		mockRandom.location = chicagoCity;
		mockRandom.action = 4;
		mockRandom.hand.put(locationCityCard.cardName,locationCityCard);
		mockRandom.hand.put(notLocationCityCard.cardName,notLocationCityCard);
		mockRandom.hand.put(eventCard.cardName,eventCard);
		mockRandom.characterFlight(locationCityCard);
		assertEquals("NewYork", mockRandom.location.cityName);
		assertEquals(2, mockRandom.hand.size());
		assertEquals(3, mockRandom.action);
	}

	@Test
	public void testSameDestinationAndLocation() {
		Player mockRandom = EasyMock.partialMockBuilder(Player.class).addMockedMethod("randomDestination")
				.withConstructor(board).createMock();
		EasyMock.expect(mockRandom.randomDestination()).andReturn(chicagoCity);
		EasyMock.replay(mockRandom);
		mockRandom.location = chicagoCity;
		mockRandom.characterFlight(locationCityCard);
		assertFalse("Chicago".equals(mockRandom.location.cityName));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithWrongCityCard() {
		player.characterFlight(notLocationCityCard);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithInvalidPlayerCard() {
		player.characterFlight(eventCard);
	}
}
