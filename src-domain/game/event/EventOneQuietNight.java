package game.event;

import game.TurnController;
import game.player.PlayerInteraction;
import lang.I18n;

public class EventOneQuietNight implements Event {
	/*
	 * Skip next infection stage
	 */
	private TurnController turnController;

	public EventOneQuietNight(TurnController turnController) {
		super();
		this.turnController = turnController;
	}

	@Override
	public void executeEvent(PlayerInteraction interaction) {
		turnController.skipNextInfectionStage();
	}

	@Override
	public String getName() {
		return I18n.format("event.night");
	}

}
