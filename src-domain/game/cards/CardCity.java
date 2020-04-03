package game.cards;

import java.util.Optional;

import game.City;
import game.cards.event.CardEvent;
import game.player.PlayerImpl;

public class CardCity implements Card {
	private City city;

	public CardCity(City city) {
		this.city = city;
	}

	@Override
	public void addToHand(Deck<Card> hand) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<City> getCity() {
		return Optional.of(city);
	}

	@Override
	public Optional<CardEvent> getEvent() {
		return Optional.empty();
	}

}
