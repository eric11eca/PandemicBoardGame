package test.game.cards;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import game.cards.Card;
import game.cards.Deck;

public class TestDeck {
	Deck deck;

	@Before
	public void setup() {
		deck = new Deck();
	}

	private Card mockCard() {
		Card card = EasyMock.mock(Card.class);
		// EasyMock.expect(card.equals(card)).andReturn(true);
		return card;
	}

	@Test
	public void testSize() {
		for (int i = 0; i < 10; i++) {
			assertEquals(i, deck.size());
			deck.putOnTop(mockCard());
		}
	}

	@Test
	public void testIsEmpty() {
		assertTrue(deck.isEmpty());
		deck.putOnTop(mockCard());
		assertFalse(deck.isEmpty());
	}

	@Test
	public void testClear() {
		assertTrue(deck.isEmpty());
		deck.putOnTop(mockCard());
		deck.putOnTop(mockCard());
		deck.clear();
		assertTrue(deck.isEmpty());
	}

	@Test
	public void testContains() {
		Card card = mockCard();
		assertFalse(deck.contains(card));
		deck.putOnTop(mockCard());
		assertFalse(deck.contains(card));
		deck.putOnTop(card);
		assertTrue(deck.contains(card));
	}

	@Test
	public void testRemoveCard() {
		Card card = mockCard();
		assertFalse(deck.removeCard(card));
		deck.putOnTop(mockCard());
		assertFalse(deck.removeCard(card));
		deck.putOnTop(card);
		assertTrue(deck.removeCard(card));
		assertFalse(deck.removeCard(card));
		assertFalse(deck.removeCard(mockCard()));
	}

	@Test
	public void testPutOnTop() {
		Card[] cards = { mockCard(), mockCard(), mockCard() };
		for (int i = 0; i < cards.length; i++) {
			deck.putOnTop(cards[i]);
		}
		for (int i = cards.length - 1; i >= 0; i--) {
			assertEquals(cards[i], deck.takeTopCard());
		}
	}

	@Test
	public void testPutAllOnTop() {
		Card[] cards = { mockCard(), mockCard(), mockCard() };
		deck.putAllOnTop(Arrays.asList(cards));
		for (int i = cards.length - 1; i >= 0; i--) {
			assertEquals(cards[i], deck.takeTopCard());
		}
	}

	@Test
	public void testTakeTopCard() {
		Card card = mockCard();
		deck.putOnTop(card);
		assertEquals(card, deck.takeTopCard());
	}

	@Test
	public void testPutOnBottom() {
		Card[] cards = { mockCard(), mockCard(), mockCard() };
		for (int i = 0; i < cards.length; i++) {
			deck.putOnBottom(cards[i]);
		}
		for (int i = 0; i < cards.length; i++) {
			assertEquals(cards[i], deck.takeTopCard());
		}
	}

	@Test
	public void testTakeBottomCard() {
		Card card = mockCard();
		deck.putOnTop(card);
		assertEquals(card, deck.takeBottomCard());
	}

	@Test
	public void testToList() {
		Card[] cards = { mockCard(), mockCard(), mockCard() };
		deck.putAllOnTop(Arrays.asList(cards));
		List<Card> list = deck.toList();
		Card[] expected = { cards[2], cards[1], cards[0] };
		Iterator<Card> list1Iter = list.iterator();
		assertEquals(3, list.size());
		for (Card e : expected) {
			assertEquals(e, list1Iter.next());
		}
	}

	@Test
	public void getFilteredSubDeck() {
		Card[] cards = { mockCard(), mockCard(), mockCard() };
		deck.putAllOnTop(Arrays.asList(cards));
		List<Card> filter = deck.getFilteredSubDeck(cards[0]::equals);
		assertEquals(1, filter.size());
		assertTrue(filter.get(0).equals(cards[0]));
	}

	int count;

	@Test
	public void testForEach() {
		Card[] cards = { mockCard(), mockCard(), mockCard() };
		deck.putAllOnTop(Arrays.asList(cards));
		count = 0;
		deck.forEach(c -> count++);
		assertEquals(3, count);
	}

	@Test
	public void testShuffle() {
		Card[] cards = { mockCard(), mockCard(), mockCard() };
		deck.putAllOnTop(Arrays.asList(cards));
		deck.shuffle();
		List<Card> list = deck.toList();
		BitSet matched = new BitSet();
		for (Card c : list) {
			for (int i = 0; i < cards.length; i++) {
				if (c.equals(cards[i])) {
					assertFalse(matched.get(i));
					matched.set(i);
				}
			}
		}
		for (int i = 0; i < cards.length; i++) {
			assertTrue(matched.get(i));
		}
	}
}
