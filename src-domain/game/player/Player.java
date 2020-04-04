package game.player;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import game.cards.Card;
import game.city.City;

public interface Player {
	default void receiveCard(Card card) {
		receiveCard(Arrays.asList(card));
	}

	void receiveCard(List<Card> cards);

	// void removeCards(List<Card> toRemove);

	void removeCard(Card toRemove);

	void discardCard(Card toDiscard);

	default void discardCards(List<Card> toDiscard) {
		toDiscard.forEach(this::discardCard);
	}

	void setLocation(City destination);

	City getLocation();

	List<Card> getFilteredHand(Predicate<? super Card> filter);

}