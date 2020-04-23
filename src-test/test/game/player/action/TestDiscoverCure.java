package test.game.player.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
	Player player;
	City city1, city2, city3, city4, city5;
	Card cityCard1, cityCard2, cityCard3, cityCard4, cityCard5;

	MockCityBuilder cityFactory = new MockCityBuilder();
	boolean cbExecuted;
	MockInteraction interaction;
	
	List<Card> cardList = new ArrayList<>();
	Deck discard = new Deck();

	@Before
	public void setup() {
		MockCityBuilder city1Builder = new MockCityBuilder().name("City1");
		city1Builder = city1Builder.color(GameColor.RED);
		city1 = city1Builder.build();
		MockCityBuilder city2Builder = new MockCityBuilder().name("City2");
		city2Builder = city2Builder.color(GameColor.RED);
		city2 = city2Builder.build();
		MockCityBuilder city3Builder = new MockCityBuilder().name("City3");
		city3Builder = city3Builder.color(GameColor.RED);
		city3 = city3Builder.build();
		MockCityBuilder city4Builder = new MockCityBuilder().name("City4");
		city4Builder = city4Builder.color(GameColor.RED);
		city4 = city4Builder.build();
		MockCityBuilder city5Builder = new MockCityBuilder().name("City5");
		city5Builder = city5Builder.color(GameColor.RED);
		city5 = city5Builder.build();
		
		cityCard1 = new CardCity(city1);
		cityCard2 = new CardCity(city2);
		cityCard3 = new CardCity(city3);
		cityCard4 = new CardCity(city4);
		cityCard5 = new CardCity(city5);
		
		cardList.addAll(Arrays.asList(new Card[]{cityCard1, cityCard2, cityCard3, cityCard4, cityCard5}));

		cbExecuted = false;
		interaction = new MockInteraction();
		interaction.implementSelectColorFrom((colors, callback)->{
			assertTrue(colors.contains(GameColor.RED));
			callback.accept(GameColor.RED);
		});
		interaction.implementSelectCardsFrom(this::selectCardsFrom);
		
		player = new PlayerImpl(null, city1, discard, interaction);
		player.receiveCard(cardList);
	}

	@Test
	public void testDiscoverCure() {
		Set<GameColor> diseases = new HashSet<>();
		diseases.add(GameColor.RED);

		Action action = new ActionDiscoverCure(player, interaction, diseases, 5);
		action.perform(() -> cbExecuted = true);
		assertTrue(cbExecuted);
		assertEquals(5, discard.size());
	}
	
	private void selectCardsFrom(int number, List<Card> cards, Consumer<List<Card>> callback) {
		assertTrue(cards.containsAll(cardList));
		callback.accept(cardList);
	}
}
