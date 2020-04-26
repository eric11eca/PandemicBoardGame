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
import game.player.action.ActionSkillOperationsExpert;
import mock.MockCityBuilder;
import mock.MockInteraction;

public class TestSkillOperationExpert {
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
		newyorkCity.buildResearchStation();

		MockCityBuilder chicagoBuilder = new MockCityBuilder().name("Chicago");
		chicagoCity = chicagoBuilder.build();

		newyorkBuilder.neighborSet().add(chicagoCity);
		chicagoBuilder.neighborSet().add(newyorkCity);

		newyorkCard = new CardCity(newyorkCity);

		cbExecuted = false;
		interaction = new MockInteraction();
		interaction.implementSelectCardsFrom(this::selectNewYorkCard);
		interaction.implementSelectCityFrom(this::selectChicago);

		discard = new Deck();
		player = new PlayerImpl(null, newyorkCity, discard, interaction);

		Set<City> citySet = new HashSet<>();
		citySet.add(chicagoCity);
		citySet.add(newyorkCity);
		cities = new CitySet(citySet);
	}

	@Test
	public void testOperationsExpertMove() {

		assertTrue(newyorkCity.equals(player.getLocation()));
		assertTrue(newyorkCity.hasResearchStation());
		player.receiveCard(newyorkCard);

		Action action = new ActionSkillOperationsExpert(player, interaction, cities);
		assertTrue(action.isOncePerTurn());
		assertTrue(action.canPerform());
		action.perform(() -> cbExecuted = true);
		interaction.verifySelectCardsCalled(1);
		interaction.verifySelectCityCalled(1);

		assertTrue(cbExecuted);
		assertEquals(chicagoCity, player.getLocation());
		assertTrue(discard.contains(newyorkCard));
	}

	@Test
	public void testCannotPerformNoStation() {
		player.setLocation(chicagoCity);
		player.receiveCard(newyorkCard);
		Action action = new ActionSkillOperationsExpert(player, interaction, cities);
		assertFalse(action.canPerform());
	}

	@Test
	public void testCannotPerformNoCard() {
		Action action = new ActionSkillOperationsExpert(player, interaction, cities);
		assertFalse(action.canPerform());
	}

	private void selectNewYorkCard(int number, List<Card> cards, Consumer<List<Card>> callback) {
		assertTrue(cards.contains(newyorkCard));
		callback.accept(Arrays.asList(newyorkCard));
	}

	private void selectChicago(Set<City> citiesToSelectFrom, Consumer<City> callback) {
		assertTrue(citiesToSelectFrom.contains(chicagoCity));
		callback.accept(chicagoCity);
	}
}
