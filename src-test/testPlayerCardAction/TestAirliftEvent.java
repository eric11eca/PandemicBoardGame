package testPlayerCardAction;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import cards.Airlift;
import data.Board;
import data.CityData;
import data.GameColor;
import game.City;
import player.Player;
import player.PlayerData;

public class TestAirliftEvent {
	Board board;
	Airlift airlift;
	Player player;
	PlayerData playerData;

	@Before
	public void setup() {
		board = new Board();
		airlift = new Airlift(board);
		player = EasyMock.createMock(Player.class);
		player.board = board;
		player.playerData = new PlayerData();
		City city = new City(new CityData("Atlanta", GameColor.RED, 10), 0, 0);
		player.playerData.location = city;
		board.currentPlayers.add(player);
		board.currentPlayer = player;
	}

	@Test
	public void testAirlift() {
		board.idxofPlayerAirlift = 0;
		board.nameofCityAirlift = "Chicago";

		City city1 = new City(new CityData("Atlanta", GameColor.RED, 10), 0, 0);
		City city2 = new City(new CityData("Chicago", GameColor.RED, 10), 0, 0);

		board.cities.put("Atlanta", city1);
		board.cities.put("Chicago", city2);

		EasyMock.replay(player);

		assertEquals("Atlanta", board.currentPlayer.playerData.location.getName());
		airlift.executeEvent();
		assertEquals("Chicago", board.currentPlayer.playerData.location.getName());

		EasyMock.verify(player);
	}

}
