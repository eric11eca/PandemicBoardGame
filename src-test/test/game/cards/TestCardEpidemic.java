package test.game.cards;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import game.Epidemic;
import game.cards.CardEpidemic;
import game.cards.Deck;

public class TestCardEpidemic {
	CardEpidemic card;
	boolean epidemicTriggered;

	@Before
	public void setup() {
		Epidemic epidemic = EasyMock.mock(Epidemic.class);
		card = new CardEpidemic(epidemic);
		epidemic.triggerEpidemic();
		EasyMock.expectLastCall().andAnswer(() -> epidemicTriggered = true);
		epidemicTriggered = false;
		EasyMock.replay(epidemic);
	}

	@Test
	public void testAddToHand() {
		Deck deck = new Deck();
		assertFalse(epidemicTriggered);
		card.addToHand(deck);
		assertTrue(epidemicTriggered);
		assertTrue(deck.isEmpty());
	}

	@Test
	public void testGetCity() {
		assertFalse(card.getCity().isPresent());
	}

	@Test
	public void testGetEvent() {
		assertFalse(card.getEvent().isPresent());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testDiscard() {
		card.discard(null);
	}

	@Test
	public void testEquals() {
		assertFalse(card.equals(null));
		assertTrue(card.equals(new CardEpidemic(null)));
	}

	@Test
	public void testHashCode() {
		assertEquals("epidemic".hashCode(), card.hashCode());
	}
}
