package game.player.action;

import java.util.Set;

import data.GameColor;
import game.player.Player;
import game.player.PlayerInteraction;

public class ActionTreatDisease extends PlayerAction {

	private Set<GameColor> curedDiseases;

	public ActionTreatDisease(Player player, PlayerInteraction interaction, Set<GameColor> curedDiseases) {
		super(player, interaction);
		this.curedDiseases = curedDiseases;
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
		if (curedDiseases.contains(diseaseColor))
			player.getLocation().eradicateDisease(diseaseColor);
		else
			player.getLocation().treatDisease(diseaseColor);
	}

}
