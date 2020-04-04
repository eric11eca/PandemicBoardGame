package game.player.action;

import java.util.Set;

import game.GameColor;
import game.player.Player;
import game.player.PlayerInteraction;

public class ActionTreatDisease extends Action {

	private Set<GameColor> curedDiseases;

	public ActionTreatDisease(Player player, PlayerInteraction interaction, Set<GameColor> curedDiseases) {
		super(player, interaction);
		this.curedDiseases = curedDiseases;
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
		if (curedDiseases.contains(diseaseColor))
			playerCurrentLocation().eradicateDisease(diseaseColor);
		else
			playerCurrentLocation().treatDisease(diseaseColor);
	}

}
