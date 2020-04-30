package game.player.action;

import game.GameColor;
import game.player.Player;
import game.player.PlayerInteraction;
import lang.I18n;

public class ActionTreatDiseaseMedic extends Action {
	public ActionTreatDiseaseMedic(Player player, PlayerInteraction interaction) {
		super(player, interaction);
	}

	@Override
	public void perform(Runnable completionCallback) {
		interaction.selectColorFrom(playerCurrentLocation().getExistingDiseases(),
				I18n.format("action.treat_disease.select_color"), color -> {
					performTreatDisease(color, completionCallback);
				});
	}

	@Override
	public boolean canPerform() {
		return playerCurrentLocation().hasDisease();
	}

	protected void performTreatDisease(GameColor diseaseColor, Runnable completionCallback) {
		playerCurrentLocation().eradicateDisease(diseaseColor);
		completionCallback.run();
	}

}
