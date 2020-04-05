package game.player;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import game.GameColor;
import game.cards.Card;
import game.city.City;

/**
 * An interface for asynchronous game mechanics, especially when user input is
 * needed. In each of the methods, the callback function is optionally invoked.
 * That is, the source of decision could deny the action and stops the current
 * game mechanics.
 */
public interface PlayerInteraction {
	/**
	 * Asynchronously select a color from a set of colors
	 * 
	 * @param colors   set of colors to select from
	 * @param callback callback function with selected color
	 */
	void selectColorFrom(Set<GameColor> colors, Consumer<GameColor> callback);

	/**
	 * Asynchronously select a player from a list of players
	 * 
	 * @param players  list of players to select from
	 * @param callback callback function with selected player
	 */
	void selectPlayerFrom(List<Player> players, Consumer<Player> callback);

	/**
	 * Asynchronously select a card from a list of cards
	 * 
	 * @param <T>      type of the card
	 * @param cards    list of cards to select from
	 * @param callback callback function with selected card
	 */
	default <T extends Card> void selectOneCardFrom(List<T> cards, Consumer<T> callback) {
		selectCardsFrom(1, cards, list -> callback.accept(list.get(0)));
	}

	/**
	 * Asynchronously select many cards from a list of cards. The callback will only
	 * be called if a valid selection with the correct number of cards is made.
	 * 
	 * @param <T>      type of cards
	 * @param number   the number of cards to choose
	 * @param cards    cards to choose from
	 * @param callback callback function with selected cards
	 */
	<T extends Card> void selectCardsFrom(int number, List<T> cards, Consumer<List<T>> callback);

	/**
	 * Asynchronously select a city from the set of cities
	 * 
	 * @param cities   the cities to select from
	 * @param callback callback function with the selected city
	 */
	void selectCityFrom(Set<City> cities, Consumer<City> callback);

	/**
	 * Asynchronously select many cards to discard from a list of cards. This method
	 * may seem similar to {@code selectCardsFrom}, but this method allows selection
	 * less than or equal to the number specified (not over)
	 * 
	 * @param <T>      type of card
	 * @param number   maximum number of cards to select
	 * @param cards    cards to select from
	 * @param callback callback function with the selected cards to discard
	 */
	<T extends Card> void selectCardsToDiscard(int number, List<T> cards, Consumer<List<T>> callback);

	/**
	 * Asynchronously arrange many cards. The callback function must provide a new
	 * data structure that will not affect the provided list of cards when modified
	 * 
	 * @param <T>      type of card
	 * @param cards    cards to arrange
	 * @param callback callback function with the arranged list of cards
	 */
	<T extends Card> void arrangeCards(List<T> cards, Consumer<List<T>> callback);
}
