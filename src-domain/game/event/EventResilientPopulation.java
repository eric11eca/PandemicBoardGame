package game.event;

import java.util.Optional;

import game.cards.CardCity;
import game.cards.Deck;

public abstract class EventResilientPopulation implements Event {
	/*
	 * Remove 1 card from the infection discard pile
	 */
	private Deck<CardCity> infectionDiscard;

	@Override
	public void executeEvent() {
		selectCard(infectionDiscard).ifPresent(infectionDiscard::removeCard);
	}

	protected abstract Optional<CardCity> selectCard(Deck<CardCity> infectionDiscard);
}
