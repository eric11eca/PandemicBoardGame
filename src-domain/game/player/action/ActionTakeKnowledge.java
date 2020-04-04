package game.player.action;

import java.util.ArrayList;
import java.util.List;

import game.cards.Card;
import game.cards.CardCity;
import game.player.Player;
import game.player.PlayerInteraction;

public class ActionTakeKnowledge extends Action {
	private List<Player> players;

	public ActionTakeKnowledge(Player player, PlayerInteraction interaction, List<Player> players) {
		super(player, interaction);
		this.players = players;
	}

	@Override
	public void perform() {
		interaction.selectPlayerFrom(getOtherPlayersWithCurrentCityCard(), giver -> {
			interaction.selectOneCardFrom(giver.getSharableKnowledgeCards(player), shared -> {
				this.performTakeKnowledgeAction(giver, shared);
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
			if (p != player && !p.getSharableKnowledgeCards(player).isEmpty()) {
				validPlayers.add(p);
			}
		}
		return validPlayers;
	}

	protected void performTakeKnowledgeAction(Player giver, Card shared) {
		giver.removeCard(shared);
		player.receiveCard(shared);
	}

}