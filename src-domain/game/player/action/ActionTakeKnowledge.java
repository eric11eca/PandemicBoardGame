package game.player.action;

import java.util.ArrayList;
import java.util.List;

import game.cards.Card;
import game.player.Player;
import game.player.PlayerInteraction;
import lang.I18n;

public class ActionTakeKnowledge extends Action {
	private List<Player> players;

	public ActionTakeKnowledge(Player player, PlayerInteraction interaction, List<Player> players) {
		super(player, interaction);
		this.players = players;
	}

	@Override
	public void perform(Runnable completionCallback) {
		interaction.selectPlayerFrom(getOtherPlayersWithCurrentCityCard(),
				I18n.format("action.take_knowledge.select_giver"), giver -> {
					interaction.selectOneCardFrom(giver.getSharableKnowledgeCards(player()),
							I18n.format("action.take_knowledge.select_card"), shared -> {
								this.performTakeKnowledgeAction(giver, shared, completionCallback);
							});
				});
	}

	@Override
	public boolean canPerform() {
		return !getOtherPlayersWithCurrentCityCard().isEmpty();
	}

	protected List<Player> getOtherPlayersWithCurrentCityCard() {
		List<Player> validPlayers = new ArrayList<>();
		for (Player p : players) {
			if (p != player() && !p.getSharableKnowledgeCards(player()).isEmpty()) {
				validPlayers.add(p);
			}
		}
		return validPlayers;
	}

	protected void performTakeKnowledgeAction(Player giver, Card shared, Runnable completionCallback) {
		giver.removeCard(shared);
		player().receiveCard(shared);
		completionCallback.run();
	}

}
