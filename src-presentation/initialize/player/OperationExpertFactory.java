package initialize.player;

import java.util.Map;

import game.ActionType;
import game.player.Player;
import game.player.PlayerRole;
import game.player.action.Action;
import game.player.action.ActionBuildStation;
import game.player.action.ActionSkillOperationsExpert;

public class OperationExpertFactory extends AbstractPlayerFactory {

	public OperationExpertFactory(int id) {
		super(id);
	}

	@Override
	protected PlayerRole getRole() {
		return PlayerRole.OPERATION_EXPERT;
	}

	@Override
	protected void addSpecialSkill(Player player, Map<ActionType, Action> actionMap) {
		actionMap.put(ActionType.BUILD_STATION,
				new ActionBuildStation(player, interaction, /* Need Card= */false, citySet));
		actionMap.put(ActionType.SPECIAL_SKILL, new ActionSkillOperationsExpert(player, interaction, citySet));
	}

}
