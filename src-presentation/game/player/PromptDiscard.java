package game.player;

import game.cards.Card;
import game.cards.Deck;

public class PromptDiscard extends AbstractPlayerDecorator {
	public PromptDiscard(Player delegate) {
		super(delegate);
	}

	@Override
	public void discardOneCard(Deck<Card> hand) {
		// TODO implement me
	}
}
