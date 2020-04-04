package game.cards.event;

import java.util.Objects;
import java.util.Optional;

import game.cards.Card;
import game.cards.Deck;
import game.city.City;
import game.event.Event;

public class CardEvent implements Card {
	private Event event;

	@Override
	public void addToHand(Deck<Card> hand) {
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
	public <T extends Card> void discard(Deck<T> discardPile, Class<T> type) {
		discardPile.putOnTop(type.cast(this));
	}

}
