package test.game.player.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;

import game.cards.Card;
import game.cards.CardCity;
import game.cards.Deck;
import game.city.City;
import game.player.Player;
import game.player.PlayerImpl;
import game.player.action.Action;
import game.player.action.ActionDirectFlight;
import mock.MockCityBuilder;
import mock.MockInteraction;

public class TestDirectFlight {
	private Player player;
	private City chicagoCity, newyorkCity;

	private boolean cbExecuted;
	private MockInteraction interaction;

	private Card chicagoCard;
	private Deck discard;

	@Before
	public void setup() {
		MockCityBuilder newyorkBuilder = new MockCityBuilder().name("NewYork");
		newyorkCity = newyorkBuilder.build();

		MockCityBuilder chicagoBuilder = new MockCityBuilder().name("Chicago");
		chicagoCity = chicagoBuilder.build();

		newyorkBuilder.neighborSet().add(chicagoCity);
		chicagoBuilder.neighborSet().add(newyorkCity);

		chicagoCard = new CardCity(chicagoCity);

		cbExecuted = false;
		interaction = new MockInteraction();
		interaction.implementSelectCardsFrom(this::selectCardsFrom);

		discard = new Deck();
		player = new PlayerImpl(null, newyorkCity, discard, interaction);

	}

	@Test
	public void testSuccessDirectFlight() {
		player.receiveCard(chicagoCard);
		assertEquals(newyorkCity, player.getLocation());

		Action action = new ActionDirectFlight(player, interaction);
		assertTrue(action.canPerform());
		action.perform(() -> cbExecuted = true);

		assertTrue(cbExecuted);
		assertEquals(chicagoCity, player.getLocation());
		assertTrue(discard.contains(chicagoCard));
	}

	@Test
	public void testCannotPerformHasNoCard() {
		Action action = new ActionDirectFlight(player, interaction);
		assertFalse(action.canPerform());
	}

	@Test
	public void testCannotPerformAlreadyThere() {
		player.receiveCard(chicagoCard);
		player.setLocation(chicagoCity);
		Action action = new ActionDirectFlight(player, interaction);
		assertFalse(action.canPerform());
	}

	private void selectCardsFrom(int number, List<Card> cards, Consumer<List<Card>> callback) {
		assertTrue(cards.contains(chicagoCard));
		callback.accept(Arrays.asList(chicagoCard));
	}
}
