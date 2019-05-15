package testPlayers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cardActions.EventCardAction;
import cards.PlayerCard;
import data.Board;
import data.City;
import player.Player;
import player.PlayerData;
import player.StationBuilderNormal;
import player.StationBuilderOperationsExpert;
import playerAction.MedicAction;
import playerAction.OperationsExpertAction;

public class TestBuildStation {
	Board board;
	PlayerData medicData, operationsExpertData;
	EventCardAction eventCardAction;
	MedicAction medicAction;
	OperationsExpertAction operationsExpertAction;
	Player playerActionMedic, playerActionOperation;
	City playerLocatedCity, cityWithStation, playerNotLocatedCity;
	String playerLocation, notPlayerLocation, theNameOfCityWithStation;
	String city1, city2, city3, city4, city5, city6;
	City cityWithResearchStation1, cityWithResearchStation2, cityWithResearchStation3, cityWithResearchStation4,
			cityWithResearchStation5, cityWithResearchStation6;
	
	@Before
	public void setup() {
		board = new Board();
		
		medicData = new PlayerData();
		medicData.role = Board.Roles.MEDIC;
		medicData.action = 4;
		medicAction = new MedicAction(board, medicData);
		medicData.buildStationModel = new StationBuilderNormal(medicData, board);
		
		operationsExpertData = new PlayerData();
		operationsExpertData.role = Board.Roles.OPERATIONSEXPERT;
		operationsExpertData.action = 4;
		operationsExpertAction = new OperationsExpertAction(board, operationsExpertData);
		operationsExpertData.buildStationModel = new StationBuilderOperationsExpert(operationsExpertData, board);
		
		eventCardAction = new EventCardAction(board);
		playerActionMedic = new Player(board, medicData);
		playerActionOperation = new Player(board, operationsExpertData);
		playerLocation = "PlayerLocation";
		playerLocatedCity = new City(playerLocation);
		playerLocatedCity.researchStation = false;
		notPlayerLocation = "notPlayerLocation";
		playerNotLocatedCity = new City(notPlayerLocation);
		playerNotLocatedCity.researchStation = false;
		theNameOfCityWithStation = "theNameOfCityWithStation";
		cityWithStation = new City(theNameOfCityWithStation);
		cityWithStation.researchStation = true;

		medicData.location = playerLocatedCity;
		operationsExpertData.location = playerLocatedCity;
		PlayerCard cityCard = new PlayerCard(Board.CardType.CITYCARD, playerLocation);
		medicData.hand.put(playerLocation, cityCard);
		operationsExpertData.hand.put(playerLocation, cityCard);
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
		medicData.hand.put(playerLocation, cityCard);
		playerActionMedic.buildStation();
		assertTrue(playerLocatedCity.researchStation);
		assertEquals(3, medicData.action);
		assertEquals(0, medicData.hand.size());
	}

	@Test
	public void testBuildStationAtTheSameCityCardOperationsExpert() {
		PlayerCard cityCard = new PlayerCard(Board.CardType.CITYCARD, playerLocation);
		operationsExpertData.hand.put(playerLocation, cityCard);
		playerActionOperation.buildStation();
		assertTrue(playerLocatedCity.researchStation);
		assertEquals(3, operationsExpertData.action);
		assertEquals(1, operationsExpertData.hand.size());
	}

	@Test(expected = RuntimeException.class)
	public void testBuildStationWithTheLocationHasStationNormal() {
		City location = medicData.location;
		location.researchStation = true;
		playerActionMedic.buildStation();
	}

	@Test(expected = RuntimeException.class)
	public void testBuildStationWithTheLocationHasStationOperationsExpert() {
		City location = operationsExpertData.location;
		location.researchStation = true;
		playerActionMedic.buildStation();
	}

	@Test(expected = RuntimeException.class)
	public void testBuildStationWithoutSameCityCardNormal() {
		medicData.hand.remove(playerLocation);
		playerActionMedic.buildStation();
	}

	@Test
	public void testBuildStationWithoutSameCityCardOperationsExpert() {
		operationsExpertData.hand.remove(playerLocation);
		playerActionOperation.buildStation();
		assertTrue(playerLocatedCity.researchStation);
		assertTrue(board.currentResearchStation.containsKey(playerLocation));
	}

}
