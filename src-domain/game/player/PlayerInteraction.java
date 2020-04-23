package game.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import game.GameColor;
import game.cards.Card;
import game.cards.CardCity;
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
	 * @param cards    list of cards to select from
	 * @param callback callback function with selected card
	 */
	default void selectOneCardFrom(List<Card> cards, Consumer<Card> callback) {
		selectCardsFrom(1, cards, list -> callback.accept(list.get(0)));
	}

	/**
	 * Asynchronously select many cards from a list of cards. The callback will only
	 * be called if a valid selection with the correct number of cards is made.
	 * 
	 * @param number   the number of cards to choose
	 * @param cards    cards to choose from
	 * @param callback callback function with selected cards
	 */
	void selectCardsFrom(int number, List<Card> cards, Consumer<List<Card>> callback);

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
	 * @param number   maximum number of cards to select
	 * @param cards    cards to select from
	 * @param callback callback function with the selected cards to discard
	 */
	@Deprecated
	default void selectCardsToDiscard(int number, List<Card> cards, Consumer<List<Card>> callback) {
	}

	List<Card> selectCardsToDiscard(int number, List<Card> cards);

	/**
	 * Asynchronously arrange many cards. The callback function must provide a new
	 * data structure that will not affect the provided list of cards when modified
	 * 
	 * @param cards    cards to arrange
	 * @param callback callback function with the arranged list of cards
	 */
	void arrangeCards(List<Card> cards, Consumer<List<Card>> callback);

	/**
	 * Display cities to the user without blocking
	 * 
	 * @param cities
	 */
	default void displayCities(Set<City> cities) {
		List<Card> cityCardList = new ArrayList<>();
		cities.forEach(card -> cityCardList.add(new CardCity(card)));
		displayCards(cityCardList);
	}

	/**
	 * Display cards to the user without blocking
	 * 
	 */
	void displayCards(List<Card> cards);
}
