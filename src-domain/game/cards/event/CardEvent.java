package game.cards.event;

import java.util.Objects;
import java.util.Optional;

import game.cards.Card;
import game.cards.Deck;
import game.city.City;
import game.event.Event;

/**
 * Event Card. See {@link Event}
 *
 */
public class CardEvent implements Card {
	private Event event;

	public CardEvent(Event event) {
		super();
		this.event = Objects.requireNonNull(event);
	}

	@Override
	public void addToHand(Deck hand) {
		hand.putOnTop(this);
	}

	@Override
	public Optional<City> getCity() {
		return Optional.empty();
	}

	@Override
	public Optional<Event> getEvent() {
		return Optional.of(event);
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass() == getClass()
				&& Objects.equals(((CardEvent) obj).event.getClass(), event.getClass());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(event.getClass());
	}

	@Override
	public void discard(Deck discardPile) {
		discardPile.putOnTop(this);
	}

}
