package test.game.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import game.cards.Card;
import game.cards.CardCity;
import game.cards.Deck;
import game.city.City;
import game.player.PlayerImpl;
import game.player.PlayerRole;
import mock.MockCityBuilder;
import mock.MockGameProperty;
import mock.MockInteraction;

public class TestPlayerImpl {
	private City city1, city2, city3;

	@After
	public void cleanProperty() {
		new MockGameProperty().resetAndEject();
	}

	@Before
	public void setup() {
		city1 = new MockCityBuilder().name("1").population(123).build();
		city2 = new MockCityBuilder().name("2").population(456).build();
		city3 = new MockCityBuilder().name("3").population(600).build();
	}

	@Test
	public void testHighestPopulationInHand() {
		PlayerImpl player = new PlayerImpl(null, null, null, null);
		assertEquals(0, player.getHighestPopulationInHand());
		player.receiveCard(Arrays.asList(new CardCity(city1), new CardCity(city2), new CardCity(city3)));
		assertEquals(600, player.getHighestPopulationInHand());
	}

	@Test
	public void testReceiveCardNoDiscard() {

		PlayerImpl player = new PlayerImpl(null, null, null, null);
		player.receiveCard(Arrays.asList(new CardCity(city1), new CardCity(city2), new CardCity(city3)));
		List<Card> hand = player.getFilteredHand(c -> true);
		assertEquals(3, hand.size());
		assertTrue(hand.contains(new CardCity(city1)));
		assertTrue(hand.contains(new CardCity(city2)));
		assertTrue(hand.contains(new CardCity(city3)));
	}

	@Test
	public void testReceiveCardDiscard() {
		new MockGameProperty().put("HAND_LIMIT", "2").inject();
		MockInteraction interaction = new MockInteraction().implementSelectCardsToDiscard((n, cards) -> {
			assertEquals(1, (int) n);
			assertEquals(3, cards.size());
			assertTrue(cards.contains(new CardCity(city1)));
			assertTrue(cards.contains(new CardCity(city2)));
			assertTrue(cards.contains(new CardCity(city3)));
			return Arrays.asList(new CardCity(city1));
		});
		Deck discard = EasyMock.mock(Deck.class);
		PlayerImpl player = new PlayerImpl(null, null, discard, interaction);

		discard.putOnTop(new CardCity(city1));
		EasyMock.expectLastCall().andVoid();
		EasyMock.replay(discard);
		player.receiveCard(Arrays.asList(new CardCity(city1), new CardCity(city2), new CardCity(city3)));
		EasyMock.verify(discard);
		interaction.verifyDiscardCardsCalled(1);

		List<Card> hand = player.getFilteredHand(c -> true);
		assertEquals(2, hand.size());
		assertFalse(hand.contains(new CardCity(city1)));
		assertTrue(hand.contains(new CardCity(city2)));
		assertTrue(hand.contains(new CardCity(city3)));
	}

	@Test
	public void testRemoveCard() {
		PlayerImpl player = new PlayerImpl(null, null, null, null);
		player.receiveCard(new CardCity(city1));
		player.removeCard(new CardCity(city1));
		assertTrue(player.getFilteredHand(c -> true).isEmpty());
	}

	@Test(expected = RuntimeException.class)
	public void testRemoveCardFail() {
		PlayerImpl player = new PlayerImpl(null, null, null, null);
		player.removeCard(new CardCity(city1));
		assertTrue(player.getFilteredHand(c -> true).isEmpty());
	}

	@Test
	public void testDiscardCard() {
		Deck discard = EasyMock.mock(Deck.class);
		discard.putOnTop(new CardCity(city1));
		EasyMock.expectLastCall().andVoid();
		EasyMock.replay(discard);
		PlayerImpl player = new PlayerImpl(null, null, discard, null);
		player.receiveCard(new CardCity(city1));
		player.discardCard(new CardCity(city1));
		assertTrue(player.getFilteredHand(c -> true).isEmpty());
		EasyMock.verify(discard);
	}

	@Test
	public void testGetLocation() {
		PlayerImpl player = new PlayerImpl(null, city1, null, null);
		assertEquals(city1, player.getLocation());
	}

	@Test
	public void testSetLocation() {
		PlayerImpl player = new PlayerImpl(null, city1, null, null);
		player.setLocation(city2);
		assertEquals(city2, player.getLocation());
	}

	@Test
	public void testPlayerRole() {
		PlayerImpl player = new PlayerImpl(PlayerRole.CONTINGENCY_PLANNER, null, null, null);
		assertEquals(PlayerRole.CONTINGENCY_PLANNER, player.getRole());
	}

	@Test
	public void testFilteredHand() {
		PlayerImpl player = new PlayerImpl(null, null, null, null);
		player.receiveCard(Arrays.asList(new CardCity(city1), new CardCity(city2), new CardCity(city3)));
		List<Card> filter = player.getFilteredHand(c -> c.equals(new CardCity(city1)));
		assertEquals(1, filter.size());
		assertTrue(filter.contains(new CardCity(city1)));

		filter = player.getFilteredHand(c -> !c.equals(new CardCity(city1)));
		assertEquals(2, filter.size());
		assertTrue(filter.contains(new CardCity(city2)));
		assertTrue(filter.contains(new CardCity(city3)));
	}

	@Test
	public void testSharableKnowledgeDifferentCity() {
		PlayerImpl player1 = new PlayerImpl(null, city1, null, null);
		PlayerImpl player2 = new PlayerImpl(null, city2, null, null);
		assertTrue(player1.getSharableKnowledgeCards(player2).isEmpty());
		assertTrue(player2.getSharableKnowledgeCards(player1).isEmpty());
	}

	@Test
	public void testSharableKnowledgeNoCard() {
		PlayerImpl player1 = new PlayerImpl(null, city1, null, null);
		PlayerImpl player2 = new PlayerImpl(null, city1, null, null);
		player1.receiveCard(new CardCity(city2));
		player2.receiveCard(new CardCity(city3));
		assertTrue(player1.getSharableKnowledgeCards(player2).isEmpty());
		assertTrue(player2.getSharableKnowledgeCards(player1).isEmpty());
	}

	@Test
	public void testSharableKnowledgeNonEmpty() {
		PlayerImpl player1 = new PlayerImpl(null, city1, null, null);
		PlayerImpl player2 = new PlayerImpl(null, city1, null, null);
		player1.receiveCard(new CardCity(city1));
		player1.receiveCard(new CardCity(city2));
		player2.receiveCard(new CardCity(city3));
		assertTrue(player2.getSharableKnowledgeCards(player1).isEmpty());
		List<Card> list = player1.getSharableKnowledgeCards(player2);
		assertEquals(1, list.size());
		assertTrue(list.contains(new CardCity(city1)));
	}

}
