package initialize.player;

import java.util.Map;

import game.ActionType;
import game.player.Player;
import game.player.PlayerRole;
import game.player.action.Action;
import game.player.action.ActionSkillContingencyPlanner;
import game.player.special.ContingencyPlanner;

public class ContingencyPlannerFactory extends AbstractPlayerFactory {

	public ContingencyPlannerFactory(int id) {
		super(id);
	}

	@Override
	protected PlayerRole getRole() {
		return PlayerRole.CONTINGENCY_PLANNER;
	}

	@Override
	protected void addSpecialSkill(Player player, Map<ActionType, Action> actionMap) {
		actionMap.put(ActionType.SPECIAL_SKILL,
				new ActionSkillContingencyPlanner((ContingencyPlanner) player, interaction, playerDiscard));
	}

	@Override
	protected Player createPlayer() {
		return new ContingencyPlanner(createBasicPlayer());
	}

}
