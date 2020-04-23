package test.playerAction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

import game.cards.Deck;
import game.city.City;
import game.city.CitySet;
import game.player.Player;
import game.player.PlayerImpl;
import game.player.action.Action;
import game.player.action.ActionShuttleFlight;
import test.MockCityBuilder;
import test.MockInteraction;

public class TestShuttleFlightAction {
	Player player;
	City chicagoCity, newyorkCity, londonCity;

	MockCityBuilder cityFactory = new MockCityBuilder();
	boolean cbExecuted;
	MockInteraction interaction;
	
	Deck discard;

	@Before
	public void setup() {
		MockCityBuilder newyorkBuilder = new MockCityBuilder().name("NewYork");
		newyorkCity = newyorkBuilder.build();
		newyorkCity.buildResearchStation();

		MockCityBuilder chicagoBuilder = new MockCityBuilder().name("Chicago");
		chicagoCity = chicagoBuilder.build();
		
		MockCityBuilder longdonBuilder = new MockCityBuilder().name("London");
		londonCity = longdonBuilder.build();
		londonCity.buildResearchStation();

		cbExecuted = false;
		interaction = new MockInteraction();	
		interaction.implementSelectCityFrom((citiesToSelectFrom, callback) -> {
			assertTrue(citiesToSelectFrom.contains(londonCity));
			assertFalse(citiesToSelectFrom.contains(chicagoCity));
			callback.accept(londonCity);
		});
		
		discard = new Deck();
		player = new PlayerImpl(0, newyorkCity, discard, interaction);
	}

	@Test
	public void testSuccessShuttleFlight() {
		Set<City> citySet = new HashSet<>();
		citySet.add(chicagoCity);
		citySet.add(londonCity);
		
		assertEquals(newyorkCity, player.getLocation());
		
		Action action = new ActionShuttleFlight(new CitySet(citySet), player, interaction);
		action.perform(() -> cbExecuted = true);

		assertTrue(cbExecuted);
		assertEquals(londonCity, player.getLocation());
	}
}
