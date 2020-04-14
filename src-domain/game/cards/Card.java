package game.cards;

import java.util.Optional;

import game.city.City;
import game.event.Event;

/**
 * Abstract of a card
 */
public interface Card {
	/**
	 * Called to add this card to the player's hand. It is up to the implementation
	 * whether the card is actually added to the hand (for example, epidemic cards
	 * are not actually added to hand)
	 * 
	 * @param hand a player's hand
	 */
	void addToHand(Deck hand);

	/**
	 * Discard this card to the discard pile. It is up to the implementation whether
	 * the card is actually added to the discard pile
	 * 
	 * @param discardPile the discard pile to discard to
	 */
	void discard(Deck discardPile);

	/**
	 * Get the city associated with this card. For player city cards and infection
	 * cards, return the city shown on the card. For event card, this should return
	 * {@code Optional.empty()}
	 */
	Optional<City> getCity();

	/**
	 * Get the event associated with this card, which is only valid if this card is
	 * an event card. See also {@code getCity()}
	 */
	Optional<Event> getEvent();
}
