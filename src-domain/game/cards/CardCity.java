package game.cards;

import java.util.Objects;
import java.util.Optional;

import game.city.City;
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

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass() == getClass() && Objects.equals(((CardCity) obj).city, city);
	}

	@Override
	public int hashCode() {
		return Objects.hash(city);
	}

}
