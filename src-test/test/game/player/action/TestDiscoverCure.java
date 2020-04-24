package test.game.player.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;

import game.GameColor;
import game.cards.Card;
import game.cards.CardCity;
import game.cards.Deck;
import game.city.City;
import game.player.Player;
import game.player.PlayerImpl;
import game.player.action.Action;
import game.player.action.ActionDiscoverCure;
import mock.MockCityBuilder;
import mock.MockInteraction;

public class TestDiscoverCure {
	private Player player;
	private City[] cities;

	private boolean cbExecuted;
	private MockInteraction interaction;

	Deck discard = new Deck();

	@Before
	public void setup() {
		cities = new City[5];
		for (int i = 0; i < cities.length; i++) {
			cities[i] = new MockCityBuilder().name("City" + i).color(GameColor.RED).build();
		}

		cbExecuted = false;
		interaction = new MockInteraction();
		interaction.implementSelectColorFrom((colors, callback) -> {
			assertTrue(colors.contains(GameColor.RED));
			callback.accept(GameColor.RED);
		});
		interaction.implementSelectCardsFrom(this::selectCardsFrom);

		player = new PlayerImpl(null, cities[0], discard, interaction);
	}

	@Test
	public void testDiscoverCure() {
		Set<GameColor> diseases = EnumSet.noneOf(GameColor.class);
		player.getLocation().buildResearchStation();
		player.receiveCard(cardList());

		Action action = new ActionDiscoverCure(player, interaction, diseases, 5);
		assertTrue(action.canPerform());
		action.perform(() -> cbExecuted = true);
		assertTrue(cbExecuted);
		assertTrue(diseases.contains(GameColor.RED));
		assertEquals(5, discard.size());
	}

	@Test
	public void testCannotPerformNoStation() {
		Action action = new ActionDiscoverCure(player, interaction, EnumSet.noneOf(GameColor.class), 5);
		assertFalse(action.canPerform());
	}

	@Test
	public void testCannotPerformNoCard() {
		player.getLocation().buildResearchStation();
		Action action = new ActionDiscoverCure(player, interaction, EnumSet.noneOf(GameColor.class), 5);
		assertFalse(action.canPerform());
	}

	@Test
	public void testCannotPerformAlreadyCured() {
		player.getLocation().buildResearchStation();
		player.receiveCard(cardList());
		Action action = new ActionDiscoverCure(player, interaction, EnumSet.of(GameColor.RED), 5);
		assertFalse(action.canPerform());
	}

	private void selectCardsFrom(int number, List<Card> cards, Consumer<List<Card>> callback) {
		assertEquals(5, number);
		for (City c : cities)
			assertTrue(cards.contains(new CardCity(c)));
		callback.accept(cardList());
	}

	private List<Card> cardList() {
		List<Card> list = new ArrayList<>();
		Arrays.stream(cities).map(CardCity::new).forEach(list::add);
		return list;
	}
}
