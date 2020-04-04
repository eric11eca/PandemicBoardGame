package game.player.action;

import game.player.Player;
import game.player.PlayerInteraction;

public abstract class PlayerAction {
	protected Player player;
	protected PlayerInteraction interaction;

	public PlayerAction(Player player, PlayerInteraction interaction) {
		super();
		this.player = player;
		this.interaction = interaction;
	}

	public abstract void perform();

	public abstract boolean canPerform();
}
