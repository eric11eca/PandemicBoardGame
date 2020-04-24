package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.JOptionPane;

import game.GameColor;
import game.cards.Card;
import game.cards.CardCity;
import game.city.City;
import game.player.Player;
import game.player.PlayerInteraction;
import gui.interaction.UICardArranger;
import gui.interaction.UICardChooser;
import gui.interaction.UIColorChooser;
import gui.interaction.UIDiscarder;
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
	public void selectColorFrom(Set<GameColor> colors, String title, Consumer<GameColor> callback) {
		UIColorChooser chooser = new UIColorChooser(title, colors, color -> {
			gui.hidePopup();
			callback.accept(color);
			gui.update();
		});
		gui.displayPopup(title, chooser);
	}

	@Override
	public void selectPlayerFrom(List<Player> players, String title, Consumer<Player> callback) {
		UIPlayerChooser chooser = new UIPlayerChooser(title, players, player -> {
			gui.hidePopup();
			callback.accept(player);
			gui.update();
		});
		gui.displayPopup(title, chooser);
	}

	@Override
	public void selectCardsFrom(int number, List<Card> cards, String title, Consumer<List<Card>> callback) {
		UICardChooser chooser = new UICardChooser(number, cards, render, list -> {
			gui.hidePopup();
			callback.accept(list);
			gui.update();
		}, true);
		gui.displayPopup(title, chooser);
	}

	@Override
	public void selectCityFrom(Set<City> cities, String title, Consumer<City> callback) {
		List<Card> cityCardList = new ArrayList<>();
		cities.forEach(card -> cityCardList.add(new CardCity(card)));
		UICardChooser chooser = new UICardChooser(1, cityCardList, render, list -> {
			assert list.size() == 1;
			City chosen = list.get(0).getCity().get();
			gui.hidePopup();
			callback.accept(chosen);
			gui.update();
		}, true);
		gui.displayPopup(title, chooser);

	}

	@Override
	public void arrangeCards(List<Card> cards, String title, Consumer<List<Card>> callback) {
		UICardArranger chooser = new UICardArranger(cards, render, list -> {
			gui.hidePopup();
			callback.accept(list);
			gui.update();
		});
		gui.displayPopup(title, chooser);
	}

	@Override
	public void displayCards(List<Card> cards, String title) {
		UICardChooser chooser = new UICardChooser(0, cards, render, list -> {
			gui.hidePopup();
			gui.update();
		}, false);
		gui.displayPopup(title, chooser);
	}

	@Override
	public List<Card> selectCardsToDiscard(int number, List<Card> cards, String title) {
		gui.hidePopup();
		UIDiscarder discarder = new UIDiscarder(number, cards, render);
		JOptionPane.showMessageDialog(gui.getGameFrame(), discarder, title, JOptionPane.PLAIN_MESSAGE);
		return discarder.getChosenCards();
	}

}
