package test.game.cards.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.Test;

import game.cards.event.CardEvent;
import game.event.Event;
import game.player.PlayerInteraction;

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

	@Test
	public void testHashCode() {
		Event e = mockEvent();
		assertEquals(e.getClass().hashCode(), new CardEvent(e).hashCode());
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEquals() {
		Event e1 = mockEvent();
		Event e2 = new Event() {

			@Override
			public void executeEvent(PlayerInteraction interaction) {
				// TODO Auto-generated method stub

			}
		};

		CardEvent eventCard1 = new CardEvent(e1);
		CardEvent eventCard2 = new CardEvent(e2);
		CardEvent eventCard3 = new CardEvent(e1);

		assertFalse(eventCard1.equals(null));
		assertFalse(eventCard1.equals("not event"));
		assertFalse(eventCard1.equals(eventCard2));
		assertTrue(eventCard1.equals(eventCard3));

	}
}
