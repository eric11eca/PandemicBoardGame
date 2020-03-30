package testPlayers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cards.PlayerCard;
import data.Board;
import data.City;
import player.PlayerData;
import playerAction.OperationsExpertAction;

public class TestOperationsExpert {
	Board board;
	City location;
	PlayerData playerData;
	OperationsExpertAction operationsExpertAction;

	@Before
	public void setup() {
		board = new Board();
		playerData = new PlayerData();
		playerData.role = Board.Roles.OPERATIONSEXPERT;
		playerData.location = new City();
		location = playerData.location;
		operationsExpertAction = new OperationsExpertAction(board, playerData);
		playerData.specialSkill = operationsExpertAction;
	}

	@Test
	public void testMoveToAnotherCity() {
		String cityName = "NewYork";
		String new_cityName = "Chicago";
		City city = new City();
		city.cityName = new_cityName;
		board.cities.put(new_cityName, city);
		PlayerCard cityCard = new PlayerCard(Board.CardType.CITYCARD, new_cityName);
		playerData.hand.put(new_cityName, cityCard);
		location.cityName = cityName;
		operationsExpertAction.cityName = new_cityName;
		playerData.specialSkill.useSpecialSkill();
		assertEquals(new_cityName, playerData.location.cityName);
	}

}
