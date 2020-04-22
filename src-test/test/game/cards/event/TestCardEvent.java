package test.game.cards.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.Test;

import game.cards.event.CardEvent;
import game.event.Event;

public class TestCardEvent {
	private Event mockEvent() {
		Event e = EasyMock.mock(Event.class);
		return e;
	}

	@Test
	public void testGetCity() {
		assertFalse(new CardEvent(mockEvent()).getCity().isPresent());
	}

	@Test
	public void testGetEvent() {
		Event e = mockEvent();
		assertTrue(new CardEvent(e).getEvent().isPresent());
		assertEquals(e, new CardEvent(e).getEvent().get());
	}
}
