package game.player.action;

import java.util.ArrayList;
import java.util.List;

import game.cards.Card;
import game.cards.CardCity;
import game.player.Player;
import game.player.PlayerInteraction;

public class ActionGiveKnowledge extends Action {
	private List<Player> players;

	public ActionGiveKnowledge(Player player, PlayerInteraction interaction, List<Player> players) {
		super(player, interaction);
		this.players = players;
	}

	@Override
	public void perform() {
		interaction.selectPlayerFrom(getOtherSharablePlayers(), taker -> {
			interaction.selectOneCardFrom(player.getSharableKnowledgeCards(taker), shared -> {
				this.performGiveKnowledgeAction(taker, shared);
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
			if (p != player && !player.getSharableKnowledgeCards(p).isEmpty()) {
				validPlayers.add(p);
			}
		}
		return validPlayers;
	}

	protected void performGiveKnowledgeAction(Player taker, Card shared) {
		player.removeCard(shared);
		taker.receiveCard(shared);
	}

}
