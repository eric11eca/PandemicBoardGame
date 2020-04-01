package testPlayers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cards.PlayerCard;
import data.Board;
import game.city.City;
import helpers.TestCityFactory;
import player.PlayerData;
import player.StationBuilder;
import player.StationBuilderNormal;

public class TestBuildStationNormal {
	Board board;
	PlayerData medicData;
	StationBuilder stationBuilderNormal;
	City playerLocatedCity;
	String playerLocation;
	TestCityFactory cityFactory = new TestCityFactory();

	@Before
	public void setup() {
		board = new Board();
		medicData = new PlayerData();
		playerLocation = "PlayerLocation";
		playerLocatedCity = cityFactory.makeFakeCity(playerLocation);
		playerLocatedCity.removeResearchStation();
		medicData.location = playerLocatedCity;
		PlayerCard cityCard = new PlayerCard(Board.CardType.CITYCARD, playerLocation);
		medicData.hand.put(playerLocation, cityCard);
		stationBuilderNormal = new StationBuilderNormal(medicData, board);
	}

	@Test
	public void testBuildStationAtTheSameCityCardNormal() {
		stationBuilderNormal.buildStation();
		assertTrue(playerLocatedCity.hasResearchStation());
		assertEquals(0, medicData.hand.size());
	}

	@Test(expected = RuntimeException.class)
	public void testBuildStationWithTheLocationHasStationNormal() {
		City location = medicData.location;
		location.buildResearchStation();
		stationBuilderNormal.buildStation();
	}

	@Test(expected = RuntimeException.class)
	public void testBuildStationWithoutSameCityCardNormal() {
		medicData.hand.remove(playerLocation);
		stationBuilderNormal.buildStation();
	}
}
