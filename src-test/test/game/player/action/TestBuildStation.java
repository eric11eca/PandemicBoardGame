package test.game.player.action;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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
import game.player.PlayerRole;
import game.player.action.Action;
import game.player.action.ActionBuildStation;
import mock.MockCityBuilder;
import mock.MockGameProperty;
import mock.MockInteraction;

public class TestBuildStation {
	Player player;
	City newyorkCity;

	MockCityBuilder cityFactory = new MockCityBuilder();
	boolean cbExecuted;
	MockInteraction interaction;

	Card newyorkCard;
	List<Card> cardList;
	Deck discard;

	@Before
	public void setup() {
		MockCityBuilder newyorkBuilder = new MockCityBuilder().name("NewYork");
		newyorkCity = newyorkBuilder.build();

		newyorkCard = new CardCity(newyorkCity);
		cardList = new ArrayList<>();
		cardList.add(newyorkCard);

		cbExecuted = false;
		interaction = new MockInteraction();
		interaction.implementSelectCardsFrom(this::selectCardsFrom);

		discard = new Deck();
		player = new PlayerImpl(null, newyorkCity, discard, interaction);
		player.receiveCard(newyorkCard);
	}

	@Test
	public void testBuildResearchStation() {
		Set<City> citySet = new HashSet<>();
		citySet.add(newyorkCity);

		assertFalse(newyorkCity.hasResearchStation());
		Action action = new ActionBuildStation(player, interaction, true, new CitySet(citySet));
		action.perform(() -> cbExecuted = true);
		assertTrue(cbExecuted);
		assertTrue(newyorkCity.hasResearchStation());
		assertTrue(discard.contains(newyorkCard));
	}

	private void selectCardsFrom(int number, List<Card> cards, Consumer<List<Card>> callback) {
		assertTrue(cards.contains(newyorkCard));
		callback.accept(Arrays.asList(newyorkCard));
	}
}
