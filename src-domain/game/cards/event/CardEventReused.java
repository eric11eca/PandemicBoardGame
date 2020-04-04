package game.cards.event;

import java.util.Objects;
import java.util.Optional;

import game.cards.Card;
import game.cards.Deck;
import game.city.City;
import game.event.Event;

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
	public void discard(Deck<Card> discardPile) {
		// Do not add to discard pile
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

}
