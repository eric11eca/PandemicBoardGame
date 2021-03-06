package game.player.action;

import lang.I18n;

public enum ActionType {
	//@formatter:off
	DRIVE,
	DIRECT_FLIGHT,
	CHARTER_FLIGHT, 
	SHUTTLE_FLIGHT,
	BUILD_STATION,
	TREAT_DISEASE,  
	TAKE_KNOWLEDGE,
	GIVE_KNOWLEDGE,
	DISCOVER_CURE,
	EVENT,
	SPECIAL_SKILL;
	//@formatter:on

	public String getName() {
		return I18n.format("action." + name() + ".name");
	}

}
