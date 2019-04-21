package TestPlayers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import Player.OperationsExpert;
import Player.Player;
import Player.StationBuilder;
import Player.StationBuilderNormal;

public class TestBuildStation {

	Player medic, operationsExpert;
	Board board;
	City playerLocatedCity, cityWithStation, playerNotLocatedCity;
	String playerLocation, notPlayerLocation, theNameOfCityWithStation;
	String city1, city2, city3, city4, city5, city6;
	City cityWithResearchStation1, cityWithResearchStation2, cityWithResearchStation3, cityWithResearchStation4,
			cityWithResearchStation5, cityWithResearchStation6;

	@Before
	public void setup() {
		board = new Board();
		medic = new Medic(board);
		medic.action = 4;
		operationsExpert = new OperationsExpert(board);
		playerLocation = "PlayerLocation";
		playerLocatedCity = new City(playerLocation);
		playerLocatedCity.researchStation = false;
		notPlayerLocation = "notPlayerLocation";
		playerNotLocatedCity = new City(notPlayerLocation);
		playerNotLocatedCity.researchStation = false;
		theNameOfCityWithStation = "theNameOfCityWithStation";
		cityWithStation = new City(theNameOfCityWithStation);
		cityWithStation.researchStation = true;
		
		medic.location = playerLocatedCity;
		operationsExpert.location = playerLocatedCity;
		PlayerCard cityCard = new PlayerCard(Board.CardType.CITYCARD, playerLocation);
		medic.hand.put(playerLocation, cityCard);
		operationsExpert.hand.put(playerLocation, cityCard);
		board.cities.put(playerLocation, playerLocatedCity);
		board.cities.put(notPlayerLocation, playerNotLocatedCity);
		board.cities.put(theNameOfCityWithStation, cityWithStation);

		city1 = "cityWithResearchStation1";
		city2 = "cityWithResearchStation2";
		city3 = "cityWithResearchStation3";
		city4 = "cityWithResearchStation4";
		city5 = "cityWithResearchStation5";
		city6 = "cityWithResearchStation6";
		cityWithResearchStation1 = new City(city1);
		cityWithResearchStation2 = new City(city2);
		cityWithResearchStation3 = new City(city3);
		cityWithResearchStation4 = new City(city4);
		cityWithResearchStation5 = new City(city5);
		cityWithResearchStation6 = new City(city6);

	}

	@Test
	public void testBuildStationAtTheSameCityCardNormal() {
		PlayerCard cityCard = new PlayerCard(Board.CardType.CITYCARD, playerLocation);
		medic.hand.put(playerLocation, cityCard);
		medic.buildStation();
		assertTrue(playerLocatedCity.researchStation);
		assertEquals(3, medic.action);
		assertEquals(0, medic.hand.size());
	}
	
	@Test
	public void testBuildStationAtTheSameCityCardOperationsExpert() {
		PlayerCard cityCard = new PlayerCard(Board.CardType.CITYCARD, playerLocation);
		operationsExpert.hand.put(playerLocation, cityCard);
		operationsExpert.buildStation();
		assertTrue(playerLocatedCity.researchStation);
		assertEquals(3, operationsExpert.action);
		assertEquals(1, operationsExpert.hand.size());
	}

	@Test(expected = RuntimeException.class)
	public void testBuildStationWithTheLocationHasStationNormal() {
		City location = medic.location;
		location.researchStation = true;
		medic.buildStation();
	}
	
	@Test(expected = RuntimeException.class)
	public void testBuildStationWithTheLocationHasStationOperationsExpert() {
		City location = operationsExpert.location;
		location.researchStation = true;
		operationsExpert.buildStation();
	}
	

	@Test(expected = RuntimeException.class)
	public void testBuildStationWithoutSameCityCardNormal() {
		medic.hand.remove(playerLocation);
		medic.buildStation();
	}
	
	@Test
	public void testBuildStationWithoutSameCityCardOperationsExpert() {
		operationsExpert.hand.remove(playerLocation);
		operationsExpert.buildStation();
		assertTrue(playerLocatedCity.researchStation);
		assertTrue(board.currentResearchStation.containsKey(playerLocation));
	}

	@Test
	public void testBuildMoreThanSixStation() {
		addCurrentStation();
		StationBuilder stationBuilderMock = EasyMock.partialMockBuilder(StationBuilderNormal.class)
				.addMockedMethod("returnRandomResearchStationCity")
				.withConstructor(medic, board).createMock();
		
		medic.buildStationModel = stationBuilderMock;	
		EasyMock.expect(stationBuilderMock.returnRandomResearchStationCity())
			.andReturn(cityWithResearchStation1);
		EasyMock.replay(stationBuilderMock);
		
		PlayerCard cityCard = new PlayerCard(Board.CardType.CITYCARD, playerLocation);
		medic.hand.put(playerLocation, cityCard);
		medic.buildStation();
		assertEquals(0, medic.hand.size());
		assertEquals(3, medic.action);
		assertFalse(cityWithResearchStation1.researchStation);
		assertFalse(board.currentResearchStation.containsKey(city1));
		assertTrue(board.currentResearchStation.containsValue(playerLocatedCity));
		assertTrue(playerLocatedCity.researchStation);
	}
	
	private void addCurrentStation() {
		board.currentResearchStation.put(city1, cityWithResearchStation1);
		board.currentResearchStation.put(city2, cityWithResearchStation2);
		board.currentResearchStation.put(city3, cityWithResearchStation3);
		board.currentResearchStation.put(city4, cityWithResearchStation4);
		board.currentResearchStation.put(city5, cityWithResearchStation5);
		board.currentResearchStation.put(city6, cityWithResearchStation6);
	}
	

	@Test
	public void testRandomAndBuildMoreThanSixStation() {
		addCurrentStation();
		Random random = EasyMock.createMock(Random.class);

		medic.random = random;
		board.currentResearchStation = EasyMock.createNiceMock(HashMap.class);
		EasyMock.expect(random.nextInt(6)).andReturn(0);

		ArrayList<City> cities = new ArrayList<City>();
		addCities(cities);

		EasyMock.expect(board.currentResearchStation.values()).andReturn(cities);
		EasyMock.replay(board.currentResearchStation, random);
		
		assertEquals(city1, medic.buildStationModel.returnRandomResearchStationCity().cityName);
	}


	private void addCities(ArrayList<City> cities) {
		cities.add(cityWithResearchStation1);
		cities.add(cityWithResearchStation2);
		cities.add(cityWithResearchStation3);
		cities.add(cityWithResearchStation4);
		cities.add(cityWithResearchStation5);
		cities.add(cityWithResearchStation6);
	}

}
