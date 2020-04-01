package testPlayerCardAction;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import cards.Airlift;
import data.Board;
import data.CityData;
import data.GameColor;
import game.city.City;
import helpers.TestCityFactory;
import player.Player;
import player.PlayerData;

public class TestAirliftEvent {
	Board board;
	Airlift airlift;
	Player player;
	PlayerData playerData;

	TestCityFactory cityFactory = new TestCityFactory();

	@Before
	public void setup() {
		board = new Board();
		airlift = new Airlift(board);
		player = EasyMock.createMock(Player.class);
		player.board = board;
		player.playerData = new PlayerData();
		City city = cityFactory.makeFakeCity("Atlanta");
		player.playerData.location = city;
		board.currentPlayers.add(player);
		board.currentPlayer = player;
	}

	@Test
	public void testAirlift() {
		board.idxofPlayerAirlift = 0;
		board.nameofCityAirlift = "Chicago";

		City city1 = cityFactory.makeFakeCity("Atlanta");
		City city2 = cityFactory.makeFakeCity("Chicago");

		board.cities.put("Atlanta", city1);
		board.cities.put("Chicago", city2);

		EasyMock.replay(player);

		assertEquals("Atlanta", board.currentPlayer.playerData.location.getName());
		airlift.executeEvent();
		assertEquals("Chicago", board.currentPlayer.playerData.location.getName());

		EasyMock.verify(player);
	}

}
