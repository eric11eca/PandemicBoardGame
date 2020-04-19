package test.game.player.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import game.cards.event.CardEvent;
import game.city.City;
import game.city.CitySet;
import game.event.Event;
import game.event.EventAirlift;
import game.player.Player;
import game.player.PlayerImpl;
import game.player.PlayerRole;
import game.player.action.Action;
import game.player.action.ActionEventCard;
import mock.MockCityBuilder;
import mock.MockInteraction;

public class TestUseEventCard {
	Player player;
	City chicagoCity, newyorkCity;

	MockCityBuilder cityFactory = new MockCityBuilder();
	boolean cbExecuted;
	MockInteraction interaction;

	Card newyorkCard;
	Card evetCard;
	List<Card> cardList;

	@Before
	public void setup() {
		cardList = new ArrayList<>();

		MockCityBuilder newyorkBuilder = new MockCityBuilder().name("NewYork");
		newyorkCity = newyorkBuilder.build();

		newyorkCard = new CardCity(newyorkCity);
		MockCityBuilder chicagoBuilder = new MockCityBuilder().name("Chicago");
		chicagoCity = chicagoBuilder.build();

		cbExecuted = false;
		interaction = new MockInteraction();
		interaction.implementSelectCardsFrom(this::selectCardsFrom);

		player = new PlayerImpl(null, newyorkCity, new Deck(), interaction);
		player.receiveCard(newyorkCard);

		interaction.implementSelectPlayerFrom((players, callback) -> {
			assertTrue(players.contains(player));
			callback.accept(player);
		});

		interaction.implementSelectCityFrom((citiesToSelectFrom, callback) -> {
			assertFalse(citiesToSelectFrom.contains(newyorkCity));
			assertTrue(citiesToSelectFrom.contains(chicagoCity));
			callback.accept(chicagoCity);
		});
	}

	@Test
	public void testAirLift() {
		List<Player> playerList = new ArrayList<>();
		playerList.add(player);

		Set<City> citySet = new HashSet<>();
		citySet.add(newyorkCity);
		citySet.add(chicagoCity);

		Event airlift = new EventAirlift(playerList, new CitySet(citySet));
		evetCard = new CardEvent(airlift);
		cardList.add(evetCard);
		player.receiveCard(evetCard);

		assertEquals(newyorkCity, player.getLocation());

		Action action = new ActionEventCard(interaction, playerList);
		action.perform(() -> cbExecuted = true);

		assertTrue(cbExecuted);
		assertEquals(chicagoCity, player.getLocation());
	}

	private void selectCardsFrom(int number, List<Card> cards, Consumer<List<Card>> callback) {
		assertTrue(cards.contains(evetCard));
		callback.accept(cardList);
	}
}
