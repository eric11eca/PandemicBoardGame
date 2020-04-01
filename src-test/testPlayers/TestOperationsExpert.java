package testPlayers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cards.PlayerCard;
import data.Board;
import game.city.City;
import helpers.TestCityFactory;
import player.PlayerData;
import playerAction.OperationsExpertAction;

public class TestOperationsExpert {
	Board board;
	City location;
	PlayerData playerData;
	OperationsExpertAction operationsExpertAction;
	TestCityFactory cityFactory = new TestCityFactory();

	@Before
	public void setup() {
		String cityName = "NewYork";
		board = new Board();
		playerData = new PlayerData();
		playerData.role = Board.Roles.OPERATIONSEXPERT;
		playerData.location = cityFactory.makeFakeCity(cityName);
		location = playerData.location;
		operationsExpertAction = new OperationsExpertAction(board, playerData);
		playerData.specialSkill = operationsExpertAction;
	}

	@Test
	public void testMoveToAnotherCity() {

		String new_cityName = "Chicago";
		City city = cityFactory.makeFakeCity(new_cityName);
		board.cities.put(new_cityName, city);
		PlayerCard cityCard = new PlayerCard(Board.CardType.CITYCARD, new_cityName);
		playerData.hand.put(new_cityName, cityCard);
		operationsExpertAction.cityName = new_cityName;
		playerData.specialSkill.useSpecialSkill();
		assertEquals(new_cityName, playerData.location.getName());
	}

}
