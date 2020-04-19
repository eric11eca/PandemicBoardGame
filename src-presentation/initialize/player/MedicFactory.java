package initialize.player;

import java.util.Map;

import game.ActionType;
import game.player.Player;
import game.player.PlayerRole;
import game.player.action.Action;
import game.player.action.ActionTreatDiseaseMedic;
import game.player.special.Medic;

public class MedicFactory extends AbstractPlayerFactory {

	@Override
	protected PlayerRole getRole() {
		return PlayerRole.MEDIC;
	}

	@Override
	protected void addSpecialSkill(Player player, Map<ActionType, Action> actionMap) {
		actionMap.put(ActionType.TREAT_DISEASE, new ActionTreatDiseaseMedic(player, interaction));
	}

	@Override
	protected Player createPlayer() {
		return new Medic(this.createBasicPlayer(), curedDiseases);
	}

}
