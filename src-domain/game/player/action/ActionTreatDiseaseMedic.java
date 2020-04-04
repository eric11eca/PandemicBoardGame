package game.player.action;

import game.GameColor;
import game.player.Player;
import game.player.PlayerInteraction;

public class ActionTreatDiseaseMedic extends Action {
	public ActionTreatDiseaseMedic(Player player, PlayerInteraction interaction) {
		super(player, interaction);
	}

	@Override
	public void perform() {
		interaction.selectColorFrom(playerCurrentLocation().getExistingDiseases(), this::performTreatDisease);
	}

	@Override
	public boolean canPerform() {
		return playerCurrentLocation().hasDisease();
	}

	protected void performTreatDisease(GameColor diseaseColor) {
		playerCurrentLocation().eradicateDisease(diseaseColor);
	}

}
