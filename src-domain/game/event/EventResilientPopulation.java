package game.event;

import game.cards.Deck;
import game.player.PlayerInteraction;

public class EventResilientPopulation implements Event {
	/*
	 * Remove 1 card from the infection discard pile
	 */
	private Deck infectionDiscard;

	public EventResilientPopulation(Deck infectionDiscard) {
		super();
		this.infectionDiscard = infectionDiscard;
	}

	@Override
	public void executeEvent(PlayerInteraction interaction) {
		interaction.selectOneCardFrom(infectionDiscard.getFilteredSubDeck(c -> true),
				"event.resilient_population.select_card", infectionDiscard::removeCard);
	}

}
