package game.player.action;

import java.util.List;

import game.cards.Card;
import game.cards.Deck;
import game.cards.event.CardEventReused;
import game.player.Player;
import game.player.PlayerInteraction;

public class ActionSkillContingencyPlanner extends Action {

	private Deck<Card> playerDiscardPile;

	public ActionSkillContingencyPlanner(Player player, PlayerInteraction interaction, Deck<Card> playerDiscardPile) {
		super(player, interaction);
		this.playerDiscardPile = playerDiscardPile;
	}

	@Override
	public void perform() {
		interaction.selectOneCardFrom(getDiscardedEventCards(), this::performSpecialSkill);
	}

	@Override
	public boolean canPerform() {
		return !getDiscardedEventCards().isEmpty();
	}

	protected List<Card> getDiscardedEventCards() {
		return playerDiscardPile.getFilteredSubDeck(card -> card.getEvent().isPresent());
	}

	protected void performSpecialSkill(Card reuseEventCard) {
		player.receiveCard(new CardEventReused(reuseEventCard));
	}

}
