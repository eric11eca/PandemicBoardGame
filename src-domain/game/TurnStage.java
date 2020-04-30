package game;

import lang.I18n;

public enum TurnStage {
	BEFORE_PLAYER_ACTION, PLAYER_ACTION, BEFORE_DRAW_CARD, DRAWING_CARD, BEFORE_INFECTION, INFECTION;

	public String getButtonName() {
		return I18n.format("stage." + name() + ".button");
	}

	public String getStageText() {
		return I18n.format("stage." + name() + ".text");
	}
}
