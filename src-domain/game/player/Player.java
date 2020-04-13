package game.player;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import game.cards.Card;
import game.city.City;

/**
 * The player interface. A Player encapsulates its location and hand.
 */
public interface Player {
	/**
	 * Receives a card. If the player's hand is over the hand limit, discard must be
	 * triggered.
	 * 
	 * @param card card to receive
	 */
	default void receiveCard(Card card) {
		receiveCard(Arrays.asList(card));
	}

	/**
	 * Receives multiple cards. If the player's hand is over the hand limit, discard
	 * must be and must only be triggered after the player receives all cards
	 * 
	 * @param cards cards to receive
	 */
	void receiveCard(List<Card> cards);

	/**
	 * Remove a card from the player's hand
	 * 
	 * @param toRemove the card to remove
	 * @throws RuntimeException if the card to remove is not held by the player
	 */
	void removeCard(Card toRemove);

	/**
	 * Remove and attempt to discard a card. See {@link Card}
	 * 
	 * @param toDiscard
	 */
	void discardCard(Card toDiscard);

	/**
	 * Discard multiple cards
	 * 
	 * @param toDiscard
	 */
	default void discardCards(List<Card> toDiscard) {
		toDiscard.forEach(this::discardCard);
	}

	void setLocation(City destination);

	City getLocation();

	/**
	 * Apply a filter to the player's hand. The returned list should be safe to
	 * modify without changing the player's hand.
	 * 
	 * @param filter the filter to apply
	 */
	List<Card> getFilteredHand(Predicate<? super Card> filter);

	/**
	 * Get the cards that this player can GIVE to the receiver during share
	 * knowledge action
	 * 
	 * @param receiver the player who is receiving the cards
	 * @return
	 */
	List<Card> getSharableKnowledgeCards(Player receiver);

	int getID();

	int getHighestPopulationInHand();
//	
//	int getCardNumberInHand();
//	
//	boolean handContains(Card card);
//	boolean discardedContains(Card card);

}