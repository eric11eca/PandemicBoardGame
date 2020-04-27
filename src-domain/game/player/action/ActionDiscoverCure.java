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
		interaction.selectColorFrom(getCanCureColors(), "action.discover_cure.select_color", color -> {
			interaction.selectCardsFrom(cardNeeded, getDiscoverCureCards(color), "action.discover_cure.select_cards",
					cards -> {
						performDiscoverCureAction(color, cards, completionCallback);
					});
		});
	}

	@Override
	public boolean canPerform() {
		return playerCurrentLocation().hasResearchStation() && !getCanCureColors().isEmpty();
	}

	protected List<Card> getDiscoverCureCards(GameColor color) {
		return player().getFilteredHand(card -> canDiscoverCureUsingCard(color, card));
	}

	protected boolean canDiscoverCureUsingCard(GameColor color, Card card) {
		return card.getCity().filter(c -> c.getColor().equals(color)).isPresent();
	}

	protected void performDiscoverCureAction(GameColor color, List<Card> usingCards, Runnable completionCallback) {
		player().discardCards(usingCards);
		curedDiseases.add(color);
		completionCallback.run();
	}

	protected Set<GameColor> getCanCureColors() {
		Set<GameColor> canCure = EnumSet.noneOf(GameColor.class);

		for (GameColor color : GameColor.values()) {
			if (getDiscoverCureCards(color).size() >= cardNeeded)
				canCure.add(color);
		}
		canCure.removeAll(curedDiseases);
		return canCure;
	}
}
