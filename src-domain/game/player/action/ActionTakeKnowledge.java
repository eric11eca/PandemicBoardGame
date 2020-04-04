package game.player.action;

import java.util.ArrayList;
import java.util.List;

import game.cards.Card;
import game.cards.CardCity;
import game.player.Player;
import game.player.PlayerInteraction;

public class ActionTakeKnowledge extends PlayerAction {
	private List<Player> players;

	public ActionTakeKnowledge(Player player, PlayerInteraction interaction, List<Player> players) {
		super(player, interaction);
		this.players = players;
	}

	@Override
	public void perform() {
		interaction.selectPlayerFrom(getOtherPlayersWithCurrentCityCard(), this::performTakeKnowledgeAction);
	}

	@Override
	public boolean canPerform() {
		return !getOtherPlayersWithCurrentCityCard().isEmpty();
	}

	protected List<Player> getOtherPlayersWithCurrentCityCard() {
		List<Player> validPlayers = new ArrayList<>();
		for (Player p : players) {
			if (p != player && otherPlayerHasTakerCityCard(p)) {
				validPlayers.add(p);
			}
		}
		return validPlayers;
	}

	protected boolean otherPlayerHasTakerCityCard(Player otherPlayer) {
		return !otherPlayer.getFilteredHand(playerCardCurrentCity()::equals).isEmpty();
	}

	protected void performTakeKnowledgeAction(Player giver) {
		Card knowledge = playerCardCurrentCity();
		giver.removeCard(knowledge);
		player.receiveCard(knowledge);
	}

	protected CardCity playerCardCurrentCity() {
		return new CardCity(player.getLocation());
	}

}
