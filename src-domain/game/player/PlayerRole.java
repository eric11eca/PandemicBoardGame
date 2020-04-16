package game.player;

import data.GameProperty;

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
		return GameProperty.getInstance().get(this.name() + "_NAME");
	}

}
