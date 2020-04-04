package game.player;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import data.GameColor;
import game.cards.Card;
import game.city.City;

public interface PlayerInteraction {
	void selectColorFrom(Set<GameColor> colors, Consumer<GameColor> callback);

	void selectPlayerFrom(List<Player> players, Consumer<Player> callback);

	default void selectOneCardFrom(List<Card> cards, Consumer<Card> callback) {
		selectCardsFrom(1, cards, list -> callback.accept(list.get(0)));
	}

	void selectCardsFrom(int number, List<Card> cards, Consumer<List<Card>> callback);

	void selectCityFrom(Set<City> cities, Consumer<City> callback);

	void selectCardsToDiscard(int number, List<Card> cards, Consumer<List<Card>> callback);
}
