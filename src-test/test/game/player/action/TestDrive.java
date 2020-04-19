package test.game.player.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;

import game.cards.Card;
import game.cards.Deck;
import game.city.City;
import game.city.CitySet;
import game.player.Player;
import game.player.PlayerImpl;
import game.player.PlayerRole;
import game.player.action.Action;
import game.player.action.ActionDrive;
import mock.MockCityBuilder;
import mock.MockInteraction;

public class TestDrive {
	Player player;
	City location, neighborCity, notNeighborCity;

	MockCityBuilder cityFactory = new MockCityBuilder();
	boolean cbExecuted;
	MockInteraction interaction;

	@Before
	public void setup() {
		MockCityBuilder neighborBuilder = new MockCityBuilder().name("Atlanta");
		neighborCity = neighborBuilder.build();

		MockCityBuilder locationBuilder = new MockCityBuilder().name("Chicago");
		location = locationBuilder.build();

		neighborBuilder.neighborSet().add(location);
		locationBuilder.neighborSet().add(neighborCity);

		notNeighborCity = new MockCityBuilder().name("Shanghai").build();

		cbExecuted = false;
		interaction = new MockInteraction();
		interaction.implementSelectCityFrom((citiesToSelectFrom, callback) -> {
			assertTrue(citiesToSelectFrom.contains(neighborCity));
			assertFalse(citiesToSelectFrom.contains(location));
			assertFalse(citiesToSelectFrom.contains(notNeighborCity));
			callback.accept(neighborCity);
		});
		player = new PlayerImpl(null, location, new Deck(), interaction);
	}

	@Test
	public void testNeighborDrive() {
		Set<City> citySet = new HashSet<>();
		citySet.add(neighborCity);

		Action action = new ActionDrive(new CitySet(citySet), player, interaction);
		assertTrue(action.canPerform());
		action.perform(() -> cbExecuted = true);
		assertTrue(cbExecuted);
		assertEquals(player.getLocation(), neighborCity);
	}

}
