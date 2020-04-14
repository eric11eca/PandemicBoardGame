package game.player.action;

import java.util.List;

import game.cards.Card;
import game.cards.Deck;
import game.cards.event.CardEventReused;
import game.player.PlayerInteraction;
import game.player.special.ContingencyPlanner;

public class ActionSkillContingencyPlanner extends Action {

	private Deck playerDiscardPile;
	private ContingencyPlanner contingencyPlanner;

	public ActionSkillContingencyPlanner(ContingencyPlanner player, PlayerInteraction interaction,
			Deck playerDiscardPile) {
		super(player, interaction);
		this.contingencyPlanner = player;
		this.playerDiscardPile = playerDiscardPile;
	}

	@Override
	public void perform(Runnable completionCallback) {
		interaction.selectOneCardFrom(getDiscardedEventCards(), card -> performSpecialSkill(card, completionCallback));
	}

	@Override
	public boolean canPerform() {
		return !getDiscardedEventCards().isEmpty() && !contingencyPlanner.hasEventCardOnRole();
	}

	protected List<Card> getDiscardedEventCards() {
		return playerDiscardPile.getFilteredSubDeck(card -> card.getEvent().isPresent());
	}

	protected void performSpecialSkill(Card reuseEventCard, Runnable completionCallback) {
		contingencyPlanner.keepEventCardOnRole(new CardEventReused(reuseEventCard));
		completionCallback.run();
	}

}
