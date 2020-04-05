package game.player.special;

import java.util.Collections;
import java.util.List;

import game.cards.Card;
import game.player.AbstractPlayerDecorator;
import game.player.Player;

/**
 * A decorator that adds the Research behavior to a Player
 */
public class Researcher extends AbstractPlayerDecorator {

	public Researcher(Player delegate) {
		super(delegate);
	}

	@Override
	public List<Card> getSharableKnowledgeCards(Player receiver) {
		if (!receiver.getLocation().equals(getLocation()))
			return Collections.emptyList();
		return getFilteredHand(card -> card.getCity().isPresent());
	}

}
