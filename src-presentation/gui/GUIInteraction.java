package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import game.GameColor;
import game.cards.Card;
import game.cards.CardCity;
import game.city.City;
import game.player.Player;
import game.player.PlayerInteraction;
import gui.interaction.UICardArranger;
import gui.interaction.UICardChooser;
import gui.interaction.UIColorChooser;
import gui.interaction.UIPlayerChooser;
import render.Render;

public class GUIInteraction implements PlayerInteraction {
	private GameGUI gui;
	private Render render;

	public void initialize(GameGUI gui, Render render) {
		this.gui = gui;
		this.render = render;
	}

	@Override
	public void selectColorFrom(Set<GameColor> colors, Consumer<GameColor> callback) {
		UIColorChooser chooser = new UIColorChooser(colors, color -> {
			gui.hidePopup();
			callback.accept(color);
			gui.update();
		});
		gui.displayPopup(chooser);
	}

	@Override
	public void selectPlayerFrom(List<Player> players, Consumer<Player> callback) {
		UIPlayerChooser chooser = new UIPlayerChooser(players, player -> {
			gui.hidePopup();
			callback.accept(player);
			gui.update();
		});
		gui.displayPopup(chooser);
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
		List<Card> cityCardList = new ArrayList<>();
		cities.forEach(card -> cityCardList.add(new CardCity(card)));
		UICardChooser chooser = new UICardChooser("Select A City", 1, cityCardList, render, list -> {
			assert list.size() == 1;
			City chosen = list.get(0).getCity().get();
			gui.hidePopup();
			callback.accept(chosen);
			gui.update();
		}, true);
		gui.displayPopup(chooser);

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
		UICardArranger chooser = new UICardArranger("Please arrange. The first card will be on top of the deck", cards,
				render, list -> {
					gui.hidePopup();
					callback.accept(list);
					gui.update();
				});
		gui.displayPopup(chooser);
	}

}
