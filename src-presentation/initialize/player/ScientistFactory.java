package initialize.player;

import java.util.Map;

import data.GameProperty;
import game.ActionType;
import game.player.Player;
import game.player.PlayerRole;
import game.player.action.Action;
import game.player.action.ActionDiscoverCure;

public class ScientistFactory extends AbstractPlayerFactory {

	@Override
	protected PlayerRole getRole() {
		return PlayerRole.SCIENTIST;
	}

	@Override
	protected void addSpecialSkill(Player player, Map<ActionType, Action> actionMap) {
		final int DISCOVER_CURE_CARDS = GameProperty.getInstance().getInt("DISCOVER_CURE_CARDS_SCIENTIST");
		actionMap.put(ActionType.DISCOVER_CURE,
				new ActionDiscoverCure(player, interaction, curedDiseases, DISCOVER_CURE_CARDS));
	}

}
