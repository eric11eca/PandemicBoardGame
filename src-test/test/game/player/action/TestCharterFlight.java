package test.game.player.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

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
import game.player.action.ActionCharterFlight;
import mock.MockCityBuilder;
import mock.MockInteraction;

public class TestCharterFlight {
	private Player player;
	private City chicagoCity, newyorkCity;
	private CitySet cities;

	private boolean cbExecuted;
	private MockInteraction interaction;

	private Card newyorkCard;

	private Deck discard;

	@Before
	public void setup() {
		MockCityBuilder newyorkBuilder = new MockCityBuilder().name("NewYork");
		newyorkCity = newyorkBuilder.build();

		MockCityBuilder chicagoBuilder = new MockCityBuilder().name("Chicago");
		chicagoCity = chicagoBuilder.build();

		newyorkBuilder.neighborSet().add(chicagoCity);
		chicagoBuilder.neighborSet().add(newyorkCity);

		newyorkCard = new CardCity(newyorkCity);

		cbExecuted = false;
		interaction = new MockInteraction();
		interaction.implementSelectCardsFrom(this::selectNewYork);
		interaction.implementSelectCityFrom(this::selectChicago);

		discard = new Deck();
		player = new PlayerImpl(null, newyorkCity, discard, interaction);

		Set<City> citySet = new HashSet<>();
		citySet.add(chicagoCity);
		citySet.add(newyorkCity);

		cities = new CitySet(citySet);

	}

	@Test
	public void testSuccessCharterFlight() {

		assertEquals(newyorkCity, player.getLocation());
		player.receiveCard(newyorkCard);

		Action action = new ActionCharterFlight(cities, player, interaction);
		assertTrue(action.canPerform());
		action.perform(() -> cbExecuted = true);

		assertTrue(cbExecuted);
		assertEquals(chicagoCity, player.getLocation());
		assertTrue(discard.contains(newyorkCard));
	}

	@Test
	public void testCannotPerformNoCard() {
		Action action = new ActionCharterFlight(cities, player, interaction);
		assertFalse(action.canPerform());
	}

	private void selectNewYork(int number, List<Card> cards, Consumer<List<Card>> callback) {
		assertTrue(cards.contains(newyorkCard));
		callback.accept(Arrays.asList(newyorkCard));
	}

	private void selectChicago(Set<City> cities, Consumer<City> callback) {
		assertTrue(cities.contains(chicagoCity));
		callback.accept(chicagoCity);
	}
}
