package game.player;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import game.GameColor;
import game.cards.Card;
import game.city.City;

public interface PlayerInteraction {
	void selectColorFrom(Set<GameColor> colors, Consumer<GameColor> callback);

	void selectPlayerFrom(List<Player> players, Consumer<Player> callback);

	default <T extends Card> void selectOneCardFrom(List<T> cards, Consumer<T> callback) {
		selectCardsFrom(1, cards, list -> callback.accept(list.get(0)));
	}

	<T extends Card> void selectCardsFrom(int number, List<T> cards, Consumer<List<T>> callback);

	void selectCityFrom(Set<City> cities, Consumer<City> callback);

	<T extends Card> void selectCardsToDiscard(int number, List<T> cards, Consumer<List<T>> callback);

	<T extends Card> void arrangeCards(List<T> cards, Consumer<List<T>> callback);
}
