package test.game.player.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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
import game.player.Player;
import game.player.PlayerImpl;
import game.player.PlayerRole;
import game.player.action.Action;
import game.player.action.ActionDirectFlight;
import mock.MockCityBuilder;
import mock.MockInteraction;

public class TestDirectFlight {
	Player player;
	City chicagoCity, newyorkCity;

	MockCityBuilder cityFactory = new MockCityBuilder();
	boolean cbExecuted;
	MockInteraction interaction;

	Card chicagoCard;
	List<Card> cardList;

	Deck discard;

	@Before
	public void setup() {
		MockCityBuilder newyorkBuilder = new MockCityBuilder().name("NewYork");
		newyorkCity = newyorkBuilder.build();

		MockCityBuilder chicagoBuilder = new MockCityBuilder().name("Chicago");
		chicagoCity = chicagoBuilder.build();

		newyorkBuilder.neighborSet().add(chicagoCity);
		chicagoBuilder.neighborSet().add(newyorkCity);

		chicagoCard = new CardCity(chicagoCity);
		cardList = new ArrayList<>();
		cardList.add(chicagoCard);

		cbExecuted = false;
		interaction = new MockInteraction();
		interaction.implementSelectCardsFrom(this::selectCardsFrom);

		discard = new Deck();
		player = new PlayerImpl(null, newyorkCity, discard, interaction);
		player.receiveCard(chicagoCard);
	}

	@Test
	public void testSuccessDirectFlight() {
		Set<City> citySet = new HashSet<>();
		citySet.add(chicagoCity);
		assertEquals(newyorkCity, player.getLocation());

		Action action = new ActionDirectFlight(player, interaction);
		action.perform(() -> cbExecuted = true);

		assertTrue(cbExecuted);
		assertEquals(chicagoCity, player.getLocation());
		assertTrue(discard.contains(chicagoCard));
	}

	private void selectCardsFrom(int number, List<Card> cards, Consumer<List<Card>> callback) {
		callback.accept(cardList);
	}
}
