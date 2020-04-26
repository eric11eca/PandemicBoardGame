package test.game.player.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import game.city.City;
import game.city.CitySet;
import game.player.Player;
import game.player.PlayerImpl;
import game.player.action.Action;
import game.player.action.ActionShuttleFlight;
import mock.MockCityBuilder;
import mock.MockInteraction;

public class TestShuttleFlight {
	private Player player;
	private City chicagoCity, newyorkCity, londonCity;
	private CitySet cities;

	private boolean cbExecuted;
	private MockInteraction interaction;

	@Before
	public void setup() {
		newyorkCity = new MockCityBuilder().name("NewYork").build();
		newyorkCity.buildResearchStation();

		chicagoCity = new MockCityBuilder().name("Chicago").build();

		londonCity = new MockCityBuilder().name("London").build();
		londonCity.buildResearchStation();

		cbExecuted = false;
		interaction = new MockInteraction();
		interaction.implementSelectCityFrom((citiesToSelectFrom, callback) -> {
			assertTrue(citiesToSelectFrom.contains(londonCity));
			assertFalse(citiesToSelectFrom.contains(chicagoCity));
			callback.accept(londonCity);
		});

		player = new PlayerImpl(null, newyorkCity, null, interaction);

		Set<City> citySet = new HashSet<>();
		citySet.add(chicagoCity);
		citySet.add(londonCity);
		citySet.add(newyorkCity);

		cities = new CitySet(citySet);
	}

	@Test
	public void testSuccessShuttleFlight() {

		assertEquals(newyorkCity, player.getLocation());

		Action action = new ActionShuttleFlight(cities, player, interaction);
		assertFalse(action.isOncePerTurn());
		assertTrue(action.canPerform());
		action.perform(() -> cbExecuted = true);

		interaction.verifySelectCityCalled(1);

		assertTrue(cbExecuted);
		assertEquals(londonCity, player.getLocation());
	}

	@Test
	public void testCannotPerformNoStation() {
		player.setLocation(chicagoCity);
		Action action = new ActionShuttleFlight(cities, player, interaction);
		assertFalse(action.canPerform());
	}

	@Test
	public void testCannotPerformNoOtherStation() {
		londonCity.removeResearchStation();
		player.setLocation(chicagoCity);
		Action action = new ActionShuttleFlight(cities, player, interaction);
		assertFalse(action.canPerform());

	}
}
