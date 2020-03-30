package testPlayers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import cards.PlayerCard;
import data.Board;
import data.City;
import player.PlayerData;
import player.StationBuilder;
import player.StationBuilderOperationsExpert;

public class TestStationBuilderOperationsExpert {
	Board board;
	PlayerData operationsExpertData;
	StationBuilder stationBuilderOperationExpert;
	City playerLocatedCity, cityWithStation;
	String playerLocation, theNameOfCityWithStation;
	
	@Before
	public void setup() {
		board = new Board();
		operationsExpertData = new PlayerData();
		
		playerLocation = "PlayerLocation";
		playerLocatedCity = new City(playerLocation);
		playerLocatedCity.researchStation = false;

		theNameOfCityWithStation = "theNameOfCityWithStation";
		cityWithStation = new City(theNameOfCityWithStation);
		cityWithStation.researchStation = true;

		operationsExpertData.location = playerLocatedCity;
		PlayerCard cityCard = new PlayerCard(Board.CardType.CITYCARD, playerLocation);
		operationsExpertData.hand.put(playerLocation, cityCard);
		
		stationBuilderOperationExpert = new StationBuilderOperationsExpert(operationsExpertData, board);
	}

	@Test
	public void testBuildStationAtTheSameCityCardOperationsExpert() {
		PlayerCard cityCard = new PlayerCard(Board.CardType.CITYCARD, playerLocation);
		operationsExpertData.hand.put(playerLocation, cityCard);
		stationBuilderOperationExpert.buildStation();
		assertTrue(playerLocatedCity.researchStation);
		assertEquals(1, operationsExpertData.hand.size());
	}

	@Test
	public void testBuildStationWithoutSameCityCardOperationsExpert() {
		operationsExpertData.hand.remove(playerLocation);
		stationBuilderOperationExpert.buildStation();
		assertTrue(playerLocatedCity.researchStation);
		assertTrue(board.currentResearchStation.containsKey(playerLocation));
	}

}
