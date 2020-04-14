package game.event;

import java.util.ArrayList;
import java.util.List;

import data.GameProperty;
import game.cards.Card;
import game.cards.Deck;
import game.player.PlayerInteraction;

public class EventForecast implements Event {
	/*
	 * Draw, look at and rearrange the top 6 infection cards, and put them back on
	 * the top
	 */
	private Deck infectionDeck;

	public EventForecast(Deck infectionDeck) {
		super();
		this.infectionDeck = infectionDeck;
	}

	@Override
	public void executeEvent(PlayerInteraction interaction) {
		List<Card> toArrange = new ArrayList<>();
		final int FORECAST_CARD_NUMBER = GameProperty.getInstance().getInt("FORECAST_CARD_NUMBER");
		for (int i = 0; i < FORECAST_CARD_NUMBER && !infectionDeck.isEmpty(); i++) {
			toArrange.add(infectionDeck.takeTopCard());
		}
		interaction.arrangeCards(toArrange, this::putArrangedCardsBack);
	}

	private void putArrangedCardsBack(List<Card> arranged) {
		while (!arranged.isEmpty()) {
			infectionDeck.putOnTop(arranged.remove(arranged.size() - 1));
		}
	}

}
