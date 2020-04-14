package gui;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import game.GameColor;
import game.cards.Card;
import game.city.City;
import game.player.Player;
import game.player.PlayerInteraction;

public class GUIInteraction implements PlayerInteraction {
	// TODO implement this
	@Override
	public void selectColorFrom(Set<GameColor> colors, Consumer<GameColor> callback) {
		callback.accept(colors.iterator().next());
	}

	@Override
	public void selectPlayerFrom(List<Player> players, Consumer<Player> callback) {
		callback.accept(players.get(0));
	}

	@Override
	public <T extends Card> void selectCardsFrom(int number, List<T> cards, Consumer<List<T>> callback) {

	}

	@Override
	public void selectCityFrom(Set<City> cities, Consumer<City> callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T extends Card> void selectCardsToDiscard(int number, List<T> cards, Consumer<List<T>> callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T extends Card> void arrangeCards(List<T> cards, Consumer<List<T>> callback) {
		// TODO Auto-generated method stub

	}

}
