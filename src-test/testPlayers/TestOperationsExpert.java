package testPlayers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cardActions.EventCardAction;
import cards.PlayerCard;
import data.Board;
import data.City;
import player.Player;
import player.PlayerData;
import playerAction.OperationsExpertAction;

public class TestOperationsExpert {
	Board board;
	City location;
	Player player;
	PlayerData playerData;
	OperationsExpertAction operationsExpertAction;
	EventCardAction eventCardAction;
	

	@Before
	public void setup() {
		board = new Board();
		playerData = new PlayerData();
		playerData.role = Board.Roles.OPERATIONSEXPERT;
		playerData.location = new City();
		location = playerData.location;
		operationsExpertAction = new OperationsExpertAction(board, playerData);
		eventCardAction = new EventCardAction(board);
		playerData.specialSkill = operationsExpertAction;
		player = new Player(board, playerData);
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
		playerData.specialSkill.specialSkill();
		assertEquals(new_cityName, playerData.location.cityName);
	}

}
