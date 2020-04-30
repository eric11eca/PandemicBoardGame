package game.player.action;

import java.util.ArrayList;
import java.util.List;

import game.cards.Card;
import game.player.Player;
import game.player.PlayerInteraction;
import lang.I18n;

public class ActionGiveKnowledge extends Action {
	private List<Player> players;

	public ActionGiveKnowledge(Player player, PlayerInteraction interaction, List<Player> players) {
		super(player, interaction);
		this.players = players;
	}

	@Override
	public void perform(Runnable completionCallback) {
		interaction.selectPlayerFrom(getOtherSharablePlayers(), I18n.format("action.give_knowledge.select_taker"),
				taker -> {
					interaction.selectOneCardFrom(player().getSharableKnowledgeCards(taker),
							I18n.format("action.give_knowledge.select_card"), shared -> {
								this.performGiveKnowledgeAction(taker, shared, completionCallback);
							});
				});
	}

	@Override
	public boolean canPerform() {
		return !getOtherSharablePlayers().isEmpty();
	}

	protected List<Player> getOtherSharablePlayers() {
		List<Player> validPlayers = new ArrayList<>();
		for (Player p : players) {
			if (p != player() && !player().getSharableKnowledgeCards(p).isEmpty()) {
				validPlayers.add(p);
			}
		}
		return validPlayers;
	}

	protected void performGiveKnowledgeAction(Player taker, Card shared, Runnable completionCallback) {
		player().removeCard(shared);
		taker.receiveCard(shared);
		completionCallback.run();
	}

}
