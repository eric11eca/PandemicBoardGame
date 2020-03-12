package testPlayers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import SpeciaoPlayerAction.OperationsExpertState;
import cards.PlayerCard;
import data.Board;
import data.City;
import player.PlayerData;

public class TestOperationsExpert {
	Board board;
	City location;
	PlayerData playerData;
	OperationsExpertState operationsExpertState;

	@Before
	public void setup() {
		board = Board.getInstance();
		playerData = new PlayerData();
		playerData.role = Board.Roles.OPERATIONSEXPERT;
		playerData.location = new City();
		location = playerData.location;
		operationsExpertState = new OperationsExpertState(board, playerData);
		playerData.specialSkill = operationsExpertState;
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
		operationsExpertState.cityName = new_cityName;
		playerData.specialSkill.useSpecialSkill();
		assertEquals(new_cityName, playerData.location.cityName);
	}

}
