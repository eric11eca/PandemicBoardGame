package test.game.player.action;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import game.cards.Card;
import game.cards.CardCity;
import game.cards.Deck;
import game.city.City;
import game.city.CitySet;
import game.player.Player;
import game.player.PlayerImpl;
import game.player.action.Action;
import game.player.action.ActionBuildStation;
import mock.MockCityBuilder;
import mock.MockGameProperty;
import mock.MockInteraction;

public class TestBuildStation {
	private Player player;
	private City newyorkCity;
	private City city2;
	private boolean cbExecuted;
	private MockInteraction interaction;
	private Deck discard;

	@Before
	public void setup() {
		newyorkCity = new MockCityBuilder().name("NewYork").build();
		city2 = new MockCityBuilder().build();

		cbExecuted = false;
		interaction = new MockInteraction();
		interaction.implementSelectCardsFrom(this::selectNewYorkCard);
		interaction.implementSelectCityFrom(this::selectCity2);

		discard = new Deck();
		player = new PlayerImpl(null, newyorkCity, discard, interaction);

	}

	@After
	public void cleanProperty() {
		new MockGameProperty().resetAndEject();
	}

	@Test
	public void testBuildResearchStation() {
		Set<City> citySet = new HashSet<>();
		citySet.add(newyorkCity);
		player.receiveCard(new CardCity(newyorkCity));

		assertFalse(newyorkCity.hasResearchStation());
		Action action = new ActionBuildStation(player, interaction, true, new CitySet(citySet));
		assertTrue(action.canPerform());
		action.perform(() -> cbExecuted = true);
		assertTrue(cbExecuted);
		assertTrue(newyorkCity.hasResearchStation());
		assertTrue(discard.contains(new CardCity(newyorkCity)));
		interaction.verifySelectCardsCalled(1);
	}

	@Test
	public void testBuildResearchStationTooMany() {

		Set<City> citySet = new HashSet<>();
		citySet.add(newyorkCity);
		citySet.add(city2);
		player.receiveCard(new CardCity(newyorkCity));
		new MockGameProperty().put("MAX_STATION_COUNT", "1").inject();
		city2.buildResearchStation();
		Action action = new ActionBuildStation(player, interaction, true, new CitySet(citySet));

		assertFalse(newyorkCity.hasResearchStation());
		assertTrue(action.canPerform());

		action.perform(() -> cbExecuted = true);
		assertTrue(cbExecuted);
		assertTrue(newyorkCity.hasResearchStation());
		assertFalse(city2.hasResearchStation());
		assertTrue(discard.contains(new CardCity(newyorkCity)));
		interaction.verifySelectCardsCalled(1);
		interaction.verifySelectCityCalled(1);
	}

	@Test
	public void testCannotPerformNoCard() {
		Set<City> citySet = new HashSet<>();
		citySet.add(newyorkCity);
		Action action = new ActionBuildStation(player, interaction, true, new CitySet(citySet));
		assertFalse(action.canPerform());
	}

	@Test
	public void testPerformWithoutCard() {
		Set<City> citySet = new HashSet<>();
		citySet.add(newyorkCity);

		assertFalse(newyorkCity.hasResearchStation());
		Action action = new ActionBuildStation(player, interaction, false, new CitySet(citySet));
		assertFalse(action.isOncePerTurn());
		assertTrue(action.canPerform());
		action.perform(() -> cbExecuted = true);
		assertTrue(cbExecuted);
		assertTrue(newyorkCity.hasResearchStation());
		assertTrue(discard.isEmpty());
		interaction.verifySelectCardsCalled(0);
	}

	@Test
	public void testCannotPerformHasStation() {
		Set<City> citySet = new HashSet<>();
		citySet.add(newyorkCity);
		player.receiveCard(new CardCity(newyorkCity));
		newyorkCity.buildResearchStation();
		Action action = new ActionBuildStation(player, interaction, true, new CitySet(citySet));
		assertFalse(action.canPerform());
	}

	private void selectNewYorkCard(int number, List<Card> cards, Consumer<List<Card>> callback) {
		assertTrue(cards.contains(new CardCity(newyorkCity)));
		callback.accept(Arrays.asList(new CardCity(newyorkCity)));
	}

	private void selectCity2(Set<City> cities, Consumer<City> callback) {
		assertTrue(cities.contains(city2));
		callback.accept(city2);
	}
}
