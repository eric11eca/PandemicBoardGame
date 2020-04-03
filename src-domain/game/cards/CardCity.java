package game.cards;

import java.util.Optional;

import game.City;
import game.event.Event;

public class CardCity implements Card {
	private City city;

	public CardCity(City city) {
		this.city = city;
	}

	@Override
	public void addToHand(Deck<Card> hand) {
		hand.putOnTop(this);
	}

	@Override
	public Optional<City> getCity() {
		return Optional.of(city);
	}

	@Override
	public Optional<Event> getEvent() {
		return Optional.empty();
	}

}
