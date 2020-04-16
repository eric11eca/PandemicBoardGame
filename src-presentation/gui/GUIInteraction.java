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
	public void selectCardsFrom(int number, List<Card> cards, Consumer<List<Card>> callback) {

	}

	@Override
	public void selectCityFrom(Set<City> cities, Consumer<City> callback) {
		callback.accept(cities.iterator().next());

	}

	@Override
	public void selectCardsToDiscard(int number, List<Card> cards, Consumer<List<Card>> callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void arrangeCards(List<Card> cards, Consumer<List<Card>> callback) {
		// TODO Auto-generated method stub

	}

}
