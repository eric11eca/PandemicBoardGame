package game.cards;

import java.util.Optional;

import game.Epidemic;
import game.city.City;
import game.event.Event;

public class CardEpidemic implements Card {
	private Epidemic epidemic;

	public CardEpidemic(Epidemic epidemic) {
		this.epidemic = epidemic;
	}

	@Override
	public void addToHand(Deck<Card> hand) {
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
	public <T extends Card> void discard(Deck<T> discardPile, Class<T> type) {
		discardPile.putOnTop(type.cast(this));
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
