package initialize.player;

import java.util.Map;

import game.ActionType;
import game.player.Player;
import game.player.PlayerRole;
import game.player.action.Action;
import game.player.special.Researcher;

public class ResearcherFactory extends AbstractPlayerFactory {

	public ResearcherFactory(int id) {
		super(id);
	}

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
