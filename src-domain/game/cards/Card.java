package game.cards;

import java.util.Optional;

import game.City;
import game.cards.event.CardEvent;
import game.player.PlayerImpl;

public interface Card {
	void addToHand(Deck<Card> hand);

	Optional<City> getCity();

	Optional<CardEvent> getEvent();
}
