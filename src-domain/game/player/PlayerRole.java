package game.player;

import lang.I18n;

public enum PlayerRole {
	//@formatter:off
	CONTINGENCY_PLANNER,
	DISPATCHER,
	MEDIC,
	OPERATION_EXPERT,
	QUARANTINE_SPECIALIST,
	RESEARCHER,
	SCIENTIST;
	//@formatter:on

	public String getRoleName() {
		return I18n.format("role." + this.name());
	}

}
