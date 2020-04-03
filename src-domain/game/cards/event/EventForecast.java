package game.cards.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import data.Board;
import game.cards.CardCity;
import game.cards.Deck;

public abstract class EventForecast implements CardEvent {
	// TODO inject
	private static final int FORECAST_CARD_NUMBER = 0;
	/*
	 * Draw, look at and rearrange the top 6 infection cards, and put them back on
	 * the top
	 */
	private Deck<CardCity> infectionDeck;

	@Override
	public void executeEvent() {
		List<CardCity> toArrange = new ArrayList<>();
		for (int i = 0; i < FORECAST_CARD_NUMBER && !infectionDeck.isEmpty(); i++) {
			toArrange.add(infectionDeck.takeTopCard());
		}
		executeForecast(toArrange).ifPresent(this::putArrangedCardsBack);
	}

	private void putArrangedCardsBack(List<CardCity> arranged) {
		while (!arranged.isEmpty()) {
			infectionDeck.putOnTop(arranged.remove(arranged.size() - 1));
		}
	}

	protected abstract Optional<List<CardCity>> executeForecast(List<CardCity> toArrange);

}
