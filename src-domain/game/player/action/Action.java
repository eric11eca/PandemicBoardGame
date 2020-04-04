package game.player.action;

import game.player.Player;
import game.player.PlayerInteraction;

public abstract class Action {
	protected Player player;
	protected PlayerInteraction interaction;

	public Action(Player player, PlayerInteraction interaction) {
		super();
		this.player = player;
		this.interaction = interaction;
	}

	public abstract void perform();

	public abstract boolean canPerform();

	public boolean isOncePerTurn() {
		return false;
	}
}
