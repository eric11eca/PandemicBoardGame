package game.player.action;

import data.GameColor;
import game.player.Player;
import game.player.PlayerInteraction;

public class ActionTreatDiseaseMedic extends Action {
	public ActionTreatDiseaseMedic(Player player, PlayerInteraction interaction) {
		super(player, interaction);
	}

	@Override
	public void perform() {
		interaction.selectColorFrom(player.getLocation().getExistingDiseases(), this::performTreatDisease);
	}

	@Override
	public boolean canPerform() {
		return player.getLocation().hasDisease();
	}

	protected void performTreatDisease(GameColor diseaseColor) {
		player.getLocation().eradicateDisease(diseaseColor);
	}

}
