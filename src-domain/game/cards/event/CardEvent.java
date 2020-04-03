package game.cards.event;

import java.util.Optional;

import game.City;
import game.cards.Card;
import game.cards.Deck;
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

}
