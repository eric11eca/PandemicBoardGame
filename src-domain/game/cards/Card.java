package game.cards;

import java.util.Optional;

import game.city.City;
import game.event.Event;
import game.player.PlayerImpl;

public interface Card {
	void addToHand(Deck<Card> hand);

	<T extends Card> void discard(Deck<T> discardPile, Class<T> type);

	Optional<City> getCity();

	Optional<Event> getEvent();
}
