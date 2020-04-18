package gui;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import game.GameColor;
import game.cards.Card;
import game.city.City;
import game.player.Player;
import game.player.PlayerInteraction;
import gui.interaction.UICardChooser;
import render.Render;

public class GUIInteraction implements PlayerInteraction {
	private GameGUI gui;
	private Render render;

	public void initialize(GameGUI gui, Render render) {
		this.gui = gui;
		this.render = render;
	}

	// TODO implement this
	@Override
	public void selectColorFrom(Set<GameColor> colors, Consumer<GameColor> callback) {
		// callback.accept(colors.iterator().next());
	}

	@Override
	public void selectPlayerFrom(List<Player> players, Consumer<Player> callback) {
		// callback.accept(players.get(0));
	}

	@Override
	public void selectCardsFrom(int number, List<Card> cards, Consumer<List<Card>> callback) {
		UICardChooser chooser = new UICardChooser("Select Cards", number, cards, render, list -> {
			gui.hidePopup();
			callback.accept(list);
			gui.update();
		}, true);
		gui.displayPopup(chooser);
	}

	@Override
	public void selectCityFrom(Set<City> cities, Consumer<City> callback) {
		// callback.accept(cities.iterator().next());

	}

	@Override
	public void selectCardsToDiscard(int number, List<Card> cards, Consumer<List<Card>> callback) {
		UICardChooser chooser = new UICardChooser("Select Cards To Discard", number, cards, render, list -> {
			gui.hidePopup();
			callback.accept(list);
			gui.update();
		}, false);
		gui.displayPopup(chooser);
	}

	@Override
	public void arrangeCards(List<Card> cards, Consumer<List<Card>> callback) {
		// TODO Auto-generated method stub

	}

}
