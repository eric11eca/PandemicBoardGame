package test.game.cards.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.easymock.EasyMock;
import org.junit.Test;

import game.cards.Card;
import game.cards.Deck;
import game.cards.event.CardEventReused;
import game.city.City;
import game.event.Event;

public class TestCardEventReused {

	@Test(expected = UnsupportedOperationException.class)
	public void testAddToHand() {
		new CardEventReused(null).addToHand(null);
	}

	@Test
	public void testGetCity() {
		Card card = EasyMock.mock(Card.class);
		Optional<City> city = Optional.empty();
		EasyMock.expect(card.getCity()).andReturn(city);
		EasyMock.replay(card);
		CardEventReused uut = new CardEventReused(card);
		assertEquals(city, uut.getCity());
		EasyMock.verify(card);
	}

	@Test
	public void testGetEvent() {
		Card card = EasyMock.mock(Card.class);
		Optional<Event> event = Optional.empty();
		EasyMock.expect(card.getEvent()).andReturn(event);
		EasyMock.replay(card);
		CardEventReused uut = new CardEventReused(card);
		assertEquals(event, uut.getEvent());
		EasyMock.verify(card);
	}

	@Test
	public void testDiscard() {
		Card card = EasyMock.mock(Card.class);
		CardEventReused uut = new CardEventReused(card);
		Deck deck = new Deck();
		uut.discard(deck);
		assertTrue(deck.isEmpty());
	}

	@Test
	public void testEquals() {
		Card card1 = EasyMock.mock(Card.class);
		Card card2 = EasyMock.mock(Card.class);

		CardEventReused eventCard1 = new CardEventReused(card1);
		CardEventReused eventCard2 = new CardEventReused(card2);
		CardEventReused eventCard3 = new CardEventReused(card1);

		assertFalse(eventCard1.equals(null));
		assertFalse(eventCard1.equals(card1));
		assertFalse(eventCard1.equals(eventCard2));
		assertTrue(eventCard1.equals(eventCard3));

	}

}
