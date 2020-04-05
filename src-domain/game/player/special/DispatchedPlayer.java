package game.player.special;

import java.util.List;
import java.util.function.Predicate;

import game.cards.Card;
import game.player.AbstractPlayerDecorator;
import game.player.Player;

/**
 * A semi-decorator that represents a player controlled by the dispatcher. The
 * methods related to the player's hand is delegated to the dispatcher, while
 * other actions are delegated to the dispatched player
 */
public class DispatchedPlayer extends AbstractPlayerDecorator {
	private Player dispatcher;

	@Override
	public void receiveCard(List<Card> cards) {
		dispatcher.receiveCard(cards);
	}

	@Override
	public void removeCard(Card toRemove) {
		dispatcher.removeCard(toRemove);
	}

	@Override
	public void discardCard(Card toDiscard) {
		dispatcher.discardCard(toDiscard);
	}

	@Override
	public List<Card> getFilteredHand(Predicate<? super Card> filter) {
		return dispatcher.getFilteredHand(filter);
	}

	@Override
	public List<Card> getSharableKnowledgeCards(Player receiver) {
		throw new UnsupportedOperationException("Unsupported by DispatchedPlayer");
	}

	public DispatchedPlayer(Player dispatcher, Player delegate) {
		super(delegate);
		this.dispatcher = dispatcher;
	}

}
