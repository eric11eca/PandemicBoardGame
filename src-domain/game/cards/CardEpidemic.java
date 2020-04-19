package game.cards;

import java.util.Optional;

import game.Epidemic;
import game.city.City;
import game.event.Event;

/**
 * An epidemic card, which triggers epidemic when draw instead of going to the
 * player's hand. See {@link Epidemic}
 */
public class CardEpidemic implements Card {
	private Epidemic epidemic;

	public CardEpidemic(Epidemic epidemic) {
		this.epidemic = epidemic;
	}

	@Override
	public void addToHand(Deck hand) {
		epidemic.triggerEpidemic();
	}

	@Override
	public Optional<City> getCity() {
		return Optional.empty();
	}

	@Override
	public Optional<Event> getEvent() {
		return Optional.empty();
	}

	@Override
	public void discard(Deck discardPile) {
		throw new UnsupportedOperationException("Cannot Discard Epidemic Cards");
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof CardEpidemic;
	}

	@Override
	public int hashCode() {
		return "epidemic".hashCode();
	}

}
