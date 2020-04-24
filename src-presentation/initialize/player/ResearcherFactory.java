package initialize.player;

import java.util.Map;

import game.player.Player;
import game.player.PlayerRole;
import game.player.action.Action;
import game.player.action.ActionType;
import game.player.special.Researcher;

public class ResearcherFactory extends AbstractPlayerFactory {

	@Override
	protected PlayerRole getRole() {
		return PlayerRole.RESEARCHER;
	}

	@Override
	protected void addSpecialSkill(Player player, Map<ActionType, Action> actionMap) {

	}

	@Override
	protected Player createPlayer() {
		return new Researcher(createBasicPlayer());
	}

}
