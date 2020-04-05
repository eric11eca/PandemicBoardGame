package game.cards.event;

import java.util.Objects;
import java.util.Optional;

import game.cards.Card;
import game.cards.Deck;
import game.city.City;
import game.event.Event;

/**
 * A decorator that removes the event card played from the game, instead of
 * adding it to the discard pile. Used for contingency planner
 *
 */
public class CardEventReused implements Card {
	private Card delegate;

	public CardEventReused(Card delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public void addToHand(Deck<Card> hand) {
		delegate.addToHand(hand);
	}

	@Override
	public Optional<City> getCity() {
		return delegate.getCity();
	}

	@Override
	public Optional<Event> getEvent() {
		return delegate.getEvent();
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass() == getClass()
				&& Objects.equals(delegate, ((CardEventReused) obj).delegate);
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	@Override
	public <T extends Card> void discard(Deck<T> discardPile, Class<T> type) {
		// Do not add to discard pile
	}

}
