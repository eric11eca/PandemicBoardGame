package game.player.action;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import game.GameColor;
import game.cards.Card;
import game.player.Player;
import game.player.PlayerInteraction;

public class ActionDiscoverCure extends Action {

	private int cardNeeded;
	private Set<GameColor> curedDiseases;

	public ActionDiscoverCure(Player player, PlayerInteraction interaction, Set<GameColor> curedDiseases,
			int cardNeeded) {
		super(player, interaction);
		this.cardNeeded = cardNeeded;
		this.curedDiseases = curedDiseases;
	}

	@Override
	public void perform(Runnable completionCallback) {
		interaction.selectColorFrom(getCanCureColors(), color -> {
			interaction.selectCardsFrom(cardNeeded, getDiscoverCureCards(color), cards -> {
				performDiscoverCureAction(color, cards, completionCallback);
			});
		});
	}

	@Override
	public boolean canPerform() {
		return !getCanCureColors().isEmpty();
	}

	protected List<Card> getDiscoverCureCards(GameColor color) {
		return player().getFilteredHand(card -> canDiscoverCureUsingCard(color, card));
	}

	protected boolean canDiscoverCureUsingCard(GameColor color, Card card) {
		return card.getCity().filter(c -> c.getColor().equals(color)).isPresent();
	}

	protected void performDiscoverCureAction(GameColor color, List<Card> usingCards, Runnable completionCallback) {
		legalityCheck(color, usingCards);
		player().discardCards(usingCards);
		curedDiseases.add(color);
		completionCallback.run();
	}

	private void legalityCheck(GameColor color, List<Card> usingCards) {
		assert usingCards.size() == cardNeeded;
		usingCards.forEach(card -> {
			assert card.getCity().get().getColor() == color;
		});
	}

	protected Set<GameColor> getCanCureColors() {
		Set<GameColor> canCure = EnumSet.noneOf(GameColor.class);
		canCure.removeAll(curedDiseases);
		for (GameColor color : GameColor.values()) {
			if (getDiscoverCureCards(color).size() >= cardNeeded)
				canCure.add(color);
		}
		return canCure;
	}
}
