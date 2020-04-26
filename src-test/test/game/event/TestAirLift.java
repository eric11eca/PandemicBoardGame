package test.game.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import game.cards.Deck;
import game.city.City;
import game.city.CitySet;
import game.event.Event;
import game.event.EventAirlift;
import game.player.Player;
import game.player.PlayerImpl;
import mock.MockCityBuilder;
import mock.MockInteraction;

public class TestAirLift {
	private Player player;
	private City chicagoCity, newyorkCity;

	private MockInteraction interaction;

	@Before
	public void setup() {
		newyorkCity = new MockCityBuilder().name("NewYork").build();
		chicagoCity = new MockCityBuilder().name("Chicago").build();

		interaction = new MockInteraction().implementSelectPlayerFrom((players, callback) -> {
			assertTrue(players.contains(player));
			callback.accept(player);
		}).implementSelectCityFrom((citiesToSelectFrom, callback) -> {
			assertFalse(citiesToSelectFrom.contains(newyorkCity));
			assertTrue(citiesToSelectFrom.contains(chicagoCity));
			callback.accept(chicagoCity);
		});

		player = new PlayerImpl(null, newyorkCity, new Deck(), interaction);

	}

	@Test
	public void testAirLift() {
		List<Player> playerList = new ArrayList<>();
		playerList.add(player);

		Set<City> citySet = new HashSet<>();
		citySet.add(newyorkCity);
		citySet.add(chicagoCity);

		assertEquals(newyorkCity, player.getLocation());

		Event event = new EventAirlift(playerList, new CitySet(citySet));

		event.executeEvent(interaction);

		assertEquals(chicagoCity, player.getLocation());
		interaction.verifySelectPlayerCalled(1);
		interaction.verifySelectCityCalled(1);
	}

}
