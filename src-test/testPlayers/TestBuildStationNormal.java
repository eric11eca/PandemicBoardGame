package testPlayers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import PlayerAction.StationBuilder;
import PlayerAction.StationBuilderNormal;
import cards.PlayerCard;
import data.Board;
import data.City;
import player.PlayerData;

public class TestBuildStationNormal {
	Board board;
	PlayerData medicData;
	StationBuilder stationBuilderNormal;
	City playerLocatedCity;
	String playerLocation;
	
	@Before
	public void setup() {
		board = Board.getInstance();
		medicData = new PlayerData();
		playerLocation = "PlayerLocation";
		playerLocatedCity = new City(playerLocation);
		playerLocatedCity.researchStation = false;
		medicData.location = playerLocatedCity;
		PlayerCard cityCard = new PlayerCard(Board.CardType.CITYCARD, playerLocation);
		medicData.hand.put(playerLocation, cityCard);
		stationBuilderNormal = new StationBuilderNormal(medicData, board);
	}

	@Test
	public void testBuildStationAtTheSameCityCardNormal() {
		stationBuilderNormal.buildStation();
		assertTrue(playerLocatedCity.researchStation);
		assertEquals(0, medicData.hand.size());
	}

	@Test(expected = RuntimeException.class)
	public void testBuildStationWithTheLocationHasStationNormal() {
		City location = medicData.location;
		location.researchStation = true;
		stationBuilderNormal.buildStation();
	}

	@Test(expected = RuntimeException.class)
	public void testBuildStationWithoutSameCityCardNormal() {
		medicData.hand.remove(playerLocation);
		stationBuilderNormal.buildStation();
	}
}
