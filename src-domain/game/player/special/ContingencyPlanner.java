package game.player.special;

import java.util.List;
import java.util.function.Predicate;

import game.cards.Card;
import game.cards.event.CardEventReused;
import game.player.AbstractPlayerDecorator;
import game.player.Player;

/**
 * A decorator that adds the Contingency Planner behavior to Player
 */
public class ContingencyPlanner extends AbstractPlayerDecorator {
	private CardEventReused eventReused;

	public ContingencyPlanner(Player delegate) {
		super(delegate);
	}

	public boolean hasEventCardOnRole() {
		return eventReused != null;
	}

	public void keepEventCardOnRole(CardEventReused card) {
		if (hasEventCardOnRole())
			throw new RuntimeException("Already has event card");
		eventReused = card;
	}

	@Override
	public List<Card> getFilteredHand(Predicate<? super Card> filter) {
		List<Card> hand = super.getFilteredHand(filter);
		if (eventReused != null && filter.test(eventReused))
			hand.add(eventReused);
		return hand;
	}

	@Override
	public void discardCard(Card toDiscard) {
		if (toDiscard.equals(eventReused)) {
			eventReused = null;
		} else {
			super.discardCard(toDiscard);
		}
	}

}
