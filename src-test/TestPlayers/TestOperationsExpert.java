package TestPlayers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.Board;
import Initialize.City;
import Player.OperationsExpert;

public class TestOperationsExpert {
	OperationsExpert operationsExpert;
	City location;
	Board board;

	@Before
	public void setup() {
		board = new Board();
		operationsExpert = new OperationsExpert(board);
		operationsExpert.location = new City();
		location = operationsExpert.location;
	}

	@Test
	public void testMoveToAnotherCity() {
		String cityName = "NewYork";
		String new_cityName = "Chicago";
		City city = new City();
		city.cityName = new_cityName;
		board.cities.put(new_cityName, city);
		PlayerCard cityCard = new PlayerCard(Board.CardType.CITYCARD, new_cityName);
		operationsExpert.hand.put(new_cityName, cityCard);
		location.cityName = cityName;
		operationsExpert.moveToAnotherCity(new_cityName);
		assertEquals(new_cityName, operationsExpert.location.cityName);
	}

}
