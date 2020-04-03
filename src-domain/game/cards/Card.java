package game.cards;

import java.util.Optional;

import game.City;
import game.event.Event;
import game.player.PlayerImpl;

public interface Card {
	void addToHand(Deck<Card> hand);

	Optional<City> getCity();

	Optional<Event> getEvent();
}
