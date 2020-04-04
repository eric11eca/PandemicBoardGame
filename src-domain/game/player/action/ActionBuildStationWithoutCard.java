package game.player.action;

import game.player.Player;
import game.player.PlayerInteraction;

public class ActionBuildStationWithoutCard extends PlayerAction {

	public ActionBuildStationWithoutCard(Player player, PlayerInteraction interaction) {
		super(player, interaction);
	}

	@Override
	public void perform() {
		player.getLocation().buildResearchStation();
	}

	@Override
	public boolean canPerform() {
		return !player.getLocation().hasResearchStation();
	}

}
